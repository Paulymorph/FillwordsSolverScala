package Core.Dictionary.Implementations

import Core.Dictionary.WordsDictionary

case class SetDictionary(listOfWords: Set[String]) extends WordsDictionary {
  val prefixes: Set[String] = listOfWords.flatMap { word =>
    Iterable.range(0, word.length + 1)
      .map { prefixLength =>
        word.substring(0, prefixLength)
      }
  }

  override def containsStringThatStartsWith(prefix: String): Boolean = prefixes.contains(prefix)

  override def containsFull(fullString: String): Boolean = listOfWords.contains(fullString)
}
