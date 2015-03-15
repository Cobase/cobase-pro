package models.daos

import models.{User, Group, Subscription}
import play.api.db.slick._
import play.api.db.slick.Config.driver.simple._
import scala.slick.jdbc.{GetResult, StaticQuery => Q}
import models.daos.DBTableDefinitions._
import scala.concurrent.Future
import play.api.Play.current

/**
 * Give access to the subscription object using Slick
 */
class SubscriptionDAO {

  /**
   * Check if given user is subscribed to given group.
   *
   * @param User user
   * @param Group group
   * @return boolean
   */
  def isUserSubscribedToGroup(user: User, group: Group): Boolean = {
    DB withSession { implicit session =>
      val subscription = slickSubscriptions.filter(
        _.userId === user.userID
      ).filter(
        _.groupId === group.id
      ).firstOption

      !subscription.isEmpty
    }
  }

  /**
   * Subscribe user to a group
   *
   * @param user User
   * @param group Group
   */
  def subscribeUserToGroup(user: User, group: Group): Unit = {
    DB withSession { implicit session =>
      slickSubscriptions.insert(
        Subscription(0, user.userID, group.id)
      )
    }
  }

  /**
   * Unsubscribe user from a group
   *
   * @param user User
   * @param group Group
   */
  def unsubscribeUserFromGroup(user: User, group: Group): Unit = {
    DB withSession { implicit session =>
      slickSubscriptions.filter(
        _.userId === user.userID
      ).filter(
        _.groupId === group.id
      ).delete
    }
  }

}
