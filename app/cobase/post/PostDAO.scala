package cobase.post

import cobase.DBTableDefinitions
import cobase.user.User
import DBTableDefinitions._
import play.api.Play.current
import play.api.db.slick.Config.driver.simple._
import play.api.db.slick._

import scala.concurrent.Future
import scala.slick.jdbc.{GetResult, StaticQuery => Q}

import java.util.UUID

/**
 * Give access to the user object using Slick
 */
class PostDAO {

  def findAll: List[Post] = {
    DB withSession { implicit session =>
      slickPosts.list
    }
  }

  def findById(postId: UUID): Option[Post] = {
    DB withSession { implicit session =>
      slickPosts.filter(_.id === postId).firstOption
    }
  }

  /**
   * Finds posts by search phrase.
   */
  def findByPhrase(phrase: String): List[Post] = {
    DB withSession { implicit session =>
      slickPosts.filter(
        _.content.toLowerCase.like("%" + phrase.toLowerCase + "%")
      ).sortBy(_.id.desc).list
    }
  }

  def findLatestPostsForGroup(groupId: UUID): List[Post] = {
    DB withSession { implicit session =>
      slickPosts.filter(
        _.groupId === groupId
      ).sortBy(_.createdTimestamp.desc).list
    }
  }

  def add(post: Post): Future[Post] = {
    DB withSession { implicit session =>
      Future.successful {
        slickPosts.insert(post)
        post
      }
    }
  }

  def update(post: Post): Future[Post] = {
    DB withSession { implicit session =>
      Future.successful {
        slickPosts.filter(_.id === post.id).update(post)
        post
      }
    }
  }

  /**
   * Get posts related to user's subscriptions.
   */
  def getDashboardPosts(user: User): List[DashboardPost] = {
    DB withSession { implicit session =>
      implicit val getPostResult =
        GetResult(r =>
          DashboardPost(r.nextString(), r.nextString(), r.nextLong(), r.nextString(), UUID.fromString(r.nextString()))
        )

      val userId = user.id

      val dashboardPosts = Q[DashboardPost] +
        s"""
            SELECT
              p.content, p.created_by, p.created_timestamp, g.title, g.id
            FROM
              posts p, groups g, subscriptions s
            WHERE
              g.id = p.group_id AND
              s.group_id = p.group_id AND
              s.user_id = '$userId'
            ORDER BY p.created_timestamp DESC
        """

      dashboardPosts.list
    }
  }

}
