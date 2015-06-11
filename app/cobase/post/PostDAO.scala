package cobase.post

import cobase.DBTableDefinitions
import cobase.user.User
import DBTableDefinitions._
import play.api.Play.current
import play.api.db.slick.Config.driver.simple._
import play.api.db.slick._

import scala.concurrent.Future
import scala.slick.jdbc.{GetResult, StaticQuery => Q}

import java.util.UUID

/**
 * Give access to the user object using Slick
 */
class PostDAO {

  /**
   * Finds all posts.
   *
   * @return Seq[Post] List of all posts found.
   */
  def findAll() = {
    DB withSession { implicit session =>
      slickPosts.list
    }
  }

  /**
   * Finds a post by its id.
   *
   * @param postId The id of the post to find.
   * @return The found post or None if no post for the given id could be found.
   */
  def findById(postId: UUID): Option[Post] = {
    DB withSession { implicit session =>
      slickPosts.filter(
        _.id === postId
      ).firstOption
    }
  }

  /**
   * Finds a post by a search phrase.
   *
   * @param phrase The search phrase.
   * @return List of found posts.
   */
  def findByPhrase(phrase: String): List[Post] = {
    DB withSession { implicit session =>
      slickPosts.filter(
        _.content.toLowerCase.like("%" + phrase.toLowerCase + "%")
      ).sortBy(_.id.desc).list
    }
  }

  /**
   * Finds all latest posts by group id.
   *
   * @param groupId The id of the group to find posts from.
   * @return The list of found posts.
   */
  def findLatestPostsForGroup(groupId: UUID) = {
    DB withSession { implicit session =>
      slickPosts.filter(
        _.groupId === groupId
      ).sortBy(_.createdTimestamp.desc).list
    }
  }

  /**
   * Saves a post with the post data.
   *
   * @param post
   * @return Post
   */
  def save(post: Post) = {
    DB withSession { implicit session =>
      Future.successful {
        slickPosts.insert(post)
        post
      }
    }
  }

  /**
   * Updates a post with the post data.
   *
   * @param post
   * @return Post
   */
  def update(post: Post) = {
    DB withSession { implicit session =>
      Future.successful {
        slickPosts.filter(_.id === post.id).update(post)
        post
      }
    }
  }

  /**
   * Get posts related to user's subscriptions
   *
   * @return Seq[DashboardPosts
   */
  def getDashboardPosts(user: User): List[DashboardPost] = {
    DB withSession { implicit session =>
      implicit val getPostResult =
        GetResult(r =>
          DashboardPost(r.nextString(), r.nextString(), r.nextLong(), r.nextString(), UUID.fromString(r.nextString()))
        )

      val userId = user.id

      val dashboardPosts = Q[DashboardPost] +
        s"""
            SELECT
              p.content, p.created_by, p.created_timestamp, g.title, g.id
            FROM
              posts p, groups g, subscriptions s
            WHERE
              g.id = p.group_id AND
              s.group_id = p.group_id AND
              s.user_id = '$userId'
            ORDER BY p.created_timestamp DESC
        """

      dashboardPosts.list
    }
  }

}
