package models.services

import javax.inject.Inject

import models.Post
import models.daos._

/**
 * Handles actions to posts.
 *
 * @param postDAO The post DAO implementation.
 */
class PostService @Inject() (postDAO: PostDAO) {

  /**
   * Retrieves all posts.
   *
   * @return The retrieved list of posts.
   */
  def findAll(): List[Post] = postDAO.findAll()

  /**
   * Retrieves a post that matches the specified id.
   *
   * @param postId The id to retrieve a post.
   * @return The retrieved post or None if no post could be retrieved for the given id.
   */
  def findById(postId: Long): Option[Post] = postDAO.findById(postId)

  /**
   * Retrieves all latest posts for a group
   * *
   * @param groupId Group id to retrieve posts from.
   * @return List of group posts.
   */
  def findLatestPostsForGroup(groupId: Long): List[Post] = postDAO.findLatestPostsForGroup(groupId)

  /**
   * Saves a post with given data.
   * *
   * @param post
   * @return Post
   */
  def save(post: Post) = {
    postDAO.save(post)
  }

}
