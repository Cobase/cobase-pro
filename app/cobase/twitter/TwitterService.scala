package cobase.twitter

import play.api.Play.current
import twitter4j.{Query, TwitterFactory}
import twitter4j.auth.AccessToken
import scala.collection.JavaConverters._
import scala.util.Try
import scala.util.Success
import scala.util.Failure
import java.util.Locale;
import com.ocpsoft.pretty.time.PrettyTime;

class TwitterService {
  def getGroupTweets(hashtags: String): Option[List[Tweet]] = {
    val consumerKey = current.configuration.getString("twitter.consumerKey").getOrElse("")
    val consumerSecret = current.configuration.getString("twitter.consumerSecret").getOrElse("")
    val accessKey = current.configuration.getString("twitter.accessKey").getOrElse("")
    val accessToken = current.configuration.getString("twitter.accessToken").getOrElse("")
    val queryMode = current.configuration.getString("twitter.queryMode").getOrElse("OR")
    val twitterQuery = hashtags.replace(",", " " + queryMode + " ");

    val twitter = new TwitterFactory().getInstance()
    twitter.setOAuthConsumer(consumerKey, consumerSecret)
    twitter.setOAuthAccessToken(
      new AccessToken(accessKey, accessToken)
    )

    val query = new Query(twitterQuery)
    val result = Try(twitter.search(query))
    val prettyfier = new PrettyTime(new Locale("DEFAULT"))

    result match {
      case Success(res) => {
        val tweets = for (status <- res.getTweets.asScala.toList) yield
          Tweet(
            status.getUser.getName,
            status.getUser.getScreenName,
            status.getUser.getProfileImageURL,
            status.getText,
            prettyfier.format(status.getCreatedAt)
          )
        Some(tweets)
      }
      case Failure(ex) => None
    }
  }
}
