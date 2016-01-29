package cobase.authentication

import org.mindrot.jbcrypt.BCrypt

class PasswordService {
  def hashPassword(password: String): String = {
    BCrypt.hashpw(password, BCrypt.gensalt())
  }

  def checkPassword(plainPassword: String, hashed: String): Boolean = {
    try {
      BCrypt.checkpw(plainPassword, hashed)
    } catch {
      case _ : Throwable => false
    }
  }
}
