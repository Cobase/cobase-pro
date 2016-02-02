package cobase.group

import java.util.UUID

import play.api.libs.json.Json

case class GroupLinkResponse(
  id: UUID,
  title: String,
  tags: String,
  isActive: Boolean,
  postCount: Int
)

object GroupLinkResponse {
  implicit val writes = Json.format[GroupLinkResponse]

  def fromGroup(group: GroupLink): GroupLinkResponse = {
    this(group.id, group.title, group.tags, group.isActive, group.postCount)
  }
}
