package cobase.authentication

import java.util.UUID
import javax.inject.Inject

import cobase.user.User
import org.joda.time.DateTime
import play.api.libs.Codecs

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class TokenService @Inject() (tokenRepository: AuthenticationTokenRepository) {
  def generateToken: String = {
    Codecs.sha1(UUID.randomUUID.toString)
  }

  def removeUsersTokens(user: AuthenticatedUser): Future[Unit] = {
    tokenRepository.removeByUser(user.user)
  }

  def removeToken(token: String): Future[Unit] = {
    tokenRepository.removeByToken(token)
  }

  def addToken(user: User, token: String): Future[Unit] = {
    tokenRepository.add(user, token, DateTime.now())
  }

  def tokenExists(token: String): Future[Boolean] = {
    tokenRepository.getByToken(token).map(_.isDefined)
  }
}
