package cobase.play.group

import java.util.UUID
import javax.inject.Inject

import cobase.group._
import cobase.post.PostService
import cobase.twitter.{Tweet, TwitterService}
import cobase.user._
import com.mohiva.play.silhouette.api.{Environment, Silhouette}
import com.mohiva.play.silhouette.impl.authenticators.CookieAuthenticator
import play.api.i18n.{Messages, MessagesApi}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json._
import play.api.mvc.Action

import scala.concurrent.Future

class GroupController @Inject() (
  implicit val env: Environment[User, CookieAuthenticator],
  val messagesApi: MessagesApi,
  groupService: GroupService,
  postService: PostService,
  subscriptionService: SubscriptionService,
  twitterService: TwitterService
) extends Silhouette[User, CookieAuthenticator] {

  def addGroup = Action.async(parse.json) { implicit request =>
    request.body.validate[AddGroupRequest].fold(
      errors => Future.successful(BadRequest(Json.obj("errors" -> JsError.toJson(errors)))),
      addGroupRequest => {
        implicit val groupWrites = Json.format[Group]
        groupService.addGroup(addGroupRequest)
          .map(group => Ok(Json.toJson(group)))
      }
    )
  }

  def getGroups = Action.async { implicit request =>
    implicit val groupLinkWrites = Json.format[GroupLink]
    for {
      groupLinks <- groupService.findGroupLinks
    } yield Ok(Json.toJson(groupLinks))
  }

  def updateGroup(groupId: UUID) = Action.async(parse.json) { implicit request =>
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

  def getTweetsForGroup(groupId: UUID) = Action.async { implicit request =>
    groupService.findById(groupId).flatMap {
      case Some(group) =>
        implicit val twitterFeedItemWrites = Json.writes[Tweet]

        twitterService.getGroupTweets(group.tags)
          .map(tweets => Ok(Json.toJson(tweets)))

      case None => Future.successful(NotFound("No tweets found"))
    }
  }

  def subscribe(groupId: UUID) = Action.async { implicit request =>
    groupService.findById(groupId).flatMap {
      case Some(group) =>
        for {
          _ <- subscriptionService.subscribeUserToGroup(request.user, group)
        } yield {
          Ok(Json.obj())
        }

      case None => Future.successful(NotFound("Group not found"))
    }
  }

  def unsubscribe(groupId: UUID) = Action.async { implicit request =>
    groupService.findById(groupId).flatMap {
      case Some(group) =>
        for {
          _ <- subscriptionService.unsubscribeUserFromGroup(request.user, group)
        } yield {
          Ok(Json.obj())
        }

      case None => Future.successful(NotFound("Group not found"))
    }
  }

}
