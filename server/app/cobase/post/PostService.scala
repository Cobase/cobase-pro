package cobase.post

import java.util.UUID
import javax.inject.Inject

import cobase.group.Group
import cobase.user.User
import org.joda.time.DateTime

import scala.concurrent.Future

/**
 * Handles actions to posts.
 */
class PostService @Inject() (postRepository: PostRepository) {
  def getPosts: Future[Seq[Post]] = postRepository.getPosts

  def getPostById(postId: UUID): Future[Option[Post]] = postRepository.getPostById(postId)

  /**
   * Retrieves a post that matches the specified search phrase.
   */
  def getPostsByPhrase(phrase: String): Future[Seq[Post]] = postRepository.getPostsByPhrase(phrase)

  def getLatestPostsForGroup(group: Group): Future[Seq[Post]] = postRepository.getLatestPostsForGroup(group.id)

  def addPost(content: String, group: Group, user: User): Future[Post] = {
    postRepository.addPost(
      UUID.randomUUID,
      content,
      group.id,
      user.fullName,
      DateTime.now()
    )
  }

  def updatePost(post: Post, content: String): Future[Post] = postRepository.updatePost(post, content)
}
