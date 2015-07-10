package cobase.util

import xml.Utility.escape
import java.net.URLEncoder

class Linkify {

  /**
   * Convert all URLs into links and all hashtags into search url.
   */
  def convert(s: String): String = {
    val urlRegex = """(?i)\b(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]*[-A-Za-z0-9+&@#/%=~_|]""".r
    val hashtagRegex = """#(\w*[a-zA-Z_0-9]+\w*)""".r

    val urlConverted = urlRegex replaceAllIn
      (escape(s), m => """<a href="%s">%s</a>"""
        format (m.matched, m.matched))

    val hashtagAndUrlConverted = hashtagRegex replaceAllIn
      (urlConverted, m => """<a href="/posts/search?phrase=%s">%s</a>"""
        format (URLEncoder.encode(m.matched, "UTF-8"), m.matched))

    hashtagAndUrlConverted
  }

}
