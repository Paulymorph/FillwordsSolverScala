package Core.Dictionary.Trie

import org.scalatest.FlatSpec

class ArrayTrieTest extends FlatSpec with TrieBehaviours {
  private val words = Seq("abf", "ac", "a")

  behavior of "Empty array trie"
  it should behave like emptyTrie(createArrayTrie(Seq()))

  behavior of "Array trie with one letter"
  it should behave like oneLetterTrie(createArrayTrie(Seq("a")), 'a')


  behavior of "Array trie with one word"
  it should behave like oneWordTrie(createArrayTrie(Seq("ab")), "ab")

  behavior of "Array trie with several words"

  private def createArrayTrie(words: Iterable[String]) = {
    ModularTrie(words)(ArrayEdgesFactory(_ - 'a', 33))
  }

  it should behave like severalWordsTrie(createArrayTrie(words), words)
}
