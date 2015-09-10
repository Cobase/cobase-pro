package cobase.play.post

import javax.inject.Inject

import cobase.group.GroupService
import cobase.post.PostService
import cobase.user.User
import com.mohiva.play.silhouette.api.{Environment, Silhouette}
import com.mohiva.play.silhouette.impl.authenticators.CookieAuthenticator
import play.api.i18n.MessagesApi
import play.api.libs.concurrent.Execution.Implicits.defaultContext

import scala.concurrent.Future

class SearchController @Inject() (
  implicit val env: Environment[User, CookieAuthenticator],
  val messagesApi: MessagesApi,
  groupService: GroupService,
  postService: PostService
) extends Silhouette[User, CookieAuthenticator] {

  /**
   * Handles the search from the front page.
   *
   * @return The posts found.
   */
  def searchPosts = SecuredAction.async { implicit request =>
    val futureGroupLinks = groupService.findGroupLinks
    val futureDashboardPosts = postService.getDashboardPosts(request.identity)

    val groupLinksAndDashboardPosts = for {
      groupLinks <- futureGroupLinks
      dashboardPosts <- futureDashboardPosts
    } yield (groupLinks, dashboardPosts)

    groupLinksAndDashboardPosts.flatMap {
      case (groupLinks, dashboardPosts) =>
        SearchForm.form.bindFromRequest.fold(
          formWithErrors => Future.successful(Ok(views.html.home(request.identity, groupLinks, dashboardPosts))),
          data => {
            for {
              posts <- postService.findByPhrase(data.phrase)
            } yield Ok(views.html.search(request.identity, groupLinks, posts))
          }
        )
    }
  }
}
