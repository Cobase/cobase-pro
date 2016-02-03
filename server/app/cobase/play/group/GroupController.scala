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
  val groupService: GroupService,
  postService: PostService,
  subscriptionService: SubscriptionService,
  twitterService: TwitterService
) extends SecuredController with GroupProvider {

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

  def AuthenticatedGroupAction(groupId: UUID) = AuthenticatedAction andThen GroupAction(groupId)

  def updateGroup(groupId: UUID) = AuthenticatedGroupAction(groupId).async(parse.json) { implicit request =>
    request.body.validate[UpdateGroupRequest].fold(
      errors => Future.successful(BadRequest(Json.obj("errors" -> JsError.toJson(errors)))),
      updateGroupRequest => {
        implicit val groupWrites = Json.format[Group]

        groupService.updateGroup(updateGroupRequest, groupId)
          .map(group => Ok(Json.toJson(group)))
      }
    )
  }

  def getTweetsForGroup(groupId: UUID) = AuthenticatedGroupAction(groupId).async { implicit request =>
    implicit val twitterFeedItemWrites = Json.writes[Tweet]

    twitterService.getGroupTweets(request.group.tags).map(tweets => Ok(Json.toJson(tweets)))
  }

  def subscribe(groupId: UUID) = AuthenticatedGroupAction(groupId).async { implicit request =>
    for {
      _ <- subscriptionService.subscribeUserToGroup(request.user.user, request.group)
    } yield {
      Ok(Json.obj())
    }
  }

  def unsubscribe(groupId: UUID) = AuthenticatedGroupAction(groupId).async { implicit request =>
    for {
      _ <- subscriptionService.unsubscribeUserFromGroup(request.user.user, request.group)
    } yield {
      Ok(Json.obj())
    }
  }
}
