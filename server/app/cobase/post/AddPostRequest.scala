package cobase.post

import play.api.libs.json.Reads._
import play.api.libs.json._

case class AddPostRequest(content: String)

object AddPostRequest {
  implicit val reads: Reads[AddPostRequest] = (__ \ "content").read[String](minLength[String](1))
    .map(content => AddPostRequest(content))
}
