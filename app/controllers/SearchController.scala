package controllers

import javax.inject.Inject
import com.mohiva.play.silhouette.api.{Environment, Silhouette}
import com.mohiva.play.silhouette.impl.authenticators.SessionAuthenticator
import play.api.i18n.Messages
import scala.concurrent.Future

import forms.{SearchForm}
import models.{User, Search}
import models.services.{GroupService, PostService}
import models.exceptions._

/**
 * The search controller
 *
 * @param env
 * @param groupService
 * @param postService
 */
class SearchController @Inject() (implicit val env: Environment[User, SessionAuthenticator],
                                 groupService: GroupService,
                                 postService: PostService)
  extends Silhouette[User, SessionAuthenticator] {

  /**
   * Handles the search from the front page.
   *
   * @return The posts found.
   */
  def searchPosts = SecuredAction.async { implicit request =>
    val groupLinks = groupService.findGroupLinks
    val dashboardPosts = postService.getDashboardPosts(request.identity)

    SearchForm.form.bindFromRequest.fold(
      formWithErrors => {
        Future.successful(
          Ok(
            views.html.home(request.identity, groupLinks, dashboardPosts)
          )
        )
      },
      data => {
        val posts = postService.findByPhrase(data.phrase)
        Future.successful(
          Ok(
            views.html.search(request.identity, groupLinks, posts)
          )
        )
      }
    )
  }

}
