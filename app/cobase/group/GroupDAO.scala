package cobase.group

import java.util.UUID
import javax.inject.Inject

import cobase.DBTableDefinitions
import play.api.db.slick._
import slick.driver.JdbcProfile
import slick.jdbc.GetResult

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 * Give access to the user object using Slick
 */
class GroupDAO @Inject() (protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] with DBTableDefinitions {
  import driver.api._

  def findAll: Future[Seq[Group]] = {
    db.run(slickGroups.sortBy(_.title.toLowerCase.asc).result)
  }

  /**
   * Get list of groups and their post counts
   */
  def findGroupLinks: Future[Seq[GroupLink]] = {
    implicit val getGroupResult =
      GetResult(r =>
        GroupLink(UUID.fromString(r.nextString()), r.nextString(), r.nextInt())
      )

    val query = sql"""
      SELECT g.id, g.title, COUNT(p.id)
      FROM groups g
      LEFT JOIN posts p ON p.group_id = g.id
      GROUP BY g.id, g.title
      ORDER BY upper(g.title)
    """.as[GroupLink]

    db.run(query)
  }

  def findById(groupId: UUID): Future[Option[Group]] = {
    db.run(slickGroups.filter(_.id === groupId).result.headOption)
  }

  def add(group: Group): Future[Group] = {
    db.run(slickGroups += group)
      .map(_ => group)
  }

  def update(group: Group): Future[Group] = {
    db.run(slickGroups.filter(_.id === group.id).update(group))
      .map(_ => group)
  }
}
