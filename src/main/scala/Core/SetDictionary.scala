package Core


class SetDictionary(listOfWords: Set[String]) extends WordsDictionary {
  val prefixes: Set[String] = listOfWords.flatMap { word =>
    Iterable.range(0, word.length + 1).map { number => word.substring(0, number) } // TODO How to use flatMap here?
  }

  override def containsStringThatStartsWith(prefix: String): Boolean = prefixes.contains(prefix)

  override def containsFull(fullString: String): Boolean = listOfWords.contains(fullString)
}
