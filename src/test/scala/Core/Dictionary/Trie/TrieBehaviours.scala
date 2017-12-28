package Core.Dictionary.Trie

import org.scalatest.FlatSpec

trait TrieBehaviours {
  this: FlatSpec =>

  def emptyTrie(newTrie: => Trie): Unit = {
    it should "add a letter" in {
      assert(newTrie.add("a") != null)
    }

    it should "add an empty string and become an end of a word" in {
      assert(newTrie.add("").isWordEnd)
    }

    it should "add a word" in {
      assert(newTrie.add("aaaaa") != null)
    }

    it should "find no subtries" in {
      assert(newTrie.findSubtrie("a").isEmpty)
      assert(newTrie.findSubtrie("ab").isEmpty)
      assert(newTrie.findSubtrie("swfe").isEmpty)
    }
  }

  def oneLetterTrie(newTrie: => Trie, letter: Char): Unit = {
    val otherLetters = genOtherLetters(letter)

    it should "have next only one next element" in {
      assert(newTrie.next(letter).isDefined)
      otherLetters.foreach(otherLetter =>
        assert(newTrie.next(otherLetter).isEmpty))
    }

    it should "not be a word end" in {
      assert(!newTrie.isWordEnd)
    }

    it should "have next that is a word end" in {
      val candidate = newTrie.next(letter).get
      assert(candidate.isWordEnd)
      assert(candidate.edgesLetters.isEmpty)
    }

    it should s"find the '$letter' subtrie" in {
      assert(newTrie.findSubtrie(letter.toString).isDefined)
    }

    it should "not find other from the 'a' subtries" in {
      otherLetters.foreach(letter => assert(newTrie.findSubtrie(letter.toString).isEmpty))
      val otherWords = (otherLetters :+ letter)
        .flatMap(letter => otherLetters.map(letter.toString + _))
        .take(20)
      otherWords.foreach(word => assert(newTrie.findSubtrie(word).isEmpty))
    }

    //    it should "merge with empty trie" in {
    //      assert(newTrie == (newTrie merge Trie()))
    //    }
  }

  private def genOtherLetters(letter: Char) = {
    val noZeroSeq = (-20 to -10).union(1 to 10)
    for (i <- noZeroSeq)
      yield (letter + i).toChar
  }

  def oneWordTrie(newTrie: => Trie, word: String): Unit = {
    val otherStartLetters = genOtherLetters(word.head)

    it should "have next only for right next elements" in {
      assert(newTrie.next(word.charAt(0)).isDefined)

      otherStartLetters.foreach(letter =>
        assert(newTrie.next(letter).isEmpty))


      val level2OtherLetters = genOtherLetters(word.charAt(1))
      val nextLevel = newTrie.next(word.head).get
      assert(nextLevel.next(word.charAt(1)).isDefined)

      level2OtherLetters.foreach(letter => {
        assert(nextLevel.next(letter).isEmpty)
      })
    }

    it should "not be a word end" in {
      assert(!newTrie.isWordEnd)
    }

    it should "have a word end on the right position" in {
      assert(newTrie.findSubtrie(word).isDefined)
    }
  }

  def severalWordsTrie(newTrie: => Trie, words: Iterable[String]): Unit = {

    //    it should behave like oneWordTrie(newTrie, words.head)

    it should "have a word end on the right positions" in {
      words.foreach(word =>
        assert(newTrie.findSubtrie(word).get.isWordEnd))
    }

    it should "not find the subtries not in the trie" in {
      val someWords = Seq("bla", "rand", "sdf", "afd", "qw", "pq")
      val wordsNoPrefixInPresent = someWords
        .filter(word => !words.forall(_.startsWith(word)))
      wordsNoPrefixInPresent.foreach(noPrefix =>
        assert(newTrie.findSubtrie(noPrefix).isEmpty))
    }

    //    it should "find other from the 'a' word" in {
    //      assert(newTrie.findSubtrie("aa").isEmpty)
    //      assert(newTrie.findSubtrie("abcd").isEmpty)
    //      assert(newTrie.findSubtrie("d").isEmpty)
    //    }

    it should "merge with other tries correctly" in {
      def checkMerge(seq: Iterable[String]) =
        assert((newTrie merge Trie(seq)) == seq.foldLeft(newTrie)(_.add(_)))

      checkMerge(Seq("a"))
      checkMerge(Seq("g"))
      checkMerge(Seq("ag", "abc", "srgddgdf"))
      checkMerge(Seq("ag", "abc", "asdw", ""))
      checkMerge(Seq("ag", "abc", "asdw", "ewr", "abf"))
    }
  }
}
