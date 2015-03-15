package controllers

import javax.inject.Inject
import com.mohiva.play.silhouette.api.{Environment, Silhouette}
import com.mohiva.play.silhouette.impl.authenticators.SessionAuthenticator
import play.api.i18n.Messages
import scala.concurrent.Future

import forms.{PostFormData, PostForm}
import models.User
import models.Post
import models.services.{GroupService, PostService, TwitterService}
import models.exceptions._

/**
 * The post controller.
 *
 * @param env The Silhouette environment.
 */
class PostController @Inject() (implicit val env: Environment[User, SessionAuthenticator],
                                groupService: GroupService,
                                postService: PostService,
                                twitterService: TwitterService)
  extends Silhouette[User, SessionAuthenticator] {

  /**
   * Display edit post form.
   *
   * @return The result to display.
   */
  def editPostForm(postId: Long) = SecuredAction.async { implicit request =>
    val groupLinks = groupService.findGroupLinks
    val post = postService.findById(postId)

    if (post.isEmpty) throw CobaseException("Post with id " + postId + " not found")

    val group = groupService.findById(post.get.groupId)
    val filledForm = PostForm.form.fill(PostFormData(post.get.content))

    Future.successful(Ok(views.html.editPost(request.identity, groupLinks, filledForm, group.get, post.get)))
  }

  /**
   * Handles the update of a post.
   *
   * @return The result to display.
   */
  def updatePost(postId: Long) = SecuredAction.async { implicit request =>
    val groupLinks = groupService.findGroupLinks
    val post = postService.findById(postId)

    if (post.isEmpty) throw CobaseException("Post with id " + postId + " not found")

    val group = groupService.findById(post.get.groupId)

    PostForm.form.bindFromRequest.fold(
      formWithErrors => {
        println(formWithErrors.errors)
        Future.successful(
          Ok(
            views.html.editPost(request.identity, groupLinks, formWithErrors, group.get, post.get)
          )
        )
      },
      data => {
        postService.update(
          Post(post.get.id, data.content, post.get.groupId, post.get.createdBy, post.get.createdTimestamp)
        )
        Future.successful(
          Redirect(
            routes.GroupController.listGroupPosts(group.get.id)).flashing("info" -> Messages("post.updated")
          )
        )
      }
    )
  }

  /**
   * Handles the creation of a post into group.
   *
   * @return The result to display.
   */
  def createPost(groupId: Long) = SecuredAction.async { implicit request =>
    val groupLinks = groupService.findGroupLinks
    val group = groupService.findById(groupId)

    if (group.isEmpty) throw CobaseException("Group with id " + groupId + " not found")

    val posts = postService.findLatestPostsForGroup(groupId)
    val tweets = twitterService.getGroupTweets(group.get.tags)

    PostForm.form.bindFromRequest.fold(
      formWithErrors => {
        Future.successful(
          Ok(
            views.html.group(request.identity, groupLinks, group, posts, tweets, formWithErrors)
          )
        )
      },
      data => {
        val timestamp: Long = System.currentTimeMillis / 1000
        postService.save(
          Post(0, data.content, groupId, request.identity.fullName, timestamp) // TODO: fix the ugly hack with the ID
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
