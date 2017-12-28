package Core.Dictionary.Trie

import org.scalatest.FlatSpec

class TrieTest extends FlatSpec with TrieBehaviours {
  behavior of "Empty trie"
  it should behave like emptyTrie(Trie())

  behavior of "Trie with one letter"
  it should behave like oneLetterTrie(Trie(Seq("a")), 'a')

  behavior of "Trie with one word"
  it should behave like oneWordTrie(Trie(Seq("ab")), "ab")

  behavior of "Trie with several words"
  private val words = Seq("abf", "ac", "a")
  it should behave like severalWordsTrie(Trie(words), words)
}
