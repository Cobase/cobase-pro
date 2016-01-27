package cobase.group

import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._

case class AddGroupRequest(
   title: String,
   tags: String
)

object AddGroupRequest {
  implicit val reads: Reads[AddGroupRequest] = (
    (__ \ "title").read[String] and
    (__ \ "tags").read[String]
  )(AddGroupRequest.apply _)
}