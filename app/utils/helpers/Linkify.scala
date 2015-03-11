package utils.helpers

class Linkify {

  /**
   * Convert all URLs found in a string into A links. 
   * @param s
   * @return String
   */
  def convert(s: String): String = {
    val escaped = xml.Utility.escape(s)
    val urlRegex = """(href=")?([-a-zA-Z0-9@:%_\+.~#?&\/\/=]{2,256}\.[a-z]{2,4}\b(\/?[-\p{L}0-9@:%_\+.~#?&\/\/=\(\)]*)?)""".r
    urlRegex replaceAllIn (escaped, m => """<a href="%s">%s</a>""" format (m.matched, m.matched))
  }

  /**
   * Escape string to HTML entities
   *  
   * @param s
   * @return String
   */
  def escape(s: String): String = {
    xml.Utility.escape(s)
  }

}
