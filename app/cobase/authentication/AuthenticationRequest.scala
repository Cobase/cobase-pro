package cobase.authentication

import play.api.libs.json._

case class AuthenticationRequest(username: String, password: String)

object AuthenticationRequest {
  implicit val reads = Json.format[AuthenticationRequest]
}
