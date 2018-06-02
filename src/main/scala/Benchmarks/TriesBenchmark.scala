package Benchmarks

import java.io.File

import Core.Dictionary.Implementations.Trie.Implementations.{MapEdgesFactory, ModularTrie}
import Core.Dictionary.Implementations.{SetDictionary, TrieWordsDictionary}
import Core.Dictionary.WordsDictionary
import org.scalameter._

import scala.collection.concurrent.TrieMap

class LibTrieDict(trie: TrieMap[String, Boolean]) extends WordsDictionary {
  override def containsStringThatStartsWith(prefix: String): Boolean = trie.contains(prefix)

  override def containsFull(fullString: String): Boolean = trie.contains(fullString)
}

object TriesBenchmark {
  val standardConfig = config(
    Key.exec.minWarmupRuns -> 10,
    Key.exec.maxWarmupRuns -> 10,
    Key.exec.benchRuns -> 10,
    Key.exec.independentSamples -> 10
  ) withWarmer new Warmer.Default

  def time[R](block: => R, name: String = "Elapsed time"): R = {
    val last = standardConfig.measure({
      block
    })

    println(s"$name: $last")
    block
  }

  def testTrie(trie: WordsDictionary, name: String, search: Iterable[String]): Unit = {
    time(search.foreach(word => {
      trie.containsStringThatStartsWith(word)
    }), name + " search")
  }

  def main(args: Array[String]): Unit = {
    val dictFile = "./Dictionary/new_dict_without_yo_and_tire.txt"
    val file = io.Source.fromFile(new File(dictFile))
    val words = file.getLines().toArray

    println("\n++++++++++++++++++++ Construction of tries ++++++++++++++++++++")
    val defaultMapTrieParallel = time(
      new TrieWordsDictionary(ModularTrie.parallelConctruct(words)),
      "parallel map trie dictionary construction"
    )
    val defaultMapTrieSeqeuntial = time(
      new TrieWordsDictionary(ModularTrie(words)(MapEdgesFactory)),
      "cons trie dictionary construction"
    )

    val setDictionary = time(
      new SetDictionary(words.toSet),
      "set dictionary construction"
    )


    println("\n------------------Another time----------------------")
    time(new TrieWordsDictionary(ModularTrie.parallelConctruct(words)), "parallel map trie dictionary construction")
    time(new TrieWordsDictionary(ModularTrie(words)(MapEdgesFactory)), "cons map trie dictionary construction")
    time(new SetDictionary(words.toSet), "set dictionary construction")

    val searchWords = words ++ Seq("ываш", "шуграцшг", "приветик")
    val libRes = searchWords.map(defaultMapTrieParallel.containsFull)
    val libDef = searchWords.map(defaultMapTrieSeqeuntial.containsFull)
    assert(libRes.sameElements(libDef))

    println("\n++++++++++++++++++++ Search on tries ++++++++++++++++++++")
    testTrie(defaultMapTrieParallel, "parallel map trie", searchWords)
    testTrie(defaultMapTrieSeqeuntial, "cons map trie dictionary", searchWords)
    testTrie(setDictionary, "set dictionary", searchWords)

    println("\n------------------Another time----------------------")
    testTrie(defaultMapTrieParallel, "parallel map trie", searchWords)
    testTrie(defaultMapTrieSeqeuntial, "cons map trie dictionary", searchWords)
    testTrie(setDictionary, "set dictionary", searchWords)
  }
}
