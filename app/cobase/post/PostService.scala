package cobase.post

import javax.inject.Inject
import java.util.UUID

import cobase.group.Group
import cobase.user.User
import org.joda.time.DateTime

import scala.concurrent.Future

/**
 * Handles actions to posts.
 */
class PostService @Inject() (postRepository: PostRepository) {
  def findAll: Future[Seq[Post]] = postRepository.findAll

  def findById(postId: UUID): Future[Option[Post]] = postRepository.findById(postId)

  /**
   * Retrieves a post that matches the specified search phrase.
   */
  def findByPhrase(phrase: String): Future[Seq[Post]] = postRepository.findByPhrase(phrase)

  def findLatestPostsForGroup(groupId: UUID): Future[Seq[Post]] = postRepository.findLatestPostsForGroup(groupId)

  def add(content: String, group: Group, user: User): Future[Post] = {
    postRepository.add(
      UUID.randomUUID,
      content,
      group.id,
      user.fullName,
      DateTime.now()
    )
  }

  def update(post: Post, content: String): Future[Post] = postRepository.update(post, content)

  /**
   * Get posts found based on user subscriptions
   */
  def getDashboardPosts(user: User): Future[Seq[DashboardPost]] = postRepository.getDashboardPosts(user)
}
