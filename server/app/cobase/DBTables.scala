package cobase

import java.util.UUID

import com.github.tototoshi.slick.PostgresJodaSupport._
import cobase.authentication.AuthenticationToken
import cobase.group.Group
import cobase.post.Post
import cobase.user.{User, Subscription}
import org.joda.time.DateTime
import slick.driver.JdbcProfile

trait DBTables {
  protected val driver: JdbcProfile

  import driver.api._

  class Users(tag: Tag) extends Table[User](tag, "users") {
    def id = column[UUID]("id", O.PrimaryKey, O.SqlType("UUID"))
    def email = column[String]("email")
    def password = column[String]("password")
    def role = column[String]("role")

    def created = column[DateTime]("created")

    def verificationToken = column[String]("verification_token")
    def verified = column[Option[DateTime]]("verified")

    def firstName = column[Option[String]]("first_name")
    def lastName = column[Option[String]]("last_name")
    def avatarURL = column[Option[String]]("avatar_url")

    def passwordResetToken = column[Option[String]]("password_reset_token")
    def passwordResetRequested = column[Option[DateTime]]("password_reset_requested")
    def passwordResetUsed = column[Option[DateTime]]("password_reset_used")

    def uniqueEmail = index("users_email_uniq", email, unique = true)
    def uniqueVerificationToken = index("users_verification_token_uniq", verificationToken, unique = true)
    def uniquePasswordResetToken = index("users_password_reset_token_uniq", passwordResetToken, unique = true)

    def * = (id, email, password, role, created, verificationToken, verified, firstName, lastName, avatarURL, passwordResetToken, passwordResetRequested, passwordResetUsed) <> (User.tupled, User.unapply)
  }

  class AuthenticationTokens(tag: Tag) extends Table[AuthenticationToken](tag, "authentication_tokens") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def userId = column[UUID]("user_id", O.SqlType("UUID"))
    def token = column[String]("token")
    def created = column[DateTime]("created")

    def uniqueToken = index("authentication_tokens_token_uniq", token, unique = true)

    def * = (id, userId, token, created) <> (AuthenticationToken.tupled, AuthenticationToken.unapply)

    def user = foreignKey("user_fk", userId, users)(_.id)
  }

  class Groups(tag: Tag) extends Table[Group](tag, "groups") {
    def id = column[UUID]("id", O.PrimaryKey, O.SqlType("UUID"))
    def title = column[String]("title")
    def tags = column[String]("tags")
    def isActive = column[Boolean]("is_active")

    def * = (id, title, tags, isActive) <> (Group.tupled, Group.unapply)
  }

  class Posts(tag: Tag) extends Table[Post](tag, "posts") {
    def id = column[UUID]("id", O.PrimaryKey, O.SqlType("UUID"))
    def content = column[String]("content", O.SqlType("text"))
    def groupId = column[UUID]("group_id", O.SqlType("UUID"))
    def createdBy = column[Option[String]]("created_by")
    def created = column[DateTime]("created")
    def isActive = column[Boolean]("is_active")

    def * = (id, content, groupId, createdBy, created, isActive) <> (Post.tupled, Post.unapply)

    def group = foreignKey("group_fk", groupId, groups)(_.id)
  }

  class Subscriptions(tag: Tag) extends Table[Subscription](tag, "subscriptions") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def userId = column[UUID]("user_id", O.SqlType("UUID"))
    def groupId = column[UUID]("group_id", O.SqlType("UUID"))

    def * = (id, userId, groupId) <> (Subscription.tupled, Subscription.unapply)

    def user = foreignKey("user_fk", userId, users)(_.id)
    def group = foreignKey("group_fk", groupId, groups)(_.id)
    def uniqueGroupAndUser = index("idx_subscr_comb", (groupId, userId), unique = true)
  }

  val users = TableQuery[Users]
  val authenticationTokens = TableQuery[AuthenticationTokens]
  val groups = TableQuery[Groups]
  val posts = TableQuery[Posts]
  val subscriptions = TableQuery[Subscriptions]
}
