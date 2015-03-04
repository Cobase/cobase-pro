package forms

import play.api.data.Form
import play.api.data.Forms._

/**
 * The form which handles the creation of a post.
 */
object PostForm {

  /**
   * A play framework form.
   */
  val form = Form(
    mapping(
      "content" -> nonEmptyText
    )(PostFormData.apply)(PostFormData.unapply)
  )
}

case class PostFormData(content: String)