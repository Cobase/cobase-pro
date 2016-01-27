package cobase.group

import javax.inject.Inject
import java.util.UUID
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Handles actions to groups.
 */
class GroupService @Inject() (groupRepository: GroupRepository) {

  def findAll: Future[Seq[Group]] = groupRepository.findAll

  def findGroupLinks: Future[Seq[GroupLink]] = groupRepository.findGroupLinks

  def findById(groupId: UUID): Future[Option[Group]] = groupRepository.findById(groupId)

  def add(group: Group): Future[Group] = groupRepository.add(group)

  def update(group: Group): Future[Group] = groupRepository.update(group)

  def addGroup(data: AddGroupRequest): Future[Group] = {
    groupRepository
      .add(Group(
        UUID.randomUUID,
        data.title,
        data.tags,
        true
      ))
      .map { group =>
        group
      }
  }

}
