package controllers

import javax.inject.Inject

import com.mohiva.play.silhouette.api.{Environment, Silhouette}
import com.mohiva.play.silhouette.impl.authenticators.SessionAuthenticator
import forms.GroupForm
import models.User
import models.Group
import models.services.GroupService
import play.api.i18n.Messages

import scala.concurrent.Future

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
}
