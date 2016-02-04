package cobase.user

import javax.inject.Inject

import cobase.DBTables
import org.joda.time.format.DateTimeFormat
import org.joda.time.{DateTime, DateTimeZone}
import play.api.db.slick._
import slick.driver.JdbcProfile

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class UserRepository @Inject() (
  protected val dbConfigProvider: DatabaseConfigProvider
) extends HasDatabaseConfigProvider[JdbcProfile] with DBTables {
  import driver.api._

  def getByEmail(email: String): Future[Option[User]] = {
    db.run(users.filter(_.email === email).result.headOption)
  }

  def getByAuthenticationToken(token: String): Future[Option[User]] = {
    val q = for {
      (user, authenticationToken) <- users join authenticationTokens on (_.id === _.userId)
      if authenticationToken.token === token
    } yield user

    db.run(q.result.headOption)
  }

  def addUser(data: RegisterUserData): Future[User] = {
    val insertQuery = users returning users.map(_.id) into ((user, id) => user.copy(id = id))

    db.run(insertQuery += User(
      data.id,
      data.username,
      data.hashedPassword,
      data.role,
      data.created,
      data.verificationToken
    ))
  }

  def updateVerified(user: User, verified: DateTime): Future[Boolean] = {
    val date = verified.withZone(DateTimeZone.UTC)
    val format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")

    val q = sqlu"""UPDATE users u
     SET verified = ${format.print(date)}::timestamp
     WHERE u.id = ${user.id.toString}::uuid AND verified IS NULL"""

    db.run(q).map(_ > 0)
  }
}
