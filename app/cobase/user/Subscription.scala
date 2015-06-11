package cobase.user

import java.util.UUID

/**
 * The user object.
 *
 * @param id Id of the row
 * @param userId The unique ID of the user.
 * @param groupId The id of the group subcribed to.
 */
case class Subscription(
  id: Long,
  userId: UUID,
  groupId: UUID
)
