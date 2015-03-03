package models.daos

import models.Group
import play.api.db.slick._
import play.api.db.slick.Config.driver.simple._
import models.daos.DBTableDefinitions._
import scala.concurrent.Future
import play.Logger
import play.api.Play.current

/**
 * Give access to the user object using Slick
 */
class GroupDAO {

  /**
   * Finds all groups.
   *
   * @return Seq[Group] List of all groups found.
   */
  def findAll() = {
    DB withSession { implicit session =>
      slickGroups.sortBy(_.title.toLowerCase.asc).list
    }
  }

  /**
   * Finds a group by its user id.
   *
   * @param groupId The id of the group to find.
   * @return The found group or None if no group for the given id could be found.
   */
  def findById(groupId: Long): Option[Group] = {
    DB withSession { implicit session =>
      slickGroups.filter(
        _.id === groupId
      ).firstOption
    }
  }

  /**
   * Finds all latest posts by group id.
   *
   * @param groupId The id of the group to find posts from.
   * @return The list of found posts.
   */
  def findLatestPostsForGroup(groupId: Long) = {
    DB withSession { implicit session =>
      slickPosts.filter(
        _.groupId === groupId
      ).sortBy(_.id.desc).list
    }
  }

  /**
   * Saves a group with the group data.
   *
   * @param group
   * @return Group
   */
  def save(group: Group) = {
    DB withSession { implicit session =>
      Future.successful {
        slickGroups.insert(group)
        group
      }
    }
  }

}