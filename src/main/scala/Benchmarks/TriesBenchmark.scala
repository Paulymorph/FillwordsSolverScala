package Benchmarks

import java.io.File

import Core._

object TriesBenchmark extends App {
  val dictFile = "./Dictionary/new_dict_without_yo_and_tire.txt"
  val file = io.Source.fromFile(new File(dictFile))
  val words = file.getLines().toArray
  val defName = "default map"
  val defaultTrie = time(Trie(words), defName + " construction")

  time(Trie(words), "heating the seq")

  println("\n++++++++++++++++++++ Construction of tries ++++++++++++++++++++")
  val oldMapName = "old map trie"
  val oldMapTrie = time(words.foldLeft(Trie())((acc, i) => acc.add(i)), oldMapName)
  val mapName = "map trie"
  val mapTrie = time(ModularTrie(words, MapEdges(Map.empty)), mapName + " construction")
  val arrName = "array trie"
  val arrTrie = time(ModularTrie(words, new ArrayEdges(_ - 'А', 33)), arrName + " construction")
  val listName = "list trie"
  val listTrie = time(ModularTrie(words, ListEdges(List())), listName + " construction")
  val searchWords = words.take(1000)

  def testTrie(trie: Trie, name: String, search: Iterable[String]): Unit = {
    time(search.foreach(word => {
      trie.findSubtrie(word)
    }), name + " search")
  }

  def time[R](block: => R, name: String = "Elapsed time"): R = {
    var times: List[Long] = List()
    val result = block
    for {
      i <- 1 to 9
      t0 = System.nanoTime()
      res = block // call-by-name
      t1 = System.nanoTime()
    } times = (t1 - t0) :: times

    println(s"$name: ${times.sum / 1e9} seconds")
    result
  }

  println("\n++++++++++++++++++++ Search on tries ++++++++++++++++++++")
  testTrie(defaultTrie, defName, searchWords)
  testTrie(oldMapTrie, oldMapName, searchWords)
  testTrie(mapTrie, mapName, searchWords)
  testTrie(arrTrie, arrName, searchWords)
  testTrie(listTrie, listName, searchWords)
}
