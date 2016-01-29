package cobase.play.user

import javax.inject.{Inject, Singleton}

import cobase.authentication.{AuthenticationRequest, AuthenticationResponse, AuthenticationService}
import play.api.libs.json._
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class AuthenticationController @Inject() (
  val authenticationService: AuthenticationService
) extends SecuredController {

  def login = Action.async(parse.json) { implicit request =>
    request.body.validate[AuthenticationRequest].fold(
      errors => Future.successful(BadRequest(Json.obj("errors" -> JsError.toJson(errors)))),
      authRequest => {
        authenticationService.login(authRequest.username, authRequest.password).map {
          case Some(authenticatedUser) => Ok(Json.toJson(
            AuthenticationResponse.fromAuthenticatedUser(authenticatedUser)
          ))

          case None => Unauthorized(Json.obj())
        }
      }
    )
  }

  def logout = AuthenticatedAction.async(parse.json) { implicit request =>
    authenticationService.logout(request.user)

    Future.successful(Ok(Json.obj()))
  }
}
