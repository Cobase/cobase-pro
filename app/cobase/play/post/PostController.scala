package cobase.play.post

import java.util.UUID
import javax.inject.Inject

import cobase.authentication.AuthenticationService
import cobase.group.{Group, GroupService}
import cobase.play.user.SecuredController
import cobase.post.{DashboardPost, Post, PostService}
import cobase.twitter.TwitterService
import cobase.user.SubscriptionService
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.Json

import scala.concurrent.Future

class PostController @Inject() (
  val authenticationService: AuthenticationService,
  groupService: GroupService,
  postService: PostService,
  twitterService: TwitterService,
  subscriptionService: SubscriptionService
) extends SecuredController {

  def viewPosts(groupId: UUID) = AuthenticatedAction.async { implicit request =>
    groupService.findById(groupId).flatMap {
      case Some(group) =>
        val futureGroupLinks = groupService.findGroupLinks
        val futureIsSubscribedToGroup = subscriptionService.isUserSubscribedToGroup(request.user.user, group)

        for {
          groupLinks <- futureGroupLinks
          isSubscribedToGroup <- futureIsSubscribedToGroup
        } yield {
          Ok(views.html.group(request.user.user, groupLinks, group, isSubscribedToGroup, PostForm.form))
        }

      case None =>
        for {
          groupLinks <- groupService.findGroupLinks
        } yield {
          NotFound(views.html.notFound(
            request.user.user,
            groupLinks,
            "Group with id " + groupId + " not found"
          ))
        }
    }
  }

  def getPostsForGroup(groupId: UUID) = AuthenticatedAction.async { implicit request =>
    groupService.findById(groupId).flatMap {
      case Some(group) =>
        implicit val postWrites = Json.writes[Post]

        val futurePosts = postService.findLatestPostsForGroup(groupId)

        futurePosts.map(posts => Ok(Json.toJson(posts)))

      case None => Future.successful(NotFound("No posts found"))
    }
  }

  def editPostForm(postId: UUID) = AuthenticatedAction.async { implicit request =>
    getPostAndGroup(postId).flatMap {
        case Some((post, group)) => {
          val filledForm = PostForm.form.fill(PostFormData(post.content))

          for {
            groupLinks <- groupService.findGroupLinks
          } yield Ok(views.html.editPost(request.user.user, groupLinks, filledForm, group, post))
        }

        case None =>
          for {
            groupLinks <- groupService.findGroupLinks
          } yield NotFound(views.html.notFound(request.user.user, groupLinks, "Post with id " + postId + " not found"))
      }
  }

  private def getPostAndGroup(postId: UUID): Future[Option[(Post, Group)]] = {
    postService.findById(postId).flatMap {
      case Some(post) => groupService.findById(post.groupId).flatMap {
        case Some(group) => Future(Some((post, group)))
        case None => Future.successful(None)
      }
      case None => Future.successful(None)
    }
  }

  def editPost(postId: UUID) = AuthenticatedAction.async { implicit request =>
    getPostAndGroup(postId).flatMap {
      case Some((post, group)) =>
        PostForm.form.bindFromRequest.fold(
          formWithErrors => {
            for {
              groupLinks <- groupService.findGroupLinks
            } yield Ok(views.html.editPost(request.user.user, groupLinks, formWithErrors, group, post))
          },
          data => {
            for {
              _ <- postService.update(post.copy(content = data.content))
            } yield Redirect(routes.PostController.viewPosts(group.id))
//              .flashing("info" -> Messages("post.updated"))
          }
        )

      case None =>
        for {
          groupLinks <- groupService.findGroupLinks
        } yield NotFound(views.html.notFound(request.user.user, groupLinks, "Post with id " + postId + " not found"))
    }
  }

  def addPost(groupId: UUID) = AuthenticatedAction.async { implicit request =>
    groupService.findById(groupId).flatMap {
      case Some(group) =>
        PostForm.form.bindFromRequest.fold(
          formWithErrors => {
            val futureGroupLinks = groupService.findGroupLinks
            val futureIsSubscribedToGroup = subscriptionService.isUserSubscribedToGroup(request.user.user, group)

            for {
              groupLinks <- futureGroupLinks
              isSubscribedToGroup <- futureIsSubscribedToGroup
            } yield {
              Ok(views.html.group(request.user.user, groupLinks, group, isSubscribedToGroup, formWithErrors))
            }
          },
          data => {
            val timestamp: Long = System.currentTimeMillis

            for {
              _ <- postService.add(Post(UUID.randomUUID, data.content, groupId, request.user.user.fullName, timestamp, true))
            } yield Redirect(routes.PostController.viewPosts(groupId))
          }
        )

      case None =>
        for {
          groupLinks <- groupService.findGroupLinks
        } yield NotFound(views.html.notFound(request.user.user, groupLinks, "Group with id " + groupId + " not found"))
    }
  }

  def getDashboardPosts = AuthenticatedAction.async { implicit request =>
    implicit val postWrites = Json.writes[DashboardPost]

    postService.getDashboardPosts(request.user.user)
      .map(dashboardPosts => Ok(Json.toJson(dashboardPosts)))

  }
}
