package cobase.authentication

import java.util.UUID

import play.api.libs.json._

case class AuthenticationResponse(
  id: UUID,
  username: String,
  role: String,
  token: String,
  firstName: Option[String],
  lastName: Option[String]
)

object AuthenticationResponse {
  implicit val writes = Json.format[AuthenticationResponse]

  def fromAuthenticatedUser(u: AuthenticatedUser): AuthenticationResponse = {
    this(u.user.id, u.user.username, u.user.role.name, u.token, u.user.firstName, u.user.lastName)
  }
}
