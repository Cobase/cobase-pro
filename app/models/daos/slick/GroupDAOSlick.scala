package models.daos.slick

import models.Group
import play.api.db.slick._
import play.api.db.slick.Config.driver.simple._
import models.daos.slick.DBTableDefinitions._
import scala.concurrent.Future
import play.Logger
import play.api.Play.current

/**
 * Give access to the user object using Slick
 */
class GroupDAOSlick {
  /**
   * Finds a group by its user id.
   *
   * @param groupId The id of the group to find.
   * @return The found group or None if no group for the given id could be found.
   */
  def find(groupId: Long) = {
    DB withSession { implicit session =>
      Future.successful {
        slickGroups.filter(
          _.id === groupId
        ).firstOption match {
          case Some(group) =>
            Some(
              Group(group.id, group.title, group.description)
            )
          case None => None
        }
      }
    }
  }
}
