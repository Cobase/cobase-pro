package cobase.play.user

import javax.inject.Inject

import cobase.group.GroupService
import cobase.post.PostService
import cobase.user.User
import com.mohiva.play.silhouette.api.{Environment, Silhouette}
import com.mohiva.play.silhouette.impl.authenticators.CookieAuthenticator
import play.api.i18n.MessagesApi
import play.api.libs.concurrent.Execution.Implicits.defaultContext

import scala.concurrent.Future

/**
 * The basic application controller.
 *
 * @param env The Silhouette environment.
 */
class ApplicationController @Inject() (
  val messagesApi: MessagesApi,
  implicit val env: Environment[User, CookieAuthenticator],
  groupService: GroupService,
  postService: PostService
) extends Silhouette[User, CookieAuthenticator] {

  /**
   * Handles the index action.
   *
   * @return The result to display.
   */
  def index = SecuredAction.async { implicit request =>
    val futureGroupLinks = groupService.findGroupLinks
    val futureDashboardPosts = postService.getDashboardPosts(request.identity)

    val groupLinksAndDashboardPosts = for {
      groupLinks <- futureGroupLinks
      dashboardPosts <- futureDashboardPosts
    } yield (groupLinks, dashboardPosts)

    groupLinksAndDashboardPosts.flatMap {
      case (groupLinks, dashboardPosts) =>
        Future.successful(Ok(views.html.home(request.identity, groupLinks, dashboardPosts)))
    }
  }
}
