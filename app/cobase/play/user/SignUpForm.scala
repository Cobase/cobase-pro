package cobase.play.user

import play.api.data.Form
import play.api.data.Forms._

/**
 * The form which handles the sign up process.
 */
object SignUpForm {
  /**
   * A play framework form.
   */
  val form = Form(
    mapping(
      "firstName" -> nonEmptyText,
      "lastName" -> nonEmptyText,
      "email" -> email,
      "password" -> nonEmptyText
    )(SignUpFormData.apply)(SignUpFormData.unapply)
  )
}

/**
 * @param firstName The first name of a user.
 * @param lastName The last name of a user.
 * @param email The email of the user.
 * @param password The password of the user.
 */
case class SignUpFormData(
  firstName: String,
  lastName: String,
  email: String,
  password: String
)
