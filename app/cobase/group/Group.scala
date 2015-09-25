package cobase.group

import java.util.UUID

/**
 * @param id The unique ID of the group.
 * @param title The title of the group.
 * @param tags Comma separated list of twitter hashtags.
 * @param isActive Is group shown to the user
 */
case class Group(
  id: UUID,
  title: String,
  tags: String,
  isActive: Boolean
)
