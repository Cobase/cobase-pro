package models.daos

import models.{Post, DashboardPost}
import play.api.db.slick._
import play.api.db.slick.Config.driver.simple._
import scala.slick.jdbc.{GetResult, StaticQuery => Q}
import Q.interpolation
import models.daos.DBTableDefinitions._
import scala.concurrent.Future
import play.Logger
import models.User
import play.api.Play.current

import scala.slick.jdbc.{StaticQuery, GetResult}

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
  def findById(postId: Long): Option[Post] = {
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
      ).list
    }
  }

  /**
   * Finds all latest posts by group id.
   *
   * @param groupId The id of the group to find posts from.
   * @return The list of found posts.
   */
  def findLatestPostsForGroup(groupId: Long) = {
    DB withSession { implicit session =>
      slickPosts.filter(
        _.groupId === groupId
      ).sortBy(_.id.desc).list
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
   * @return Seq[DashboardPosts]
   */
  def getDashboardPosts(user: User): List[DashboardPost] = {
    DB withSession { implicit session =>
      implicit val getPostResult =
        GetResult(r =>
          DashboardPost(r.nextString().toString(), r.nextString().toString(), r.nextLong(), r.nextString().toString(), r.nextLong())
        )

      val userId = user.userID

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
