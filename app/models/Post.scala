package models

/**
 * The group object.
 *
 * @param id The unique ID of the group post.
 * @param content Content of the group post.
 * @param createdBy The userId of the creator.
 * @param createdTimestamp Timestamp of the creation.
 */
case class Post (
  id: Long,
  content: String,
  groupId: Long,
  createdBy: Option[String],
  createdTimestamp: Long
)
