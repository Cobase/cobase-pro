package cobase.test

import cobase.util.Linkify
import org.scalatest.FlatSpec

/**
 * Test case for the [[Linkify]] class.
 */
class LinkifySpec extends FlatSpec {

  "A URL cotaining http://" should "be converted into a link." in {

    val s = "This is a http://www.google.com link."
    val linkify = new Linkify()
    val converted = linkify.convert(s)

    assert(converted === "This is a <a href=\"http://www.google.com\">http://www.google.com</a> link.")
  }

  "A URL not cotaining http://" should "not be converted into a link." in {

    val s = "This is a www.google.com link."
    val linkify = new Linkify()
    val converted = linkify.convert(s)

    assert(converted === "This is a www.google.com link.")
  }

  "A hashtag in a string" should "be converted into a search link." in {

    val s = "This is a #cobase hashtag."
    val linkify = new Linkify()
    val converted = linkify.convert(s)

    assert(converted === "This is a <a href=\"/posts/search?phrase=%23cobase\">#cobase</a> hashtag.")
  }

}
