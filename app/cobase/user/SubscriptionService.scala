package cobase.user

import javax.inject.Inject

import cobase.group.Group

import scala.concurrent.Future

/**
 * Handles actions to subscriptions.
 */
class SubscriptionService @Inject() (subscriptionDAO: SubscriptionDAO) {

  def isUserSubscribedToGroup(user: User, group: Group): Future[Boolean] = {
    subscriptionDAO.isUserSubscribedToGroup(user, group)
  }

  def subscribeUserToGroup(user: User, group: Group): Future[Subscription] = {
    subscriptionDAO.subscribeUserToGroup(user, group)
  }

  def unsubscribeUserFromGroup(user: User, group: Group): Future[Unit] = {
    subscriptionDAO.unsubscribeUserFromGroup(user, group)
  }

}
