package models.daos

import com.mohiva.play.silhouette.api.LoginInfo
import com.mohiva.play.silhouette.impl.daos.DelegableAuthInfoDAO
import com.mohiva.play.silhouette.impl.providers.OpenIDInfo
import scala.concurrent.Future
import models.daos.slick.DBTableDefinitions._
import play.api.db.slick._
import play.api.db.slick.Config.driver.simple._

/**
 * The DAO to store the OpenID information.
 */
class OpenIDInfoDAOSlick extends DelegableAuthInfoDAO[OpenIDInfo] {

  import play.api.Play.current
  
  /**
   * Saves the OpenID info.
   *
   * @param loginInfo The login info for which the auth info should be saved.
   * @param authInfo The OpenID info to save.
   * @return The saved OpenID info or None if the OpenID info couldn't be saved.
   */
  def save(loginInfo: LoginInfo, authInfo: OpenIDInfo): Future[OpenIDInfo] = {
    Future.successful(
      DB withSession { implicit session =>
        val infoId = slickLoginInfos.filter(
          x => x.providerID === loginInfo.providerID && x.providerKey === loginInfo.providerKey
        ).first.id.get
        val infoQuery = slickOpenIDInfos.filter(_.loginInfoId === infoId)
        infoQuery.firstOption match {
          case Some(info) => {
            infoQuery.delete
            slickOpenIDAttributes.filter(_.id === info.id).delete
          }
          case None =>
        }
        slickOpenIDInfos insert DBOpenIDInfo(authInfo.id, infoId)
        slickOpenIDAttributes ++= authInfo.attributes.map {
          case (key, value) => DBOpenIDAttribute(authInfo.id, key, value)
        }
        authInfo
      }
    )
  }

  /**
   * Finds the OpenID info which is linked with the specified login info.
   *
   * @param loginInfo The linked login info.
   * @return The retrieved OpenID info or None if no OpenID info could be retrieved for the given login info.
   */
  def find(loginInfo: LoginInfo): Future[Option[OpenIDInfo]] = {
    Future.successful(
      DB withSession { implicit session =>
        slickLoginInfos.filter(info => info.providerID === loginInfo.providerID && info.providerKey === loginInfo.providerKey).firstOption match {
          case Some(info) =>
            val openIDInfo = slickOpenIDInfos.filter(_.loginInfoId === info.id).first
            val openIDAttributes = slickOpenIDAttributes.filter(_.id === openIDInfo.id).
              list.map(attr => (attr.key, attr.value)).toMap
            Some(OpenIDInfo(openIDInfo.id, openIDAttributes))
          case None => None
        }
      }
    )
  }
}
