package controllers

import javax.inject.Inject
import com.mohiva.play.silhouette.api.{Environment, Silhouette}
import com.mohiva.play.silhouette.impl.authenticators.SessionAuthenticator
import play.api.i18n.Messages
import scala.concurrent.Future

import forms.{GroupForm, PostForm}
import models.User
import models.Group
import models.Post
import models.services.{GroupService, PostService}
import models.exceptions._

/**
 * The group controller.
 *
 * @param env The Silhouette environment.
 */
class GroupController @Inject() (implicit val env: Environment[User, SessionAuthenticator], groupService: GroupService, postService: PostService)
  extends Silhouette[User, SessionAuthenticator] {

  /**
   * Handles the index action.
   *
   * @return The result to display.
   */
  def index = SecuredAction.async { implicit request =>
    val groupLinks = groupService.findGroupLinks
    
    Future.successful(Ok(views.html.newGroup(request.identity, groupLinks, GroupForm.form)))
  }

  /**
   * Handles the creation of a group.
   *
   * @return The result to display.
   */
  def createGroup = SecuredAction.async { implicit request =>
    val groupLinks = groupService.findGroupLinks

    GroupForm.form.bindFromRequest.fold(
      formWithErrors => {
        println(formWithErrors.errors)
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
  def listGroupPosts(groupId: Long) = SecuredAction.async { implicit request =>
    val groupLinks = groupService.findGroupLinks
    val group = groupService.findById(groupId)
    val posts = postService.findLatestPostsForGroup(groupId)

    if (group.isEmpty) throw CobaseException("Group with id " + groupId + " not found")

    Future.successful(Ok(views.html.group(request.identity, groupLinks, group, posts, PostForm.form)))
  }

  /**
   * Handles the creation of a post into group.
   *
   * @return The result to display.
   */
  def createGroupPost(groupId: Long) = SecuredAction.async { implicit request =>
    val groupLinks = groupService.findGroupLinks
    val group = groupService.findById(groupId)
    val posts = postService.findLatestPostsForGroup(groupId)

    if (group.isEmpty) throw CobaseException("Group with id " + groupId + " not found")
    
    PostForm.form.bindFromRequest.fold(
      formWithErrors => {
        Future.successful(
          Ok(
            views.html.group(request.identity, groupLinks, group, posts, formWithErrors)
          )
        )
      },
      data => {
        postService.save(
          Post(0, data.content, groupId, request.identity.fullName, 234234234) // TODO: fix the ugly hack with the ID
        )
        Future.successful(
          Redirect(
            routes.GroupController.listGroupPosts(groupId)
          )
        )
      }
    )
  }
  
}
