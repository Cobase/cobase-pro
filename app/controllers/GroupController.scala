package controllers

import javax.inject.Inject
import com.mohiva.play.silhouette.api.{Environment, Silhouette}
import com.mohiva.play.silhouette.impl.authenticators.SessionAuthenticator
import play.api.i18n.Messages
import scala.concurrent.Future

import forms.GroupForm
import models.User
import models.Group
import models.services.GroupService
import models.exceptions._

/**
 * The group controller.
 *
 * @param env The Silhouette environment.
 */
class GroupController @Inject() (implicit val env: Environment[User, SessionAuthenticator], groupService: GroupService)
  extends Silhouette[User, SessionAuthenticator] {

  /**
   * Handles the index action.
   *
   * @return The result to display.
   */
  def index = SecuredAction.async { implicit request =>
    val groups = groupService.findAll
    
    Future.successful(Ok(views.html.newGroup(request.identity, groups, GroupForm.form)))
  }

  /**
   * Handles the index action.
   *
   * @return The result to display.
   */
  def create = SecuredAction.async { implicit request =>
    val groups = groupService.findAll

    GroupForm.form.bindFromRequest.fold(
      formWithErrors => {
        Future.successful(
          Ok(
            views.html.newGroup(request.identity, groups, formWithErrors)
          )
        )
      },
      data => {
        groupService.save(
          Group(0, data.title, data.description) // TODO: fix the ugly hack with the ID
        )
        Future.successful(
          Redirect(
            routes.ApplicationController.index()).flashing("info" -> Messages("group.created")
          )
        )
      }
    )
  }

  /**
   * Get posts for a given group
   * *
   * @param groupId
   * @return
   */
  def listPosts(groupId: Long) = SecuredAction.async { implicit request =>
    val groups = groupService.findAll
    val group = groupService.findById(groupId)
    val posts = groupService.findLatestPostsForGroup(groupId)

    if (group.isEmpty) throw new CobaseException("Group with id " + groupId + " not found")
    
    Future.successful(Ok(views.html.group(request.identity, groups, group, posts)))
  }

}
