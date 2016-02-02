package cobase.post

import java.util.UUID

import org.joda.time.DateTime
import play.api.libs.json.{Json, Writes}

case class PostResponse(
  id: UUID,
  groupId: UUID,
  content: String,
  active: Boolean,
  created: DateTime,
  createdBy: Option[String]
)

object PostResponse {
  implicit val dateWrites = Writes.jodaDateWrites("yyyy-MM-dd'T'HH:mm:ssZ")
  implicit val writes = Json.format[PostResponse]

  def fromPost(post: Post): PostResponse = {
    this(post.id, post.groupId, post.content, post.isActive, post.created, post.createdBy)
  }
}
