package models.services

import twitter4j.TwitterFactory
import twitter4j.auth.AccessToken
import twitter4j.Query
import scala.collection.JavaConverters._
import play.api.Play.current

class TwitterService {

  def getGroupTweets(hashtags: String): Option[List[twitter4j.Status]] = {
    val consumerKey = current.configuration.getString("twitter.consumerKey").getOrElse("")
    val consumerSecret = current.configuration.getString("twitter.consumerSecret").getOrElse("")
    val accessKey = current.configuration.getString("twitter.accessKey").getOrElse("")
    val accessToken = current.configuration.getString("twitter.accessToken").getOrElse("")
    
    val twitter = new TwitterFactory().getInstance()
    twitter.setOAuthConsumer(consumerKey, consumerSecret)
    twitter.setOAuthAccessToken(
      new AccessToken(accessKey, accessToken)
    )

    try {
      val query = new Query(hashtags);
      val result = twitter.search(query)
      val tweets = result.getTweets().asScala.toList

      if (tweets.size > 0) Some(tweets) else None
    } catch {
      case e:Exception => None
    }
    
  }

}
