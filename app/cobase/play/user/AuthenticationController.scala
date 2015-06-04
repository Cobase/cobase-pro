package cobase.play.user

import javax.inject.Inject

import cobase.user.User
import com.mohiva.play.silhouette.api.{Environment, LogoutEvent, Silhouette}
import com.mohiva.play.silhouette.impl.authenticators.SessionAuthenticator

import scala.concurrent.Future

/**
 * The basic application controller.
 *
 * @param env The Silhouette environment.
 */
class AuthenticationController @Inject() (
  implicit val env: Environment[User, SessionAuthenticator]
) extends Silhouette[User, SessionAuthenticator] {
  def signIn = UserAwareAction.async { implicit request =>
    request.identity match {
      case Some(user) => Future.successful(Redirect(routes.ApplicationController.index))
      case None => Future.successful(Ok(views.html.signIn(SignInForm.form)))
    }
  }

  def signOut = SecuredAction.async { implicit request =>
    val result = Future.successful(Redirect(routes.ApplicationController.index))
    env.eventBus.publish(LogoutEvent(request.identity, request, request2lang))

    request.authenticator.discard(result)
  }
}
