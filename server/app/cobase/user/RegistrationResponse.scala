package cobase.user

import java.util.UUID

import play.api.libs.json.Json

case class RegistrationResponse(
  id: UUID,
  username: String,
  role: String,
  fullName: Option[String]
)

object RegistrationResponse {
  implicit val writes = Json.format[RegistrationResponse]

  def fromUser(user: User): RegistrationResponse = {
    this(user.id, user.username, user.role.name, user.fullName)
  }
}
