package cobase.test


import java.util.UUID

import cobase.play.user.ApplicationController
import cobase.user.User
import com.google.inject.AbstractModule
import com.mohiva.play.silhouette.api.{Environment, LoginInfo}
import com.mohiva.play.silhouette.impl.authenticators.CookieAuthenticator
import com.mohiva.play.silhouette.test._
import net.codingwell.scalaguice.ScalaModule
import org.specs2.mock.Mockito
import org.specs2.specification.Scope
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.libs.concurrent.Execution.Implicits._
import play.api.test.{FakeRequest, PlaySpecification, WithApplication}

/**
 * Test case for the [[ApplicationController]] class.
 */
class ApplicationControllerSpec extends PlaySpecification with Mockito {
  sequential

  "The `index` action" should {
    "redirect to login page if user is unauthorized" in new Context {
      new WithApplication(application) {
        val Some(redirectResult) = route(
          FakeRequest(cobase.play.user.routes.ApplicationController.index())
            .withAuthenticator[CookieAuthenticator](LoginInfo("invalid", "invalid"))
        )

        status(redirectResult) must be equalTo SEE_OTHER

        val redirectURL = redirectLocation(redirectResult).getOrElse("")
        redirectURL must contain(cobase.play.user.routes.AuthenticationController.signIn().toString())

        val Some(unauthorizedResult) = route(FakeRequest(GET, redirectURL))

        status(unauthorizedResult) must be equalTo OK
        contentType(unauthorizedResult) must beSome("text/html")
        contentAsString(unauthorizedResult) must contain("CobasePRO")
        contentAsString(unauthorizedResult) must contain("Login into system")
      }
    }

    "return 200 if user is authorized" in new Context {
      new WithApplication(application) {
        val Some(result) = route(
          FakeRequest(cobase.play.user.routes.ApplicationController.index())
            .withAuthenticator[CookieAuthenticator](identity.loginInfo)
        )

        status(result) must beEqualTo(OK)
      }
    }
  }

  /**
   * The context.
   */
  trait Context extends Scope {
    /**
     * A fake Guice module.
     */
    class FakeModule extends AbstractModule with ScalaModule {
      def configure() = {
        bind[Environment[User, CookieAuthenticator]].toInstance(env)
      }
    }

    /**
     * An identity.
     */
    val identity = User(
      id = UUID.randomUUID(),
      loginInfo = LoginInfo("facebook", "user@facebook.com"),
      firstName = None,
      lastName = None,
      fullName = None,
      email = None,
      avatarURL = None
    )

    /**
     * A Silhouette fake environment.
     */
    implicit val env: Environment[User, CookieAuthenticator] = new FakeEnvironment[User, CookieAuthenticator](Seq(identity.loginInfo -> identity))

    /**
     * The application.
     */
    lazy val application = new GuiceApplicationBuilder()
      .overrides(new FakeModule)
      .build()
  }
}
