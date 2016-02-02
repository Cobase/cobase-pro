package cobase.post

import play.api.libs.json.Reads._
import play.api.libs.json._

case class EditPostRequest(content: String)

object EditPostRequest {
  implicit val reads: Reads[EditPostRequest] = (__ \ "content").read[String](minLength[String](1))
    .map(content => EditPostRequest(content))
}
