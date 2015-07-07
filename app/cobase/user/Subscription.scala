package cobase.user

import java.util.UUID

case class Subscription(
  id: Long,
  userId: UUID,
  groupId: UUID
)
