package cobase.post

import java.util.UUID

/**
 * @param id The unique ID of the group post.
 * @param content Content of the group post.
 * @param groupId ID of the group the post belongs to.
 * @param createdBy The userId of the creator.
 * @param createdTimestamp Timestamp of the creation.
 * @param isActive is group visible to user
 */
case class Post(
  id: UUID,
  content: String,
  groupId: UUID,
  createdBy: Option[String],
  createdTimestamp: Long,
  isActive: Boolean
)
