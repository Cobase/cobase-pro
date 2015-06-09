package cobase.post

import javax.inject.Inject

import cobase.user.User

/**
 * Handles actions to posts.
 *
 * @param postDAO The post DAO implementation.
 */
class PostService @Inject() (postDAO: PostDAO) {

  /**
   * Retrieves erall posts.
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
  def findById(postId: String): Option[Post] = postDAO.findById(postId)

  /**
   * Retrieves a post that matches the specified search phrase.
   *
   * @param phrase The search phrase.
   * @return The retrieved post or None if no post could be retrieved for the given phrase.
   */
  def findByPhrase(phrase: String): List[Post] = postDAO.findByPhrase(phrase)

  /**
   * Retrieves all latest posts for a group
   * *
   * @param groupId Group id to retrieve posts from.
   * @return List of group posts.
   */
  def findLatestPostsForGroup(groupId: String): List[Post] = postDAO.findLatestPostsForGroup(groupId)

  /**
   * Saves a post with given data.
   * *
   * @param post
   * @return Post
   */
  def save(post: Post) = {
    postDAO.save(post)
  }

  /**
   * Updates a post with given data.
   * *
   * @param post
   * @return Post
   */
  def update(post: Post) = {
    postDAO.update(post)
  }

  /**
   * Get posts found based on user subscriptions
   *
   * @param user User
   * @return
   */
  def getDashboardPosts(user: User): List[DashboardPost] = {
    postDAO.getDashboardPosts(user)
  }

}
