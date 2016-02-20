package cobase.play.user

import java.io.File
import javax.inject.Singleton

import com.typesafe.config.ConfigFactory
import play.api.http.HeaderNames
import play.api.mvc.{Action, Controller}

@Singleton
class ApplicationController extends Controller {
  def index = html(ConfigFactory.load.getString("cobase.clientIndex"))

  def html(file: String) = Action {
    val f = new File(file)

    if (f.exists()) Ok(scala.io.Source.fromFile(f.getCanonicalPath).mkString).as("text/html")
    else NotFound
  }

  def options(path: String) = Action { request =>
    Ok.withHeaders(
      HeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN  -> "*",
      HeaderNames.ALLOW                        -> "*",
      HeaderNames.ACCESS_CONTROL_ALLOW_METHODS -> "POST, GET, PUT, DELETE, OPTIONS",
      HeaderNames.ACCESS_CONTROL_ALLOW_HEADERS -> "Origin, X-Requested-With, Content-Type, Accept, Referer, User-Agent"
    )
  }

}

