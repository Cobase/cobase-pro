package forms

import play.api.data.Form
import play.api.data.Forms._

/**
 * The form which handles the search phrase.
 */
object SearchForm {

  /**
   * A play framework form.
   */
  val form = Form(
    mapping(
      "phrase" -> nonEmptyText
    )(SearchFormData.apply)(SearchFormData.unapply)
  )
}

case class SearchFormData(phrase: String)