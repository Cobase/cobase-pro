package cobase.user

import java.util.UUID
import javax.inject.Inject

import cobase.authentication.{TokenService, PasswordService}
import org.joda.time.DateTime

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class UserService @Inject() (
  userRepository: UserRepository,
  passwordService: PasswordService,
  tokenService: TokenService
) {
  def registerUser(data: RegistrationRequest): Future[User] = {
    val password = passwordService.hashPassword(data.password)
    val verificationToken = tokenService.generateToken

    userRepository
      .addUser(RegisterUserData(
        UUID.randomUUID(),
        data.username,
        password,
        data.role,
        DateTime.now(),
        verificationToken
      ))
      .map { user =>
        // TODO: Send verification email to users.
        verifyRegistration(user)

        user
      }
  }

  def verifyRegistration(user: User): Future[Boolean] = {
    userRepository.updateVerified(user, DateTime.now())
  }
}
