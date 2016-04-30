package cobase.twitter

import javax.inject.Inject
import play.api.{ Configuration, Environment }
import twitter4j.auth.AccessToken
import twitter4j.{Query, Twitter, TwitterFactory}

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class TwitterService @Inject() (
  protected val configuration: Configuration
) {
  def getGroupTweets(hashtags: String): Future[Seq[Tweet]] = {
    val queryMode = configuration.getString("twitter.queryMode").getOrElse("OR")
    val twitterQuery = hashtags.replace(",", " " + queryMode + " ")

    val query = new Query(twitterQuery)

    Future {
      val result = createTwitter.search(query)

      val format = new java.text.SimpleDateFormat("MMM dd, yyyy hh:mm")
      format.format(new java.util.Date())
      for (status <- result.getTweets.asScala.toSeq) yield Tweet(
        status.getUser.getName,
        status.getUser.getScreenName,
        status.getUser.getProfileImageURL,
        status.getText,
        format.format(status.getCreatedAt)
      )
    }
  }

  private def createTwitter: Twitter = {
    val consumerKey = configuration.getString("twitter.consumerKey").getOrElse("")
    val consumerSecret = configuration.getString("twitter.consumerSecret").getOrElse("")
    val accessKey = configuration.getString("twitter.accessKey").getOrElse("")
    val accessToken = configuration.getString("twitter.accessToken").getOrElse("")

    val twitter = new TwitterFactory().getInstance()

    twitter.setOAuthConsumer(consumerKey, consumerSecret)
    twitter.setOAuthAccessToken(new AccessToken(accessKey, accessToken))

    twitter
  }
}
