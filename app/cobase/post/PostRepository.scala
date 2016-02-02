package cobase.post

import java.util.UUID
import javax.inject.Inject

import com.github.tototoshi.slick.PostgresJodaSupport._
import cobase.DBTables
import cobase.user.User
import org.joda.time.DateTime
import play.api.db.slick._
import slick.driver.JdbcProfile
import slick.jdbc.GetResult

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class PostRepository @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] with DBTables {
  import driver.api._

  def findAll: Future[Seq[Post]] = {
    db.run(posts.result)
  }

  def findById(postId: UUID): Future[Option[Post]] = {
    db.run(posts.filter(_.id === postId).result.headOption)
  }

  /**
   * Finds posts by search phrase.
   */
  def findByPhrase(phrase: String): Future[Seq[Post]] = {
    db.run(
      posts
        .filter(_.content.toLowerCase.like("%" + phrase.toLowerCase + "%"))
        .sortBy(_.id.desc)
        .result
    )
  }

  def findLatestPostsForGroup(groupId: UUID): Future[Seq[Post]] = {
    db.run(
      posts
        .filter(_.groupId === groupId)
        .filter(_.isActive === true)
        .sortBy(_.created.desc)
        .result
    )
  }

  def add(id: UUID, content: String, groupId: UUID, userFullName: Option[String], created: DateTime): Future[Post] = {
    val post = Post(id, content, groupId, userFullName, created, isActive = true)

    db.run(posts += post)
      .map(_ => post)
  }

  def update(post: Post, content: String): Future[Post] = {
    val updatedPost = post.copy(content = content)

    db.run(posts.filter(_.id === updatedPost.id).update(updatedPost))
      .map(_ => updatedPost)
  }

  /**
   * Get posts related to user's subscriptions.
   */
  def getDashboardPosts(user: User): Future[Seq[DashboardPost]] = {
    implicit val getPostResult =
      GetResult(r =>
        DashboardPost(r.nextString(), r.nextString(), r.nextLong(), r.nextString(), UUID.fromString(r.nextString()))
      )

    val query = sql"""
      SELECT p.content, p.created_by, p.created, g.title, g.id
      FROM posts p
      INNER JOIN groups g ON g.id = p.group_id
      INNER JOIN subscriptions s ON s.group_id = p.group_id
      WHERE s.user_id = CAST(${user.id.toString} AS uuid)
      AND g.is_active = true
      AND p.is_active = true
      ORDER BY p.created DESC
    """.as[DashboardPost]

    db.run(query)
  }
}
