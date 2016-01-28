package cobase.play.user

import javax.inject.{Inject, Singleton}

import cobase.authentication.AuthenticationService
import cobase.group.GroupService
import cobase.post.PostService

import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class ApplicationController @Inject() (
  val authenticationService: AuthenticationService,
  groupService: GroupService,
  postService: PostService
) extends SecuredController {

  def index = AuthenticatedAction.async { implicit request =>
    val futureGroupLinks = groupService.findGroupLinks

    for {
      groupLinks <- groupService.findGroupLinks
    } yield Ok(views.html.home(request.user.user, groupLinks))
  }
}
