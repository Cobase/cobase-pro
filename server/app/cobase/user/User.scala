package cobase.user

import java.util.UUID

import org.joda.time.DateTime

case class User(
  id: UUID,
  username: String,
  password: String,
  role: String,
  created: DateTime,
  verificationToken: String,
  verified: Option[DateTime] = None,
  firstName: Option[String] = None,
  lastName: Option[String] = None,
  avatarURL: Option[String] = None,
  passwordResetToken: Option[String] = None,
  passwordResetRequested: Option[DateTime] = None,
  passwordResetUsed: Option[DateTime] = None
) {
  lazy val fullName: Option[String] = for {
    first <- firstName
    last <- lastName
  } yield (first + last).trim

  lazy val isVerified: Boolean = verified.isDefined
}
