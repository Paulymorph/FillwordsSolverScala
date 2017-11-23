import java.io.File

import Core._
import Core.TableSolver.InterimStructures.Table
import Core.TableSolver._

object Timer {
  private def printTime(s: String, beg: Long, end: Long): Unit = {
    print(s + " " + ((end - beg) / 1e6))
  }
}

class Timer {
  private var _tick = 0l
  private var _lastTack = 0l

  private def getTimeNow = System.nanoTime()


  def tick(): Unit = {
    _tick = getTimeNow
    _lastTack = _tick
  }

  def tack(str: String): Unit = {
    val now = getTimeNow
    Timer.printTime("\n" + str + " from first tick: ", _tick, now)
    Timer.printTime(", from last tack: ", _lastTack, now)
    _lastTack = now
  }
}


object Main extends App {
  val tablePath = new File("./Tables/table3.txt")
  val dictPath = new File("./Dictionary/new_dict_without_yo_and_tire.txt")
  //  val dictPath = new File("./Dictionary/test_dict.txt")

  val timer = new Timer()

  timer.tick()
  val table = new Table(tablePath)
  timer.tack("table read")
  val fileWords = SupportMethods.readFile(dictPath)
  timer.tack("words read")

//  val dict = new SetDictionary(SupportMethods.readFile(dictPath).toSet)
//  val dict = new TrieWordsDictionary(SupportMethods.readFile(dictPath).toSeq)
  val dict = new TraversableTrie(Trie(fileWords))
//  timer.tack("set created")
  //  println(table)
  //  println(dict.prefixes.take(20).toArray.sorted.mkString(", "))


  val crawler = new TableCrawler(table, dict)
  timer.tack("Creation completed.")

  val words = crawler.findWords()
  timer.tack("Words found")
  println("\n" + words.toArray.take(10).sortBy(x => x.word).mkString("\n"))
}