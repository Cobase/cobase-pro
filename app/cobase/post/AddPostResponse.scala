package cobase.post

import java.util.UUID

import org.joda.time.DateTime
import play.api.libs.json._

case class AddPostResponse(
  id: UUID,
  content: String,
  active: Boolean,
  created: DateTime,
  createdBy: Option[String]
)

object AddPostResponse {
  implicit val dateWrites = Writes.jodaDateWrites("yyyy-MM-dd'T'HH:mm:ssZ")
  implicit val writes = Json.format[AddPostResponse]

  def fromPost(post: Post): AddPostResponse = {
    this(post.id, post.content, post.isActive, post.created, post.createdBy)
  }
}
