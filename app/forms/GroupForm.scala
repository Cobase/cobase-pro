package forms

import models.Group
import play.api.data.Form
import play.api.data.Forms._

/**
 * The form which handles the creation of a group.
 */
object GroupForm {

  /**
   * A play framework form.
   */
  val form = Form(
    mapping(
      "title" -> nonEmptyText,
      "description" -> nonEmptyText
    )(FormData.apply)(FormData.unapply)
  )
}

case class FormData(title: String, description: String)