//package Core.Dictionary
//
//import org.scalatest.FlatSpec
//
//class WordsDictionaryTest extends FlatSpec {
//
//  behavior of "WordsDictionary"
//  val someWords: Seq[String] = Seq("aa", "abc", "a", "ba")
//  val someDict = WordsDictionary(someWords)
//
//  it should "contain all prefixes of the words in the sequence" in {
//    for {
//      word <- someWords
//      prefixSize <- 1 to word.length
//      prefix = word.substring(0, prefixSize)
//    } assert(someDict.containsStringThatStartsWith(prefix))
//  }
//
//  it should "contain all words from the sequence" in {
//    someWords.foreach(
//      word => assert(someDict.containsFull(word))
//    )
//  }
//
//  it should "not contain prefixes of the words in the sequence" in {
//    val wrongPrefixes = Seq("q", "ac", "aaa")
//    for (prefix <- wrongPrefixes)
//      assert(!someDict.containsStringThatStartsWith(prefix))
//  }
//
//  it should "not have words not in the dictionary" in {
//    val wordsNotIn = Seq("df", "d", "ds", "ab").filter(!someWords.contains(_))
//
//    wordsNotIn.foreach(notIn =>
//      assert(!someDict.containsFull(notIn)))
//  }
//}
