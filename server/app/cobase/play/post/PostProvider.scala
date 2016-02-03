package cobase.play.post

import java.util.UUID

import cobase.play.authentication.AuthenticatedRequest
import cobase.post.{Post, PostService}
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global

class PostRequest[A](val post: Post, request: AuthenticatedRequest[A])
  extends WrappedRequest[A](request) {

  val user = request.user
}

trait PostProvider extends Controller {
  def postService: PostService

  def PostAction(id: UUID) = new ActionRefiner[AuthenticatedRequest, PostRequest] {
    def refine[A](input: AuthenticatedRequest[A]) = {
      postService.getPostById(id).map {
        case Some(post) => Right(new PostRequest(post, input))
        case None => Left(NotFound(Json.toJson(Json.obj())))
      }
    }
  }
}
