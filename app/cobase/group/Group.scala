package cobase.group

import java.util.UUID

/**
 * @param id The unique ID of the group.
 * @param title The title of the group.
 * @param tags Comma separated list of twitter hashtags.
 */
case class Group(
  id: UUID,
  title: String,
  tags: String
)
