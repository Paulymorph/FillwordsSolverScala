package Core.Dictionary.Trie

import org.scalatest.FlatSpec

class MapTrieTest extends FlatSpec with TrieBehaviours {
  behavior of "Empty map trie"
  it should behave like emptyTrie(ModularTrie(nextNodes = MapEdgesFactory())(MapEdgesFactory))

  behavior of "Map trie with one letter"
  it should behave like oneLetterTrie(ModularTrie(Seq("a"))(MapEdgesFactory), 'a')

  behavior of "Map trie with one word"
  it should behave like oneWordTrie(ModularTrie(Seq("ab"))(MapEdgesFactory), "ab")

  behavior of "Map trie with several words"
  private val words = Seq("abf", "ac", "a")
  it should behave like severalWordsTrie(ModularTrie(words)(MapEdgesFactory), words)
}
