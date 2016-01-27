package cobase.user

import javax.inject.Inject

import cobase.group.Group

import scala.concurrent.Future

/**
 * Handles actions to subscriptions.
 */
class SubscriptionService @Inject() (subscriptionRepository: SubscriptionRepository) {

  def isUserSubscribedToGroup(user: User, group: Group): Future[Boolean] = {
    subscriptionRepository.isUserSubscribedToGroup(user, group)
  }

  def subscribeUserToGroup(user: User, group: Group): Future[Subscription] = {
    subscriptionRepository.subscribeUserToGroup(user, group)
  }

  def unsubscribeUserFromGroup(user: User, group: Group): Future[Unit] = {
    subscriptionRepository.unsubscribeUserFromGroup(user, group)
  }

}
