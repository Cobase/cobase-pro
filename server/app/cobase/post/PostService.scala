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

  def findLatestPostsForGroup(group: Group): Future[Seq[Post]] = postRepository.findLatestPostsForGroup(group.id)

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
}
