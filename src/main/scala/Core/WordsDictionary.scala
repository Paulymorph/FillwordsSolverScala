package Core

trait WordsDictionary {
  def containsStringThatStartsWith(prefix: String): Boolean
  def containsFull(fullString: String): Boolean
}

object WordsDictionary {
  def apply(collection: Seq[String]): WordsDictionary =
    TrieWordsDictionary(collection)
}
