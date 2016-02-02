package cobase.play.post

import java.util.UUID
import javax.inject.Inject

import cobase.authentication.AuthenticationService
import cobase.group.GroupService
import cobase.play.user.SecuredController
import cobase.post._
import cobase.twitter.TwitterService
import cobase.user.SubscriptionService
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.{JsError, Json}

import scala.concurrent.Future

class PostController @Inject() (
  val authenticationService: AuthenticationService,
  groupService: GroupService,
  postService: PostService,
  twitterService: TwitterService,
  subscriptionService: SubscriptionService
) extends SecuredController {

  def getPostsForGroup(groupId: UUID) = AuthenticatedAction.async { implicit request =>
    groupService.findById(groupId).flatMap {
      case Some(group) =>
        postService.findLatestPostsForGroup(groupId)
          .map(posts => Ok(Json.toJson(posts.map(post => PostResponse.fromPost(post)))))

      case None => Future.successful(NotFound(Json.obj()))
    }
  }

  def updatePost(postId: UUID) = AuthenticatedAction.async(parse.json) { implicit request =>
    postService.findById(postId).flatMap {
      case Some(post) =>
        request.body.validate[EditPostRequest].fold(
          errors => Future.successful(BadRequest(Json.obj("errors" -> JsError.toJson(errors)))),
          editPostRequest => postService.update(post, editPostRequest.content)
            .map(post => Ok(Json.toJson(PostResponse.fromPost(post))))
        )

      case None => Future.successful(NotFound(Json.obj()))
    }
  }

  def addPost(groupId: UUID) = AuthenticatedAction.async(parse.json) { implicit request =>
    groupService.findById(groupId).flatMap {
      case Some(group) =>
        request.body.validate[AddPostRequest].fold(
          errors => Future.successful(BadRequest(Json.obj("errors" -> JsError.toJson(errors)))),
          addPostRequest => {
            postService.add(addPostRequest.content, group, request.user.user)
                .map(post => Created(Json.toJson(PostResponse.fromPost(post))))
          }
        )

      case None => Future.successful(NotFound(Json.obj()))
    }
  }

  def getPosts = AuthenticatedAction.async { implicit request =>
    val futurePosts = request.getQueryString("phrase") match {
      case Some(phrase) => postService.findByPhrase(phrase)

      case None => postService.findAll
    }

    futurePosts.map { posts =>
      Ok(Json.toJson(posts.map(post => PostResponse.fromPost(post))))
    }
  }
}
