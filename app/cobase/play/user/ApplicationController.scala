package cobase.play.user

import javax.inject.Inject

import cobase.post.PostService
import cobase.user.{GroupService, User}
import com.mohiva.play.silhouette.api.{Environment, Silhouette}
import com.mohiva.play.silhouette.impl.authenticators.SessionAuthenticator

import scala.concurrent.Future

/**
 * The basic application controller.
 *
 * @param env The Silhouette environment.
 */
class ApplicationController @Inject() (implicit val env: Environment[User, SessionAuthenticator],
                                       groupService: GroupService,
                                       postService: PostService)
  extends Silhouette[User, SessionAuthenticator] {

  /**
   * Handles the index action.
   *
   * @return The result to display.
   */
  def index = SecuredAction.async { implicit request =>
    val groupLinks = groupService.findGroupLinks
    val dashboardPosts = postService.getDashboardPosts(request.identity)

    Future.successful(Ok(views.html.home(request.identity, groupLinks, dashboardPosts)))
  }

}
