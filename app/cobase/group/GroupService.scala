package cobase.group

import javax.inject.Inject

import java.util.UUID

import scala.concurrent.Future

/**
 * Handles actions to groups.
 */
class GroupService @Inject() (groupDAO: GroupDAO) {

  def findAll: List[Group] = groupDAO.findAll

  def findGroupLinks: List[GroupLink] = groupDAO.findGroupLinks

  def findById(groupId: UUID): Option[Group] = groupDAO.findById(groupId)

  def add(group: Group): Future[Group] = groupDAO.add(group)

  def update(group: Group): Future[Group] = groupDAO.update(group)

}
