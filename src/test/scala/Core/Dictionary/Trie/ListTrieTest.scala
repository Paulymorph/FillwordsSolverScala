package Core.Dictionary.Trie

import org.scalatest.FlatSpec

class ListTrieTest extends FlatSpec with TrieBehaviours {
  behavior of "Empty list trie"
  it should behave like emptyTrie(ModularTrie(nextNodes = ListEdgesFactory())(ListEdgesFactory))

  behavior of "List trie with one letter"
  it should behave like oneLetterTrie(ModularTrie(Seq("a"))(ListEdgesFactory), 'a')

  behavior of "List trie with one word"
  it should behave like oneWordTrie(ModularTrie(Seq("ab"))(ListEdgesFactory), "ab")

  behavior of "List trie with several words"
  private val words = Seq("abf", "ac", "a")
  it should behave like severalWordsTrie(ModularTrie(words)(ListEdgesFactory), words)
}
