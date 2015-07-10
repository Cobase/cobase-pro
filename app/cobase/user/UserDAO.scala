package cobase.user

import java.util.UUID

import com.mohiva.play.silhouette.api.LoginInfo

import scala.concurrent.Future

/**
 * Give access to the user object.
 */
trait UserDAO {

  def find(loginInfo: LoginInfo): Future[Option[User]]

  def find(userId: UUID): Future[Option[User]]

  def save(user: User): Future[User]
}
