package cobase.twitter

case class Tweet (
  realName: String,
  screenName: String,
  profileImageURL: String,
  text: String,
  createdAt: java.util.Date
)