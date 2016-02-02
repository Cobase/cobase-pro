package cobase.authentication

import java.util.UUID

import org.joda.time.DateTime

case class AuthenticationToken(
  id: Long,
  userId: UUID,
  token: String,
  created: DateTime
)
