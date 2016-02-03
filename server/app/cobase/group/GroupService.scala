package cobase.group

import java.util.UUID
import javax.inject.Inject

import cobase.user.User

import scala.concurrent.Future

/**
 * Handles actions to groups.
 */
class GroupService @Inject() (groupRepository: GroupRepository) {
  def getGroups: Future[Seq[Group]] = groupRepository.getGroups

  def getGroupLinks: Future[Seq[GroupLink]] = groupRepository.getGroupLinks

  def getGroupById(groupId: UUID): Future[Option[Group]] = groupRepository.getGroupById(groupId)

  def addGroup(data: AddGroupRequest): Future[Group] = {
    groupRepository.addGroup(UUID.randomUUID, data.title, data.tags)
  }

  def updateGroup(group: Group, data: UpdateGroupRequest): Future[Group] = {
    groupRepository.updateGroup(group, data.title, data.tags)
  }

  def getGroupsLinksSubscribedTo(user: User): Future[Seq[GroupLink]] = {
    groupRepository.getGroupsLinksSubscribedTo(user)
  }
}
