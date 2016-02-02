package cobase.group

import java.util.UUID

/**
 * @param id Group ID
 * @param title Group title
 * @param tags Group tags
 * @param isActive Is group active
 * @param postCount Count of posts in the group
 */
case class GroupLink (
  id: UUID,
  title: String,
  tags: String,
  isActive: Boolean,
  postCount: Int
)
