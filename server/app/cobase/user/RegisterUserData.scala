package cobase.user

import java.util.UUID

import org.joda.time.DateTime

case class RegisterUserData(
  id: UUID,
  username: String,
  hashedPassword: String,
  created: DateTime,
  verificationToken: String
)
