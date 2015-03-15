package models

import java.util.UUID

import com.mohiva.play.silhouette.api.{Identity, LoginInfo}

/**
 * The user object.
 *
 * @param id Id of the row
 * @param userID The unique ID of the user.
 * @param groupId The id of the group subcribed to.
 */
case class Subscription(
  id: Long,
  userID: UUID,
  groupId: Long
)
