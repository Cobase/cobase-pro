package models

/**
 * The group object.
 *
 * @param id The unique ID of the group.
 * @param title The title of the group.
 * @param description Detailed description of the group.
 */
case class Group (
  id: Long,
  title: String,
  description: String
)
