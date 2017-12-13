package Benchmarks

import java.io.File

import Core._
import org.scalameter._

object TriesBenchmark extends App {
  val standardConfig = config(
    Key.exec.minWarmupRuns -> 5,
    Key.exec.maxWarmupRuns -> 10,
    Key.exec.benchRuns -> 10,
  ) withWarmer (new Warmer.Default)

  new TraversableTrieDictionary(Seq("asd", "sdf"))

  val dictFile = "./Dictionary/new_dict_without_yo_and_tire.txt"
  val file = io.Source.fromFile(new File(dictFile))
  val words = file.getLines().take(5000).toArray

  val defName = "default map"

  println("\n++++++++++++++++++++ Construction of tries ++++++++++++++++++++")
  val defaultTrie = time(Trie(words), defName + " construction")
  val oldMapName = "old map trie"
  val oldMapTrie = time(words.foldLeft(Trie())((acc, i) => acc.add(i)), oldMapName)
  val mapName = "map trie"
  val mapTrie = time(ModularTrie(words, MapEdges(Map.empty)), mapName + " construction")
  val arrName = "array trie"
  val arrTrie = time(ModularTrie(words, new ArrayEdges(_ - 'А', 33)), arrName + " construction")
  val listName = "list trie"
  val listTrie = time(ModularTrie(words, ListEdges(List())), listName + " construction")
  val listBufferName = "list buffer trie"
  val listBufferTrie = time(ModularTrie(words, ListBufferEdges()), listBufferName + " construction")
  val searchWords = words.take(1000)


  def testTrie(trie: Trie, name: String, search: Iterable[String]): Unit = {
    time(search.foreach(word => {
      trie.findSubtrie(word)
    }), name + " search")
  }

  def time[R](block: => R, name: String = "Elapsed time"): R = {
    val last = standardConfig.measure({
      block
    })

    println(s"$name: $last")
    block
  }

  println("\n++++++++++++++++++++ Search on tries ++++++++++++++++++++")
  testTrie(defaultTrie, defName, searchWords)
  testTrie(oldMapTrie, oldMapName, searchWords)
  testTrie(mapTrie, mapName, searchWords)
  testTrie(arrTrie, arrName, searchWords)
  testTrie(listTrie, listName, searchWords)
  testTrie(listBufferTrie, listBufferName, searchWords)
}
