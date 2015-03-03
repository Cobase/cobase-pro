package models

/**
 * The group object.
 *
 * @param id The unique ID of the group post.
 * @param title The title of the group post.
 * @param content Content of the group post.
 */
case class Post (
  id: Long,
  title: String,
  content: String,
  groupId: Long
)
