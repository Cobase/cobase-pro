package cobase.play.user

import java.io.File
import javax.inject.Singleton

import com.typesafe.config.ConfigFactory
import play.api.mvc.{Action, Controller}

@Singleton
class ApplicationController extends Controller {
  def index = html(ConfigFactory.load.getString("cobase.clientIndex"))

  def html(file: String) = Action {
    val f = new File(file)

    if (f.exists()) Ok(scala.io.Source.fromFile(f.getCanonicalPath).mkString).as("text/html")
    else NotFound
  }
}
