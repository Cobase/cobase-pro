package cobase.play.post

import javax.inject.Inject

import cobase.post.{Post, PostService}
import cobase.twitter.TwitterService
import cobase.user.{GroupService, SubscriptionService, User}
import com.mohiva.play.silhouette.api.{Environment, Silhouette}
import com.mohiva.play.silhouette.impl.authenticators.SessionAuthenticator
import play.api.i18n.Messages

import scala.concurrent.Future

/**
 * The post controller.
 *
 * @param env The Silhouette environment.
 */
class PostController @Inject() (implicit val env: Environment[User, SessionAuthenticator],
                                groupService: GroupService,
                                postService: PostService,
                                twitterService: TwitterService,
                                subscriptionService: SubscriptionService)
  extends Silhouette[User, SessionAuthenticator] {

  def viewPosts(groupId: Long) = SecuredAction.async { implicit request =>
    Future.successful(
      groupService.findById(groupId) match {
        case Some(group) => Ok(views.html.group(
          request.identity,
          groupService.findGroupLinks,
          group,
          postService.findLatestPostsForGroup(groupId),
          twitterService.getGroupTweets(group.tags),
          subscriptionService.isUserSubscribedToGroup(request.identity, group),
          PostForm.form
        ))

        case None => NotFound("Group with id " + groupId + " not found")
      }
    )
  }

  def editPostForm(postId: Long) = SecuredAction.async { implicit request =>
    Future.successful {
      val postAndGroup = for {
        post <- postService.findById(postId)
        group <- groupService.findById(post.groupId)
      } yield (post, group)

      postAndGroup match {
        case Some((post, group)) =>
          val filledForm = PostForm.form.fill(PostFormData(post.content))

          Ok(views.html.editPost(request.identity, groupService.findGroupLinks, filledForm, group, post))

        case None => NotFound("Post with id " + postId + " not found")
      }
    }
  }

  def editPost(postId: Long) = SecuredAction.async { implicit request =>
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

        case None => NotFound("Post with id " + postId + " not found")
      }
    }
  }

  def addPost(groupId: Long) = SecuredAction.async { implicit request =>
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
                twitterService.getGroupTweets(group.tags),
                subscriptionService.isUserSubscribedToGroup(request.identity, group),
                formWithErrors
              ))
            },
            data => {
              val timestamp: Long = System.currentTimeMillis / 1000

              postService.save(
                Post(0, data.content, groupId, request.identity.fullName, timestamp) // TODO: fix the ugly hack with the ID
              )

              Redirect(routes.PostController.viewPosts(groupId))
            }
          )

        case None => NotFound("Group with id " + groupId + " not found")
      }
    )
  }

}
