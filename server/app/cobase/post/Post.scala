package cobase.post

import java.util.UUID

import org.joda.time.DateTime

/**
 * @param id The unique ID of the group post.
 * @param content Content of the group post.
 * @param groupId ID of the group the post belongs to.
 * @param createdBy The userId of the creator.
 * @param created Timestamp of the creation.
 * @param isActive is group visible to user
 */
case class Post(
  id: UUID,
  content: String,
  groupId: UUID,
  createdBy: Option[String],
  created: DateTime,
  isActive: Boolean
)
