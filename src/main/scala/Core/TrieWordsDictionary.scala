package Core

class TrieWordsDictionary(trie: Trie) extends WordsDictionary {
  def this(words: Seq[String]) = {
    this(words.foldLeft(Trie())((acc, i) => acc.add(i)))
    println(this)
  }

  override def containsStringThatStartsWith(prefix: String): Boolean =
    trie.findWord(prefix).isDefined

  override def containsFull(fullString: String): Boolean =
    trie.findWord(fullString) match {
      case None => false
      case Some(end) => end.isWordEnd
    }
}

object TrieWordsDictionary {
  def apply(words: Seq[String]) = new TrieWordsDictionary(words)
}
