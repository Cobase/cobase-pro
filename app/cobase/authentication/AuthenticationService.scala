package cobase.authentication

import javax.inject.Inject

import cobase.user.UserRepository

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class AuthenticationService @Inject() (
  userRepository: UserRepository,
  tokenService: TokenService,
  passwordService: PasswordService
) {
  def login(username: String, password: String): Future[Option[AuthenticatedUser]] = {
    userRepository.getByEmail(username).flatMap {
      case Some(user) if user.isVerified && passwordService.checkPassword(password, user.password) =>
        val token = tokenService.generateToken

        tokenService.addToken(user, token)
          .map(_ => Some(AuthenticatedUser(user, token)))

      case _ => Future.successful(None)
    }
  }

  def logout(user: AuthenticatedUser): Future[Unit] = {
    tokenService.removeToken(user.token)
  }

  def isAuthenticated(token: String): Future[Boolean] = {
    tokenService.tokenExists(token)
  }

  def getAuthenticatedUserByAuthenticationToken(token: String): Future[Option[AuthenticatedUser]] = {
    userRepository.getByAuthenticationToken(token).map {
      case Some(user) => Some(AuthenticatedUser(user, token))
      case None => None
    }
  }
}
