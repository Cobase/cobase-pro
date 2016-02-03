package cobase.group

import java.util.UUID
import javax.inject.Inject

import cobase.DBTables
import cobase.user.User
import play.api.db.slick._
import slick.driver.JdbcProfile
import slick.jdbc.GetResult

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
 * Give access to the user object using Slick
 */
class GroupRepository @Inject() (
  protected val dbConfigProvider: DatabaseConfigProvider
) extends HasDatabaseConfigProvider[JdbcProfile] with DBTables {
  import driver.api._

  def getGroups: Future[Seq[Group]] = {
    db.run(
      groups
        .filter(_.isActive === true)
        .sortBy(_.title.toLowerCase.asc)
        .result
    )
  }

  /**
   * Get list of groups and their post counts
   */
  def getGroupLinks: Future[Seq[GroupLink]] = {
    implicit val getGroupResult =
      GetResult(r =>
        GroupLink(UUID.fromString(r.nextString()), r.nextString(), r.nextString(), r.nextBoolean(), r.nextInt())
      )

    val query = sql"""
      SELECT g.id, g.title, g.tags, g.is_active, COUNT(p.id)
      FROM groups g
      LEFT JOIN posts p ON p.group_id = g.id
      WHERE g.is_active = true
      GROUP BY g.id, g.title
      ORDER BY upper(g.title)
    """.as[GroupLink]

    db.run(query)
  }

  def getGroupById(groupId: UUID): Future[Option[Group]] = {
    db.run(
      groups
        .filter(_.id === groupId)
        .filter(_.isActive === true)
        .result
        .headOption
    )
  }

  def addGroup(id: UUID, title: String, tags: String): Future[Group] = {
    val group = Group(id, title, tags, isActive = true)

    db.run(groups += group)
      .map(_ => group)
  }

  def updateGroup(group: Group, title: String, tags: String): Future[Group] = {
    val updatedGroup = group.copy(title = title, tags = tags)

    db.run(groups.filter(_.id === group.id).update(updatedGroup))
      .map(_ => updatedGroup)
  }

  def getGroupsLinksSubscribedTo(user: User): Future[Seq[GroupLink]] = {
    implicit val getResult =
      GetResult(r =>
        GroupLink(UUID.fromString(r.nextString()), r.nextString(), r.nextString(), r.nextBoolean(), r.nextInt())
      )

    val query = sql"""
      SELECT g.id, g.title, g.tags, g.is_active, COUNT(p.id)
      FROM groups g
      INNER JOIN subscriptions s ON s.group_id = g.id
      INNER JOIN posts p ON p.group_id = g.id
      WHERE s.user_id = CAST(${user.id.toString} AS uuid)
      AND g.is_active = true
      GROUP BY g.id
    """.as[GroupLink]

    db.run(query)
  }
}
