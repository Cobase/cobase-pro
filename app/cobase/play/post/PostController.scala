package cobase.play.post

import java.util.UUID
import javax.inject.Inject

import cobase.group.{Group, GroupService}
import cobase.post.{Post, PostService}
import cobase.twitter.TwitterService
import cobase.user.{SubscriptionService, User}
import com.mohiva.play.silhouette.api.{Environment, Silhouette}
import com.mohiva.play.silhouette.impl.authenticators.CookieAuthenticator
import play.api.i18n.{Messages, MessagesApi}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.Json

import scala.concurrent.Future

class PostController @Inject() (
  implicit val env: Environment[User, CookieAuthenticator],
  val messagesApi: MessagesApi,
  groupService: GroupService,
  postService: PostService,
  twitterService: TwitterService,
  subscriptionService: SubscriptionService
) extends Silhouette[User, CookieAuthenticator] {

  def viewPosts(groupId: UUID) = SecuredAction.async { implicit request =>
    groupService.findById(groupId).flatMap {
      case Some(group) =>
        val futureGroupLinks = groupService.findGroupLinks
        val futurePosts = postService.findLatestPostsForGroup(groupId)
        val futureIsSubscribedToGroup = subscriptionService.isUserSubscribedToGroup(request.identity, group)

        for {
          groupLinks <- futureGroupLinks
          posts <- futurePosts
          isSubscribedToGroup <- futureIsSubscribedToGroup
        } yield {
          Ok(views.html.group(request.identity, groupLinks, group, posts, isSubscribedToGroup, PostForm.form))
        }

      case None =>
        for {
          groupLinks <- groupService.findGroupLinks
        } yield {
          NotFound(views.html.notFound(
            request.identity,
            groupLinks,
            "Group with id " + groupId + " not found"
          ))
        }
    }
  }

  def getPostsForGroup(groupId: UUID) = SecuredAction.async { implicit request =>
    groupService.findById(groupId).flatMap {
      case Some(group) =>
        implicit val postWrites = Json.writes[Post]

        val futurePosts = postService.findLatestPostsForGroup(groupId)

        futurePosts.map(posts => Ok(Json.toJson(posts)))

      case None => Future.successful(NotFound("No posts found"))
    }
  }

  def editPostForm(postId: UUID) = SecuredAction.async { implicit request =>
    getPostAndGroup(postId).flatMap {
        case Some((post, group)) => {
          val filledForm = PostForm.form.fill(PostFormData(post.content))

          for {
            groupLinks <- groupService.findGroupLinks
          } yield Ok(views.html.editPost(request.identity, groupLinks, filledForm, group, post))
        }

        case None =>
          for {
            groupLinks <- groupService.findGroupLinks
          } yield NotFound(views.html.notFound(request.identity, groupLinks, "Post with id " + postId + " not found"))
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

  def editPost(postId: UUID) = SecuredAction.async { implicit request =>
    getPostAndGroup(postId).flatMap {
      case Some((post, group)) =>
        PostForm.form.bindFromRequest.fold(
          formWithErrors => {
            for {
              groupLinks <- groupService.findGroupLinks
            } yield Ok(views.html.editPost(request.identity, groupLinks, formWithErrors, group, post))
          },
          data => {
            for {
              _ <- postService.update(post.copy(content = data.content))
            } yield Redirect(routes.PostController.viewPosts(group.id)).flashing("info" -> Messages("post.updated"))
          }
        )

      case None =>
        for {
          groupLinks <- groupService.findGroupLinks
        } yield NotFound(views.html.notFound(request.identity, groupLinks, "Post with id " + postId + " not found"))
    }
  }

  def addPost(groupId: UUID) = SecuredAction.async { implicit request =>
    groupService.findById(groupId).flatMap {
      case Some(group) =>
        PostForm.form.bindFromRequest.fold(
          formWithErrors => {
            val futureGroupLinks = groupService.findGroupLinks
            val futurePosts = postService.findLatestPostsForGroup(groupId)
            val futureIsSubscribedToGroup = subscriptionService.isUserSubscribedToGroup(request.identity, group)

            for {
              groupLinks <- futureGroupLinks
              posts <- futurePosts
              isSubscribedToGroup <- futureIsSubscribedToGroup
            } yield {
              Ok(views.html.group(request.identity, groupLinks, group, posts, isSubscribedToGroup, formWithErrors))
            }
          },
          data => {
            val timestamp: Long = System.currentTimeMillis

            for {
              _ <- postService.add(Post(UUID.randomUUID, data.content, groupId, request.identity.fullName, timestamp))
            } yield Redirect(routes.PostController.viewPosts(groupId))
          }
        )

      case None =>
        for {
          groupLinks <- groupService.findGroupLinks
        } yield NotFound(views.html.notFound(request.identity, groupLinks, "Group with id " + groupId + " not found"))
    }
  }
}
