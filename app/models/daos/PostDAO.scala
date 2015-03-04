package models.daos

import models.Post
import play.api.db.slick._
import play.api.db.slick.Config.driver.simple._
import models.daos.DBTableDefinitions._
import scala.concurrent.Future
import play.Logger
import play.api.Play.current

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

}
