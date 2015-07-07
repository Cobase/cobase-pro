package cobase.user

import javax.inject.Inject

import cobase.group.Group

/**
 * Handles actions to subscriptions.
 */
class SubscriptionService @Inject() (subscriptionDAO: SubscriptionDAO) {

  def isUserSubscribedToGroup(user: User, group: Group): Boolean = {
    subscriptionDAO.isUserSubscribedToGroup(user, group)
  }

  def subscribeUserToGroup(user: User, group: Group): Unit = {
    subscriptionDAO.subscribeUserToGroup(user, group)
  }

  def unsubscribeUserFromGroup(user: User, group: Group): Unit = {
    subscriptionDAO.unsubscribeUserFromGroup(user, group)
  }

}
