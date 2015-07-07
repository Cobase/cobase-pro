package cobase.user

import cobase.DBTableDefinitions._
import cobase.group.Group
import play.api.Play.current
import play.api.db.slick.Config.driver.simple._
import play.api.db.slick._

/**
 * Give access to the subscription object using Slick
 */
class SubscriptionDAO {

  def isUserSubscribedToGroup(user: User, group: Group): Boolean = {
    DB withSession { implicit session =>
      val subscription = slickSubscriptions.filter(
        _.userId === user.id
      ).filter(
        _.groupId === group.id
      ).firstOption

      subscription.isDefined
    }
  }

  def subscribeUserToGroup(user: User, group: Group): Unit = {
    DB withSession { implicit session =>
      slickSubscriptions.insert(
        Subscription(0, user.id, group.id)
      )
    }
  }

  def unsubscribeUserFromGroup(user: User, group: Group): Unit = {
    DB withSession { implicit session =>
      slickSubscriptions.filter(
        _.userId === user.id
      ).filter(
        _.groupId === group.id
      ).delete
    }
  }

}
