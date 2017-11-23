package Core

import org.scalatest.FlatSpec

class TrieTest extends FlatSpec {
  behavior of "Empty trie"
  it should "add a letter" in {
    assert(Trie().add("a") != null)
  }

  it should "add an empty string and become an end of a word" in {
    assert(Trie().add("").isWordEnd)
  }

  it should "add a word" in {
    assert(Trie().add("aaaaa") != null)
  }

  it should "find no subtries" in {
    assert(Trie().findSubtrie("a").isEmpty)
    assert(Trie().findSubtrie("ab").isEmpty)
    assert(Trie().findSubtrie("swfe").isEmpty)
  }

  behavior of "Trie with one letter"
  val oneLetterTrie: Trie = Trie(Seq("a"))

  it should "have next only one next element" in {
    assert(oneLetterTrie.next('a').isDefined)
    assert(oneLetterTrie.next('b').isEmpty)
    assert(oneLetterTrie.next('z').isEmpty)
  }

  it should "not be a word end" in {
    assert(!oneLetterTrie.isWordEnd)
  }

  it should "have next that is a word end" in {
    val candidate = oneLetterTrie.next('a').get
    assert(candidate.isWordEnd)
  }

  it should "find the 'a' subtrie" in {
    assert(oneLetterTrie.findSubtrie("a").isDefined)
  }

  it should "not find other from the 'a' subtries" in {
    assert(oneLetterTrie.findSubtrie("aa").isEmpty)
    assert(oneLetterTrie.findSubtrie("ab").isEmpty)
    assert(oneLetterTrie.findSubtrie("d").isEmpty)
  }

  it should "merge with empty trie" in {
    assert(oneLetterTrie == (oneLetterTrie merge Trie()))
  }

  behavior of "Trie with one word"
  val oneWordTrie: Trie = Trie(Seq("ab"))

  it should "have next only right next elements" in {
    assert(oneWordTrie.next('b').isEmpty)
    assert(oneWordTrie.next('z').isEmpty)

    assert(oneWordTrie.next('a').isDefined)

    val nextLevel = oneWordTrie.next('a').get
    assert(nextLevel.next('b').isDefined)
    assert(nextLevel.next('c').isEmpty)
  }

  it should "not be a word end" in {
    assert(!oneWordTrie.isWordEnd)
  }

  it should "have a word end on the right position" in {
    val nextLevel = oneWordTrie.next('a').get
    assert(!nextLevel.isWordEnd)
    assert(nextLevel.next('b').get.isWordEnd)
  }

  it should "find the 'a' and 'ab' subtries" in {
    assert(oneWordTrie.findSubtrie("a").isDefined)
    assert(oneWordTrie.findSubtrie("ab").isDefined)
  }

  it should "not find other from the 'ab' subtries" in {
    assert(oneWordTrie.findSubtrie("c").isEmpty)
    assert(oneWordTrie.findSubtrie("abc").isEmpty)
    assert(oneWordTrie.findSubtrie("av").isEmpty)
  }




  behavior of "Trie with several words"
  val severalWordsTrie: Trie = Trie(Seq("abf", "ac", "a"))

  it should "have next only right next elements" in {
    assert(severalWordsTrie.next('b').isEmpty)
    assert(severalWordsTrie.next('z').isEmpty)

    assert(severalWordsTrie.next('a').isDefined)

    val nextLevel = severalWordsTrie.next('a').get
    assert(nextLevel.next('b').isDefined)
    assert(nextLevel.next('c').isDefined)
    assert(nextLevel.next('h').isEmpty)
  }

  it should "not be a word end" in {
    assert(!severalWordsTrie.isWordEnd)
  }

  it should "have a word end on the right positions" in {
    val aLevel = severalWordsTrie.next('a').get
    assert(aLevel.isWordEnd)
    assert(!aLevel.next('b').get.isWordEnd)
    assert(aLevel.next('c').get.isWordEnd)

    val abLevel = aLevel.next('b').get
    assert(abLevel.next('f').get.isWordEnd)
  }

  it should "find the subtries not in the trie" in {
    assert(severalWordsTrie.findSubtrie("a").isDefined)
  }

  it should "find other from the 'a' word" in {
    assert(severalWordsTrie.findSubtrie("aa").isEmpty)
    assert(severalWordsTrie.findSubtrie("abcd").isEmpty)
    assert(severalWordsTrie.findSubtrie("d").isEmpty)
  }

  it should "merge with other tries correctly" in {
    def checkMerge(seq: Iterable[String]) =
      assert((severalWordsTrie merge Trie(seq)) == seq.foldLeft(severalWordsTrie)(_.add(_)))

    checkMerge(Seq("a"))
    checkMerge(Seq("g"))
    checkMerge(Seq("ag", "abc", "srgddgdf"))
    checkMerge(Seq("ag", "abc", "asdw", ""))
    checkMerge(Seq("ag", "abc", "asdw", "ewr", "abf"))
  }
}
