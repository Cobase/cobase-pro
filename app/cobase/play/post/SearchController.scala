package cobase.play.post

import javax.inject.{Inject, Singleton}

import cobase.authentication.AuthenticationService
import cobase.group.GroupService
import cobase.play.user.SecuredController
import cobase.post.PostService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class SearchController @Inject() (
  val authenticationService: AuthenticationService,
  groupService: GroupService,
  postService: PostService
) extends SecuredController {

  /**
   * Handles the search from the front page.
   *
   * @return The posts found.
   */
  def searchPosts = AuthenticatedAction.async { implicit request =>
    val futureGroupLinks = groupService.findGroupLinks

    val groupLinksAndDashboardPosts = for {
      groupLinks <- futureGroupLinks
    } yield groupLinks

    groupLinksAndDashboardPosts.flatMap {
      case (groupLinks) =>
        SearchForm.form.bindFromRequest.fold(
          formWithErrors => Future.successful(Ok(views.html.home(request.user.user, groupLinks))),
          data => {
            for {
              posts <- postService.findByPhrase(data.phrase)
            } yield Ok(views.html.search(request.user.user, groupLinks, posts))
          }
        )
    }
  }
}
