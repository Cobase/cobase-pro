package cobase.play.group

import java.util.UUID

import cobase.group.{Group, GroupService}
import cobase.play.authentication.AuthenticatedRequest
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global

class GroupRequest[A](val group: Group, request: AuthenticatedRequest[A])
  extends WrappedRequest[A](request) {

  val user = request.user
}

trait GroupProvider extends Controller {
  def groupService: GroupService

  def GroupAction(id: UUID) = new ActionRefiner[AuthenticatedRequest, GroupRequest] {
    def refine[A](input: AuthenticatedRequest[A]) = {
      groupService.findById(id).map {
        case Some(group) => Right(new GroupRequest(group, input))
        case None => Left(NotFound(Json.toJson(Json.obj())))
      }
    }
  }
}
