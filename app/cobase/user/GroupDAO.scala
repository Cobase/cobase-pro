package cobase.user

import cobase.DBTableDefinitions
import DBTableDefinitions._
import play.api.Play.current
import play.api.db.slick.Config.driver.simple._
import play.api.db.slick._

import scala.concurrent.Future
import scala.slick.jdbc.{GetResult, StaticQuery => Q}

/**
 * Give access to the user object using Slick
 */
class GroupDAO {

  /**
   * Find all groups.
   *
   * @return Seq[Group]
   */
  def findAll = {
    DB withSession { implicit session =>
      slickGroups.sortBy(_.title.toLowerCase.asc).list
    }
  }

  /**
   * Get list of groups and their post counts
   *
   * @return Seq[GroupLink]
   */
  def findGroupLinks = {
    DB withSession { implicit session =>

      implicit val getGroupResult =
        GetResult(r =>
          GroupLink(r.nextLong(), r.nextString().toString(), r.nextInt())
        )

      val groupLinks = Q[GroupLink] +
        """
          SELECT
            g.id, g.title, COUNT(p.id)
          FROM
            groups g
          LEFT JOIN
            posts p
          ON
            p.group_id = g.id
          GROUP BY
            g.id, g.title
          ORDER BY upper(g.title)
        """

      groupLinks.list
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

  /**
   * Updates a group with the group data.
   *
   * @param group
   * @return Group
   */
  def update(group: Group) = {
    DB withSession { implicit session =>
      Future.successful {
        slickGroups.filter(_.id === group.id).update(group)
        group
      }
    }
  }

}
