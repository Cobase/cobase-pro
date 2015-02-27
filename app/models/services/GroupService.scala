package models.services

import javax.inject.Inject

import models.Group
import models.daos.slick.GroupDAOSlick
import play.api.libs.concurrent.Execution.Implicits._
import scala.concurrent.Future

/**
 * Handles actions to groups.
 *
 * @param groupDAO The group DAO implementation.
 */
class GroupService @Inject() (groupDAO: GroupDAOSlick) {

  /**
   * Retrieves a group that matches the specified id.
   *
   * @param groupId The id to retrieve a group.
   * @return The retrieved group or None if no group could be retrieved for the given id.
   */
  def find(groupId: Long): Future[Option[Group]] = groupDAO.find(groupId)

}
