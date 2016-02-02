package cobase.play.user

import javax.inject.Inject

import cobase.user.{RegistrationRequest, RegistrationResponse, UserService}
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.json.{JsError, Json}
import play.api.mvc.{Action, Controller}

import scala.concurrent.Future

class UserController @Inject()(
  userService: UserService
) extends Controller {

  def registerUser = Action.async(parse.json) { implicit request =>
    request.body.validate[RegistrationRequest].fold(
      errors => Future.successful(BadRequest(Json.obj("errors" -> JsError.toJson(errors)))),
      registrationRequest => {
        userService.registerUser(registrationRequest)
          .map(user => Created(Json.toJson(RegistrationResponse.fromUser(user))))
          .recoverWith {
            case e: Exception => Future.successful(Conflict(Json.obj()))
          }
      }
    )
  }
}
