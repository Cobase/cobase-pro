package cobase.play.user

import cobase.authentication.AuthenticationService
import cobase.play.authentication.AuthenticatedRequest
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

trait SecuredController extends Controller {
  protected def authenticationService: AuthenticationService

  object AuthenticatedAction extends ActionBuilder[AuthenticatedRequest] {
    def invokeBlock[A](
      request: Request[A],
      block: AuthenticatedRequest[A] => Future[Result]
    ): Future[Result] = {
      request.headers.get("X-Token") match {
        case Some(token) =>
          authenticationService.getAuthenticatedUserByAuthenticationToken(token).flatMap {
            case Some(user) => block(new AuthenticatedRequest(user, request))
            case None => Future.successful(Results.Unauthorized)
          }

        case _ => Future.successful(Results.Unauthorized)
      }
    }
  }
}
