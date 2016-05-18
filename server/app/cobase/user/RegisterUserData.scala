package cobase.user

import java.util.UUID

import org.joda.time.DateTime

case class RegisterUserData(
  id: UUID,
  username: String,
  hashedPassword: String,
  role: Role,
  created: DateTime,
  verificationToken: String,
  firstName: Option[String],
  lastName: Option[String]
)
