package cobase.user

import javax.inject.Inject

import cobase.DBTableDefinitions
import cobase.group.Group
import play.api.db.slick._
import slick.driver.JdbcProfile

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 * Give access to the subscription object using Slick
 */
class SubscriptionDAO @Inject() (protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] with DBTableDefinitions {
  import driver.api._

  def isUserSubscribedToGroup(user: User, group: Group): Future[Boolean] = {
    val result = db.run(
      slickSubscriptions
        .filter(_.userId === user.id)
        .filter(_.groupId === group.id)
        .result
        .headOption
    )

    result.map(subscription => subscription.isDefined)
  }

  def subscribeUserToGroup(user: User, group: Group): Future[Subscription] = {
    db.run((slickSubscriptions returning slickSubscriptions.map(_.id)) += Subscription(0, user.id, group.id))
      .map(id => Subscription(id, user.id, group.id))
  }

  def unsubscribeUserFromGroup(user: User, group: Group): Future[Unit] = {
    val result = db.run(
      slickSubscriptions
        .filter(_.userId === user.id)
        .filter(_.groupId === group.id)
        .delete
    )

    result.map(_ => ())
  }
}
