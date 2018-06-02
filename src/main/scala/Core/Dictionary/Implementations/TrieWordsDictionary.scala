package Core.Dictionary.Implementations

import Core.Dictionary.Implementations.Trie.Implementations.{MapEdgesFactory, ModularTrie}
import Core.Dictionary.Implementations.Trie.{Trie => _Trie}
import Core.Dictionary.WordsDictionary

/**
  * A words dictionary realization on a trie
  *
  * @param trie the trie of the dictionary
  */
class TrieWordsDictionary(trie: _Trie) extends WordsDictionary {

  def this(words: Iterable[String]) = {
    this(ModularTrie(words)(MapEdgesFactory))
  }

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
    *
    * @param words the words of the dictionary
    * @return the constructed trie
    */
  def apply(words: Iterable[String]) = new TrieWordsDictionary(words)
}
