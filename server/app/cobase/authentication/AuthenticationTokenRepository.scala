package cobase.authentication

import javax.inject.Inject

import cobase.DBTables
import cobase.user.User
import org.joda.time.DateTime
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.driver.JdbcProfile

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class AuthenticationTokenRepository @Inject() (
  protected val dbConfigProvider: DatabaseConfigProvider
) extends HasDatabaseConfigProvider[JdbcProfile] with DBTables {
  import driver.api._

  def add(user: User, token: String, created: DateTime): Future[Unit] = {
    db.run(authenticationTokens += AuthenticationToken(0, user.id, token, created))
      .map(_ => ())
  }

  def removeByToken(token: String): Future[Unit] = {
    db.run(authenticationTokens.filter(_.token === token).delete)
      .map(_ => ())
  }

  def removeByUser(user: User): Future[Unit] = {
    db.run(authenticationTokens.filter(_.userId === user.id).delete)
      .map(_ => ())
  }

  def getByToken(token: String): Future[Option[AuthenticationToken]] = {
    db.run(authenticationTokens.filter(_.token === token).result.headOption)
  }
}
