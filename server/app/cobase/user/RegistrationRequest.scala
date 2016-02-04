package cobase.user

import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._

case class RegistrationRequest(
  username: String,
  password: String
)

object RegistrationRequest {
  implicit val reads: Reads[RegistrationRequest] = (
    (__ \ "username").read[String](email) and
    (__ \ "password").read[String](minLength[String](8))
  )(RegistrationRequest.apply _)
}