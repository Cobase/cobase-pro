package cobase.group

import cobase.DBTableDefinitions._
import play.api.Play.current
import play.api.db.slick.Config.driver.simple._
import play.api.db.slick._

import scala.concurrent.Future
import scala.slick.jdbc.{GetResult, StaticQuery => Q}

import java.util.UUID

/**
 * Give access to the user object using Slick
 */
class GroupDAO {

  def findAll: List[Group] = {
    DB withSession { implicit session =>
      slickGroups.sortBy(_.title.toLowerCase.asc).list
    }
  }

  /**
   * Get list of groups and their post counts
   */
  def findGroupLinks: List[GroupLink] = {
    DB withSession { implicit session =>
      implicit val getGroupResult =
        GetResult(r =>
          GroupLink(UUID.fromString(r.nextString()), r.nextString(), r.nextInt())
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

  def findById(groupId: UUID): Option[Group] = {
    DB withSession { implicit session =>
      slickGroups.filter(_.id === groupId).firstOption
    }
  }

  def add(group: Group): Future[Group] = {
    DB withSession { implicit session =>
      Future.successful {
        slickGroups.insert(group)
        group
      }
    }
  }

  def update(group: Group): Future[Group] = {
    DB withSession { implicit session =>
      Future.successful {
        slickGroups.filter(_.id === group.id).update(group)
        group
      }
    }
  }

}
