package forms

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
      "tags" -> nonEmptyText
    )(GroupFormData.apply)(GroupFormData.unapply)
  )
}

case class GroupFormData(title: String, tags: String)