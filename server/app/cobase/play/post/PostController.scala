package cobase.play.post

import java.util.UUID
import javax.inject.Inject

import cobase.authentication.AuthenticationService
import cobase.group.GroupService
import cobase.play.group.GroupProvider
import cobase.play.user.SecuredController
import cobase.post._
import cobase.twitter.TwitterService
import cobase.user.SubscriptionService
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.{JsError, Json}

import scala.concurrent.Future

class PostController @Inject() (
  val authenticationService: AuthenticationService,
  val groupService: GroupService,
  val postService: PostService,
  twitterService: TwitterService,
  subscriptionService: SubscriptionService
) extends SecuredController with PostProvider with GroupProvider {

  def AuthenticatedGroupAction(groupId: UUID) = AuthenticatedAction andThen GroupAction(groupId)

  def getPostsForGroup(groupId: UUID) = AuthenticatedGroupAction(groupId).async { implicit request =>
    postService.findLatestPostsForGroup(request.group)
      .map(posts => Ok(Json.toJson(posts.map(post => PostResponse.fromPost(post)))))
  }

  def AuthenticatedPostAction(postId: UUID) = AuthenticatedAction andThen PostAction(postId)

  def updatePost(postId: UUID) = AuthenticatedPostAction(postId).async(parse.json) { implicit request =>
    request.body.validate[EditPostRequest].fold(
      errors => Future.successful(BadRequest(Json.obj("errors" -> JsError.toJson(errors)))),
      editPostRequest => postService.update(request.post, editPostRequest.content)
        .map(post => Ok(Json.toJson(PostResponse.fromPost(post))))
    )
  }

  def addPost(groupId: UUID) = AuthenticatedGroupAction(groupId).async(parse.json) { implicit request =>
    request.body.validate[AddPostRequest].fold(
      errors => Future.successful(BadRequest(Json.obj("errors" -> JsError.toJson(errors)))),
      addPostRequest => {
        postService.add(addPostRequest.content, request.group, request.user.user)
            .map(post => Created(Json.toJson(PostResponse.fromPost(post))))
      }
    )
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
