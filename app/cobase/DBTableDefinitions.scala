package cobase

import java.util.UUID

import cobase.group.Group
import cobase.post.Post
import cobase.user.Subscription
import com.mohiva.play.silhouette.api.LoginInfo
import slick.driver.JdbcProfile

trait DBTableDefinitions {
  protected val driver: JdbcProfile

  import driver.api._

  case class DBUser (
    id: String,
    firstName: Option[String],
    lastName: Option[String],
    fullName: Option[String],
    email: Option[String],
    avatarURL: Option[String]
  )

  class Users(tag: Tag) extends Table[DBUser](tag, "users") {
    def id = column[String]("id", O.PrimaryKey)
    def firstName = column[Option[String]]("first_name")
    def lastName = column[Option[String]]("last_name")
    def fullName = column[Option[String]]("full_name")
    def email = column[Option[String]]("email")
    def avatarURL = column[Option[String]]("avatar_url")
    def * = (id, firstName, lastName, fullName, email, avatarURL) <> (DBUser.tupled, DBUser.unapply)
  }

  case class DBLoginInfo (
    id: Option[Long],
    providerID: String,
    providerKey: String
  )

  class LoginInfos(tag: Tag) extends Table[DBLoginInfo](tag, "login_infos") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def providerID = column[String]("provider_id")
    def providerKey = column[String]("provider_key")
    def * = (id.?, providerID, providerKey) <> (DBLoginInfo.tupled, DBLoginInfo.unapply)
  }

  case class DBUserLoginInfo (
    userId: String,
    loginInfoId: Long
  )

  class UserLoginInfos(tag: Tag) extends Table[DBUserLoginInfo](tag, "user_login_infos") {
    def userId = column[String]("user_id")
    def loginInfoId = column[Long]("login_info_id")
    def * = (userId, loginInfoId) <> (DBUserLoginInfo.tupled, DBUserLoginInfo.unapply)
  }

  case class DBPasswordInfo (
    hasher: String,
    password: String,
    salt: Option[String],
    loginInfoId: Long
  )

  class PasswordInfos(tag: Tag) extends Table[DBPasswordInfo](tag, "password_infos") {
    def hasher = column[String]("hasher")
    def password = column[String]("password")
    def salt = column[Option[String]]("salt")
    def loginInfoId = column[Long]("login_info_id")
    def * = (hasher, password, salt, loginInfoId) <> (DBPasswordInfo.tupled, DBPasswordInfo.unapply)
  }

  case class DBOAuth1Info (
    id: Option[Long],
    token: String,
    secret: String,
    loginInfoId: Long
  )

  class OAuth1Infos(tag: Tag) extends Table[DBOAuth1Info](tag, "oauth1_infos") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def token = column[String]("token")
    def secret = column[String]("secret")
    def loginInfoId = column[Long]("login_info_id")
    def * = (id.?, token, secret, loginInfoId) <> (DBOAuth1Info.tupled, DBOAuth1Info.unapply)
  }

  case class DBOAuth2Info (
    id: Option[Long],
    accessToken: String,
    tokenType: Option[String],
    expiresIn: Option[Int],
    refreshToken: Option[String],
    loginInfoId: Long
  )

  class OAuth2Infos(tag: Tag) extends Table[DBOAuth2Info](tag, "oauth2_infos") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def accessToken = column[String]("access_token")
    def tokenType = column[Option[String]]("token_type")
    def expiresIn = column[Option[Int]]("expires_in")
    def refreshToken = column[Option[String]]("refresh_token")
    def loginInfoId = column[Long]("login_info_id")
    def * = (id.?, accessToken, tokenType, expiresIn, refreshToken, loginInfoId) <> (DBOAuth2Info.tupled, DBOAuth2Info.unapply)
  }

  case class DBOpenIDInfo (
    id: String,
    loginInfoId: Long
  )

  class OpenIDInfos(tag: Tag) extends Table[DBOpenIDInfo](tag, "openid_infos") {
    def id = column[String]("id")
    def loginInfoId = column[Long]("login_info_id")
    def * = (id, loginInfoId) <> (DBOpenIDInfo.tupled, DBOpenIDInfo.unapply)
  }

  case class DBOpenIDAttribute (
    id: String,
    key: String,
    value: String
  )

  class OpenIDAttributes(tag: Tag) extends Table[DBOpenIDAttribute](tag, "openid_attributes") {
    def id = column[String]("id")
    def key = column[String]("key")
    def value = column[String]("value")
    def * = (id, key, value) <> (DBOpenIDAttribute.tupled, DBOpenIDAttribute.unapply)
  }

  class Groups(tag: Tag) extends Table[Group](tag, "groups") {
    def id = column[UUID]("id", O.PrimaryKey, O.SqlType("UUID"))
    def title = column[String]("title")
    def tags = column[String]("tags")
    def idx = index("idx_group_id", id, unique = true)
    def * = (id, title, tags) <> (Group.tupled, Group.unapply)
  }

  class Posts(tag: Tag) extends Table[Post](tag, "posts") {
    def id = column[UUID]("id", O.PrimaryKey, O.SqlType("UUID"))
    def content = column[String]("content", O.SqlType("text"))
    def groupId = column[UUID]("group_id", O.SqlType("UUID"))
    def createdBy = column[Option[String]]("created_by")
    def createdTimestamp = column[Long]("created_timestamp")
    def isActive = column[Boolean]("is_active")
    def idx = index("idx_post_group", groupId, unique = false)
    def * = (id, content, groupId, createdBy, createdTimestamp, isActive) <> (Post.tupled, Post.unapply)
  }

  class Subscriptions(tag: Tag) extends Table[Subscription](tag, "subscriptions") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def userId = column[UUID]("user_id", O.SqlType("UUID"))
    def groupId = column[UUID]("group_id", O.SqlType("UUID"))
    def idx1 = index("idx_subscr_group", groupId, unique = false)
    def idx2 = index("idx_subscr_user", userId, unique = false)
    def idx3 = index("idx_subscr_comb", (groupId, userId), unique = true)
    def * = (id, userId, groupId) <> (Subscription.tupled, Subscription.unapply)
  }

  val slickUsers = TableQuery[Users]
  val slickLoginInfos = TableQuery[LoginInfos]
  val slickUserLoginInfos = TableQuery[UserLoginInfos]
  val slickPasswordInfos = TableQuery[PasswordInfos]
  val slickOAuth1Infos = TableQuery[OAuth1Infos]
  val slickOAuth2Infos = TableQuery[OAuth2Infos]
  val slickOpenIDInfos = TableQuery[OpenIDInfos]
  val slickOpenIDAttributes = TableQuery[OpenIDAttributes]
  val slickGroups = TableQuery[Groups]
  val slickPosts = TableQuery[Posts]
  val slickSubscriptions = TableQuery[Subscriptions]

  // queries used in multiple places
  def loginInfoQuery(loginInfo: LoginInfo) = {
    slickLoginInfos.filter(dbLoginInfo =>
      dbLoginInfo.providerID === loginInfo.providerID && dbLoginInfo.providerKey === loginInfo.providerKey
    )
  }
}
