package Core.Dictionary.Trie

import org.scalatest.FlatSpec

class BufferListTrieTest extends FlatSpec with TrieBehaviours {
  behavior of "Empty buffer list trie"
  it should behave like emptyTrie(ModularTrie(nextNodes = BufferListEdges())(BufferListEdgesFactory))

  behavior of "Buffer list trie with one letter"
  it should behave like oneLetterTrie(ModularTrie(Seq("a"))(BufferListEdgesFactory), 'a')

  behavior of "Buffer list trie with one word"
  it should behave like oneWordTrie(ModularTrie(Seq("ab"))(BufferListEdgesFactory), "ab")

  behavior of "Buffer list trie with several words"
  private val words = Seq("abf", "ac", "a")
  it should behave like severalWordsTrie(ModularTrie(words)(BufferListEdgesFactory), words)
}
