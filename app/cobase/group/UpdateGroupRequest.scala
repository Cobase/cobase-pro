package cobase.group

import java.util.UUID

import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._

case class UpdateGroupRequest(
  title: String,
  tags: String
)

object UpdateGroupRequest {
  implicit val reads: Reads[UpdateGroupRequest] = (
    (__ \ "title").read[String] and
    (__ \ "tags").read[String]
  )(UpdateGroupRequest.apply _)
}