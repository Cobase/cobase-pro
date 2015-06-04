package cobase.test

import java.util.UUID

import cobase.play.{Global, SilhouetteModule}
import cobase.play.user.ApplicationController
import cobase.user.User
import com.google.inject.util.Modules
import com.google.inject.{AbstractModule, Guice}
import com.mohiva.play.silhouette.api.{Environment, LoginInfo}
import com.mohiva.play.silhouette.impl.authenticators.SessionAuthenticator
import com.mohiva.play.silhouette.test._
import net.codingwell.scalaguice.ScalaModule
import org.specs2.mock.Mockito
import play.api.test.{FakeApplication, FakeRequest, PlaySpecification, WithApplication}

/**
 * Test case for the [[ApplicationController]] class.
 */
class ApplicationControllerSpec extends PlaySpecification with Mockito {
  isolated

  "The `index` action" should {
    "redirect to login page if user is unauthorized" in new WithApplication(FakeApplication(withGlobal = Some(new FakeGlobal))) {
      val Some(redirectResult) = route(FakeRequest(cobase.play.user.routes.ApplicationController.index())
        .withAuthenticator[SessionAuthenticator](LoginInfo("invalid", "invalid"))
      )

      status(redirectResult) must be equalTo SEE_OTHER

      val redirectURL = redirectLocation(redirectResult).getOrElse("")
      redirectURL must contain(cobase.play.user.routes.ApplicationController.signIn().toString())

      val Some(unauthorizedResult) = route(FakeRequest(GET, redirectURL))

      status(unauthorizedResult) must be equalTo OK
      contentType(unauthorizedResult) must beSome("text/html")
      contentAsString(unauthorizedResult) must contain("CobasePRO")
      contentAsString(unauthorizedResult) must contain("Login into system")
    }

    "return 200 if user is authorized" in new WithApplication(FakeApplication(withGlobal = Some(new FakeGlobal))) {
      val Some(result) = route(FakeRequest(cobase.play.user.routes.ApplicationController.index())
        .withAuthenticator[SessionAuthenticator](identity.loginInfo)
      )

      status(result) must beEqualTo(OK)
    }
  }

  /**
   * Provides a fake global to override the Guice injector.
   */
  class FakeGlobal extends Global {

    /**
     * Overrides the Guice injector.
     */
    override val injector = Guice.createInjector(Modules.`override`(new SilhouetteModule).`with`(new FakeModule))

    /**
     * A fake Guice module.
     */
    class FakeModule extends AbstractModule with ScalaModule {
      def configure() = {
        bind[Environment[User, SessionAuthenticator]].toInstance(env)
      }
    }
  }

  /**
   * An identity.
   */
  val identity = User(
    userID = UUID.randomUUID(),
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
  implicit val env = FakeEnvironment[User, SessionAuthenticator](Seq(identity.loginInfo -> identity))
}
