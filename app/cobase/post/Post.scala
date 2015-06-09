package cobase.post

/**
 * The group object.
 *
 * @param id The unique ID of the group post.
 * @param content Content of the group post.
 * @param createdBy The userId of the creator.
 * @param createdTimestamp Timestamp of the creation.
 */
case class Post (
  id: String,
  content: String,
  groupId: String,
  createdBy: Option[String],
  createdTimestamp: Long
)
