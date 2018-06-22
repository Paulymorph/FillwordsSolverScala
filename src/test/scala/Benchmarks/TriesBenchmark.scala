package Benchmarks

import java.io.File

import Core.Dictionary.Implementations.Trie.Implementations.{MapEdgesFactory, ModularTrie}
import Core.Dictionary.Implementations.{SetDictionary, TrieWordsDictionary}
import Core.Dictionary.WordsDictionary
import org.scalameter._

import scala.io.Source

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
    val dictFilename = args.headOption.getOrElse("./Dictionary/new_dict_without_yo_and_tire.txt")
    val file = Source.fromFile(new File(dictFilename))
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
      SetDictionary(words.toSet),
      "set dictionary construction"
    )

    println("\n------------------Another time----------------------")
    time(new TrieWordsDictionary(ModularTrie.parallelConctruct(words)), "parallel map trie dictionary construction")
    time(new TrieWordsDictionary(ModularTrie(words)(MapEdgesFactory)), "cons map trie dictionary construction")
    time(SetDictionary(words.toSet), "set dictionary construction")


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
