package cobase.group

import java.util.UUID

/**
 * The group link object.
 *
 * @param id The unique ID of the group link.
 * @param title The title of the group.
 * @param count Count of posts in the group.
 */
case class GroupLink (
  id: UUID,
  title: String,
  count: Int
)
