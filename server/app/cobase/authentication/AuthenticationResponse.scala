package cobase.authentication

import java.util.UUID

import play.api.libs.json._

case class AuthenticationResponse(
  id: UUID,
  username: String,
  token: String,
  fullName: Option[String]
)

object AuthenticationResponse {
  implicit val writes = Json.format[AuthenticationResponse]

  def fromAuthenticatedUser(u: AuthenticatedUser): AuthenticationResponse = {
    this(u.user.id, u.user.username, u.token, u.user.fullName)
  }
}
