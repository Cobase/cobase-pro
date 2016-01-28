package cobase.user

import javax.inject.Inject

import cobase.DBTables
import cobase.group.Group
import play.api.db.slick._
import slick.driver.JdbcProfile

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 * Give access to the subscription object using Slick
 */
class SubscriptionRepository @Inject() (protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] with DBTables {
  import driver.api._

  def isUserSubscribedToGroup(user: User, group: Group): Future[Boolean] = {
    val result = db.run(
      subscriptions
        .filter(_.userId === user.id)
        .filter(_.groupId === group.id)
        .result
        .headOption
    )

    result.map(subscription => subscription.isDefined)
  }

  def subscribeUserToGroup(user: User, group: Group): Future[Subscription] = {
    db.run((subscriptions returning subscriptions.map(_.id)) += Subscription(0, user.id, group.id))
      .map(id => Subscription(id, user.id, group.id))
  }

  def unsubscribeUserFromGroup(user: User, group: Group): Future[Unit] = {
    val result = db.run(
      subscriptions
        .filter(_.userId === user.id)
        .filter(_.groupId === group.id)
        .delete
    )

    result.map(_ => ())
  }
}
