package models.daos

import models.Group
import models.GroupLink
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
   * Find all groups.
   *
   * @return Seq[Group]
   */
  def findAll() = {
    DB withSession { implicit session =>
      slickGroups.sortBy(_.title.toLowerCase.asc).list
    }
  }

  /**
   * Find all groups with the count of their posts.
   *
   * @return List[GroupLink]
   */
  def findGroupLinks() = {
    DB withSession { implicit session =>
      val q = (for {
        groups <- slickGroups
        posts <- slickPosts if groups.id === posts.groupId
      } yield (groups, posts)).groupBy(_._1.id)

      q.map {
        case (groupTitle, groupPosts) => 
          GroupLink(0, groupTitle.asInstanceOf[String], groupPosts.length.asInstanceOf[Int])
      }.list
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
