package cobase.play.post

import javax.inject.Inject

import cobase.group.GroupService
import cobase.post.{Post, PostService}
import cobase.twitter.TwitterService
import cobase.user.{SubscriptionService, User}
import com.mohiva.play.silhouette.api.{Environment, Silhouette}
import com.mohiva.play.silhouette.impl.authenticators.SessionAuthenticator
import play.api.i18n.Messages

import scala.concurrent.Future
import java.util.UUID

class PostController @Inject() (implicit val env: Environment[User, SessionAuthenticator],
                                groupService: GroupService,
                                postService: PostService,
                                twitterService: TwitterService,
                                subscriptionService: SubscriptionService)
  extends Silhouette[User, SessionAuthenticator] {

  def viewPosts(groupId: UUID) = SecuredAction.async { implicit request =>
    Future.successful(
      groupService.findById(groupId) match {
        case Some(group) => Ok(views.html.group(
          request.identity,
          groupService.findGroupLinks,
          group,
          postService.findLatestPostsForGroup(groupId),
          subscriptionService.isUserSubscribedToGroup(request.identity, group),
          PostForm.form
        ))

        case None => NotFound(views.html.notFound(
          request.identity,
          groupService.findGroupLinks,
          "Group with id " + groupId + " not found"
        ))
      }
    )
  }

  def editPostForm(postId: UUID) = SecuredAction.async { implicit request =>
    Future.successful {
      val postAndGroup = for {
        post <- postService.findById(postId)
        group <- groupService.findById(post.groupId)
      } yield (post, group)

      postAndGroup match {
        case Some((post, group)) => {
          val filledForm = PostForm.form.fill(PostFormData(post.content))
          Ok(views.html.editPost(request.identity, groupService.findGroupLinks, filledForm, group, post))
        }

        case None => NotFound(views.html.notFound(
          request.identity,
          groupService.findGroupLinks,
          "Post with id " + postId + " not found"
        ))
      }
    }
  }

  def editPost(postId: UUID) = SecuredAction.async { implicit request =>
    Future.successful {
      val postAndGroup = for {
        post <- postService.findById(postId)
        group <- groupService.findById(post.groupId)
      } yield (post, group)

      postAndGroup match {
        case Some((post, group)) =>
          PostForm.form.bindFromRequest.fold(
            formWithErrors => {
              Ok(views.html.editPost(request.identity, groupService.findGroupLinks, formWithErrors, group, post))
            },
            data => {
              postService.update(Post(post.id, data.content, post.groupId, post.createdBy, post.createdTimestamp))

              Redirect(routes.PostController.viewPosts(group.id)).flashing("info" -> Messages("post.updated"))
            }
          )

        case None => NotFound(views.html.notFound(
          request.identity,
          groupService.findGroupLinks,
          "Post with id " + postId + " not found"
        ))
      }
    }
  }

  def addPost(groupId: UUID) = SecuredAction.async { implicit request =>
    Future.successful(
      groupService.findById(groupId) match {
        case Some(group) =>
          PostForm.form.bindFromRequest.fold(
            formWithErrors => {
              Ok(views.html.group(
                request.identity,
                groupService.findGroupLinks,
                group,
                postService.findLatestPostsForGroup(groupId),
                subscriptionService.isUserSubscribedToGroup(request.identity, group),
                formWithErrors
              ))
            },
            data => {
              val timestamp: Long = System.currentTimeMillis / 1000

              postService.save(
                Post(UUID.randomUUID, data.content, groupId, request.identity.fullName, timestamp)
              )

              Redirect(routes.PostController.viewPosts(groupId))
            }
          )

        case None => NotFound(views.html.notFound(
          request.identity,
          groupService.findGroupLinks,
          "Group with id " + groupId + " not found"
        ))
      }
    )
  }

}
