package cobase.play.group

import java.util.UUID
import javax.inject.{Inject, Singleton}

import cobase.authentication.AuthenticationService
import cobase.group._
import cobase.play.user.SecuredController
import cobase.post.PostService
import cobase.twitter.{Tweet, TwitterService}
import cobase.user._
import play.api.libs.json._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class GroupController @Inject() (
  val authenticationService: AuthenticationService,
  groupService: GroupService,
  postService: PostService,
  subscriptionService: SubscriptionService,
  twitterService: TwitterService
) extends SecuredController {

  def addGroup = AuthenticatedAction.async(parse.json) { implicit request =>
    request.body.validate[AddGroupRequest].fold(
      errors => Future.successful(BadRequest(Json.obj("errors" -> JsError.toJson(errors)))),
      addGroupRequest => {
        implicit val groupWrites = Json.format[Group]
        groupService.addGroup(addGroupRequest)
          .map(group => Created(Json.toJson(group)))
      }
    )
  }

  def getGroups = AuthenticatedAction.async { implicit request =>
    groupService.findGroupLinks.map { groupLinks =>
      Ok(Json.toJson(groupLinks.map(groupLink => GroupLinkResponse.fromGroup(groupLink))))
    }
  }

  def getSubscribedGroups = AuthenticatedAction.async { implicit request =>
    groupService.getGroupsLinksSubscribedTo(request.user.user).map { groupLinks =>
      Ok(Json.toJson(groupLinks.map(groupLink => GroupLinkResponse.fromGroup(groupLink))))
    }
  }

  def updateGroup(groupId: UUID) = AuthenticatedAction.async(parse.json) { implicit request =>
    groupService.findById(groupId).flatMap {
      case Some(group) =>
        request.body.validate[UpdateGroupRequest].fold(
          errors => Future.successful(BadRequest(Json.obj("errors" -> JsError.toJson(errors)))),
          updateGroupRequest => {
            implicit val groupWrites = Json.format[Group]
            groupService.updateGroup(updateGroupRequest, groupId)
              .map(group => Ok(Json.toJson(group)))
          }
        )
      case None => Future.successful(BadRequest(Json.toJson("Group not found")))
    }
  }

  def getTweetsForGroup(groupId: UUID) = AuthenticatedAction.async { implicit request =>
    groupService.findById(groupId).flatMap {
      case Some(group) =>
        implicit val twitterFeedItemWrites = Json.writes[Tweet]

        twitterService.getGroupTweets(group.tags)
          .map(tweets => Ok(Json.toJson(tweets)))

      case None => Future.successful(NotFound("No tweets found"))
    }
  }

  def subscribe(groupId: UUID) = AuthenticatedAction.async { implicit request =>
    groupService.findById(groupId).flatMap {
      case Some(group) =>
        for {
          _ <- subscriptionService.subscribeUserToGroup(request.user.user, group)
        } yield {
          Ok(Json.obj())
        }

      case None => Future.successful(NotFound("Group not found"))
    }
  }

  def unsubscribe(groupId: UUID) = AuthenticatedAction.async { implicit request =>
    groupService.findById(groupId).flatMap {
      case Some(group) =>
        for {
          _ <- subscriptionService.unsubscribeUserFromGroup(request.user.user, group)
        } yield {
          Ok(Json.obj())
        }

      case None => Future.successful(NotFound("Group not found"))
    }
  }

}
