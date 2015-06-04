package cobase.play.user

import javax.inject.Inject

import cobase.CobaseException
import cobase.play.post.PostForm
import cobase.post.PostService
import cobase.twitter.TwitterService
import cobase.user._
import com.mohiva.play.silhouette.api.{Environment, Silhouette}
import com.mohiva.play.silhouette.impl.authenticators.SessionAuthenticator
import play.api.i18n.Messages

import scala.concurrent.Future

/**
 * The group controller.
 *
 * @param env The Silhouette environment.
 */
class GroupController @Inject() (implicit val env: Environment[User, SessionAuthenticator],
                                 groupService: GroupService,
                                 postService: PostService,
                                 subscriptionService: SubscriptionService,
                                 twitterService: TwitterService)
  extends Silhouette[User, SessionAuthenticator] {

  def addGroupForm() = SecuredAction.async { implicit request =>
    val groupLinks = groupService.findGroupLinks

    Future.successful(Ok(views.html.newGroup(request.identity, groupLinks, GroupForm.form)))
  }

  def addGroup() = SecuredAction.async { implicit request =>
    val groupLinks = groupService.findGroupLinks

    GroupForm.form.bindFromRequest.fold(
      formWithErrors => {
        Future.successful(
          Ok(
            views.html.newGroup(request.identity, groupLinks, formWithErrors)
          )
        )
      },
      data => {
        groupService.save(
          Group(0, data.title, data.tags) // TODO: fix the ugly hack with the ID
        )
        Future.successful(
          Redirect(
            cobase.play.user.routes.ApplicationController.index()).flashing("info" -> Messages("group.created")
          )
        )
      }
    )
  }

  /**
   * Display edit group form.
   *
   * @return The result to display.
   */
  def editGroupForm(groupId: Long) = SecuredAction.async { implicit request =>
    val groupLinks = groupService.findGroupLinks
    val group = groupService.findById(groupId)

    if (group.isEmpty) throw CobaseException("Group with id " + groupId + " not found")

    val filledForm = GroupForm.form.fill(GroupFormData(group.get.title, group.get.tags))

    Future.successful(Ok(views.html.editGroup(request.identity, groupLinks, filledForm, group.get)))
  }

  def editGroup(groupId: Long) = SecuredAction.async { implicit request =>
    val groupLinks = groupService.findGroupLinks
    val group = groupService.findById(groupId)

    if (group.isEmpty) throw CobaseException("Group with id " + groupId + " not found")

    GroupForm.form.bindFromRequest.fold(
      formWithErrors => {
        Future.successful(
          Ok(
            views.html.editGroup(request.identity, groupLinks, formWithErrors, group.get)
          )
        )
      },
      data => {
        groupService.update(
          Group(group.get.id, data.title, data.tags)
        )
        Future.successful(
          Redirect(
            cobase.play.post.routes.PostController.viewPosts(group.get.id)).flashing("info" -> Messages("group.updated")
          )
        )
      }
    )
  }

  /**
   * Subscribe user to a group
   *
   * @param groupId
   * @return
   */
  def subscribe(groupId: Long) = SecuredAction.async { implicit request =>
    val group = groupService.findById(groupId)

    if (group.isEmpty) throw CobaseException("Group with id " + groupId + " not found")

    subscriptionService.subscribeUserToGroup(request.identity, group.get)

    Future.successful(
      Redirect(
        cobase.play.user.routes.ApplicationController.index()).flashing("info" -> Messages("group.subscribe")
      )
    )
  }

  /**
   * Unsubscribe user from a group
   *
   * @param groupId
   * @return
   */
  def unsubscribe(groupId: Long) = SecuredAction.async { implicit request =>
    val group = groupService.findById(groupId)

    if (group.isEmpty) throw CobaseException("Group with id " + groupId + " not found")

    subscriptionService.unsubscribeUserFromGroup(request.identity, group.get)

    Future.successful(
      Redirect(
        cobase.play.user.routes.ApplicationController.index()).flashing("info" -> Messages("group.unsubscribe")
      )
    )
  }

}
