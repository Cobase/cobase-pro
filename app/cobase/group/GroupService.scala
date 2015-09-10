package cobase.group

import javax.inject.Inject

import java.util.UUID

import scala.concurrent.Future

/**
 * Handles actions to groups.
 */
class GroupService @Inject() (groupDAO: GroupDAO) {

  def findAll: Future[Seq[Group]] = groupDAO.findAll

  def findGroupLinks: Future[Seq[GroupLink]] = groupDAO.findGroupLinks

  def findById(groupId: UUID): Future[Option[Group]] = groupDAO.findById(groupId)

  def add(group: Group): Future[Group] = groupDAO.add(group)

  def update(group: Group): Future[Group] = groupDAO.update(group)

}
