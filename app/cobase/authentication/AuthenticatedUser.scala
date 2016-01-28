package cobase.authentication

import cobase.user.User

case class AuthenticatedUser(user: User, token: String)
