package Core

/**
  * A words dictionary realization on a trie
  * @param trie the trie of the dictionary
  */
class TrieWordsDictionary(trie: Trie) extends WordsDictionary {

  def this(words: Seq[String]) =
    this(Trie(words))

  override def containsStringThatStartsWith(prefix: String): Boolean =
    trie.findSubtrie(prefix).isDefined

  override def containsFull(fullString: String): Boolean =
    trie.findSubtrie(fullString) match {
      case None => false
      case Some(end) => end.isWordEnd
    }
}

object TrieWordsDictionary {
  /**
    * Creates a trie words dictionary with the words
    * @param words the words of the dictionary
    * @return the constructed trie
    */
  def apply(words: Seq[String]) = new TrieWordsDictionary(words)
}
