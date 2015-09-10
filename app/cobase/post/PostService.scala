package cobase.post

import javax.inject.Inject
import java.util.UUID

import cobase.user.User

import scala.concurrent.Future

/**
 * Handles actions to posts.
 */
class PostService @Inject() (postDAO: PostDAO) {

  def findAll: Future[Seq[Post]] = postDAO.findAll

  def findById(postId: UUID): Future[Option[Post]] = postDAO.findById(postId)

  /**
   * Retrieves a post that matches the specified search phrase.
   */
  def findByPhrase(phrase: String): Future[Seq[Post]] = postDAO.findByPhrase(phrase)

  def findLatestPostsForGroup(groupId: UUID): Future[Seq[Post]] = postDAO.findLatestPostsForGroup(groupId)

  def add(post: Post): Future[Post] = postDAO.add(post)

  def update(post: Post): Future[Post] = postDAO.update(post)

  /**
   * Get posts found based on user subscriptions
   */
  def getDashboardPosts(user: User): Future[Seq[DashboardPost]] = postDAO.getDashboardPosts(user)

}
