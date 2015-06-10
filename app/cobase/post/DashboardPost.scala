package cobase.post

import java.util.UUID

/**
  * The group link object.
  *
  * @param content Content of the post.
  * @param createdBy Author of the post.
  * @param createdTimestamp Timestamp of the post creation.
  * @param groupTitle Title of the group the post belongs to.
  */
case class DashboardPost(
  content: String,
  createdBy: String,
  createdTimestamp: Long,
  groupTitle: String,
  groupId: UUID
)
