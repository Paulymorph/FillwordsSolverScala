import java.io.File

import Core.Combiner.ExactCover
import Core._
import Core.TableSolver.InterimStructures.{Table, Word}
import Core.TableSolver._

import org.scalameter._

object Timer {
  private def printTime(s: String, beg: Long, end: Long): Unit = {
    val timeValue = (end - beg) / 1e6
    print(f"$s $timeValue%8.3f")
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
    Timer.printTime("from first tick: ", _tick, now)
    Timer.printTime(", from last tack: ", _lastTack, now)
    print(f" $str\n")
    _lastTack = now
  }
}


object Main extends App {

  def measureExecTime[T](func: () => T): T = {
    var res: T = ???
    val time: Quantity[Double] = withWarmer(new Warmer.Default) measure {
      res = func()
    }
    println(s"Execution time: $time")
    res
  }

  override def main(args: Array[String]): Unit = {
    val tablePath = new File("./resources/Tables/table3.txt")
    val dictPath = new File("./resources/Dictionaries/new_dict_without_yo_and_tire.txt")
    //    val dictPath = new File("./Dictionary/test_dict.txt")

    val timer = new Timer()

    timer.tick()
    val table = new Table(tablePath)
    timer.tack("Read and create table")
    val fileWords = SupportMethods.readFile(dictPath).toSet
    timer.tack("Read dictionary words")

    val dict = new SetDictionary(fileWords)

//    val dict = new TrieWordsDictionary(SupportMethods.readFile(dictPath).toSeq)
//    val dict = new TraversableTrie(Trie(fileWords))

    timer.tack(s"Created dictionary: ${dict.getClass.getName}")

    val crawler = new TableCrawler(table, dict)
    timer.tack("Created table crawler")

    val words: List[Word] = crawler.findWords().toList.filter(w => w.word.length > 2)
    timer.tack("Found words in table")

    //    printWords(words)

    val wordsMap = words.zipWithIndex.map(_.swap).toMap
    val wordsCoors = wordsMap.mapValues(x => x.coordinates.map(p => p.x * table.size + p.y).toSet)
    val coverer = new ExactCover(wordsCoors)

    timer.tack("Done some intermediate conversions")
    val indices: Set[Set[Int]] = coverer.getSolutions
    timer.tack("Combined words to a solid table")

    //    printSolution(indices)
  }

  def printWords(words: List[Word]): Unit = {
    println("\n" + words.size + " words found " + words.toArray.sortBy(x => x.word).mkString("\n") + "\n")
  }

  def printSolution(words: List[Word], indices: Set[Set[Int]]): Unit = {
    val solutions: String = indices.map(_.toList.map(ind => words(ind).word).sorted.mkString(", ")).mkString("\n")
    println(solutions)
  }
}