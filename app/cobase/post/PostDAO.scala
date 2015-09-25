package cobase.post

import java.util.UUID
import javax.inject.Inject

import cobase.DBTableDefinitions
import cobase.user.User
import play.api.db.slick._
import slick.driver.JdbcProfile
import slick.jdbc.GetResult

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 * Give access to the user object using Slick
 */
class PostDAO @Inject() (protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] with DBTableDefinitions {
  import driver.api._

  def findAll: Future[Seq[Post]] = {
    db.run(slickPosts.result)
  }

  def findById(postId: UUID): Future[Option[Post]] = {
    db.run(slickPosts.filter(_.id === postId).result.headOption)
  }

  /**
   * Finds posts by search phrase.
   */
  def findByPhrase(phrase: String): Future[Seq[Post]] = {
    db.run(
      slickPosts
        .filter(_.content.toLowerCase.like("%" + phrase.toLowerCase + "%"))
        .sortBy(_.id.desc)
        .result
    )
  }

  def findLatestPostsForGroup(groupId: UUID): Future[Seq[Post]] = {
    db.run(
      slickPosts
        .filter(_.groupId === groupId)
        .filter(_.isActive === true)
        .sortBy(_.createdTimestamp.desc)
        .result
    )
  }

  def add(post: Post): Future[Post] = {
    db.run(slickPosts += post)
      .map(_ => post)
  }

  def update(post: Post): Future[Post] = {
    db.run(slickPosts.filter(_.id === post.id).update(post))
      .map(_ => post)
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
      SELECT p.content, p.created_by, p.created_timestamp, g.title, g.id
      FROM posts p
      INNER JOIN groups g ON g.id = p.group_id
      INNER JOIN subscriptions s ON s.group_id = p.group_id
      WHERE s.user_id = CAST(${user.id.toString} AS uuid)
      AND p.is_active = true
      ORDER BY p.created_timestamp DESC
    """.as[DashboardPost]

    db.run(query)
  }
}
