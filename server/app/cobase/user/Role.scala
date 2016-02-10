package cobase.user

import scala.util.{Failure, Success, Try}

sealed trait Role {
  def name: String
}

object Role {
  def fromName(name: String): Try[Role] = {
    name match {
      case UserRole.name => Success(UserRole)
      case AdminRole.name => Success(AdminRole)
      case _ => Failure(new Exception("invalid role"))
    }
  }
}

case object UserRole extends Role {
  val name = "user"
}
case object AdminRole extends Role {
  val name = "admin"
}
