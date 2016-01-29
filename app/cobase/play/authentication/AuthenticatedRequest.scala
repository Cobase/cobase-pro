package cobase.play.authentication

import cobase.authentication.AuthenticatedUser
import play.api.mvc.{Request, WrappedRequest}

class AuthenticatedRequest[A](val user: AuthenticatedUser, request: Request[A])
  extends WrappedRequest[A](request)
