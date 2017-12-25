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

  def measureExecTime[T](func: => T): T = {
    var res: Option[T] = None
    val time: Quantity[Double] = config(
      Key.exec.benchRuns -> 10,
      Key.exec.maxWarmupRuns -> 10,
      Key.verbose -> true
    ) withWarmer (new Warmer.Default) measure {
      res = Option(func)
    }
    println(s"Execution time: $time")
    res.get
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

    //    val dict: WordsDictionary = new SetDictionary(fileWords)

    //    val dict = new TrieWordsDictionary(fileWords)

    val listOfDicts: List[Class[_ <: WordsDictionary]] =
      List(classOf[SetDictionary], classOf[TrieWordsDictionary])
    timer.tack("Created list of dicts")

    // !!!!!!!!!!!!!!!!!
    val printWhileTesting = false
    // !!!!!!!!!!!!!!!!!

    listOfDicts.foreach { dictType: Class[_ <: WordsDictionary] =>
      val dictName = dictType.getName
      println(s"Start testing '$dictName'")
      val res = measureExecTime({
        val dict: WordsDictionary = dictType.getConstructors()(0).newInstance(fileWords).asInstanceOf[WordsDictionary]
        if (printWhileTesting)
          timer.tack(s"Created dictionary: ${dictName})")

        val crawler = new TableCrawler(table, dict)
        if (printWhileTesting)
          timer.tack("Created table crawler")

        val words: List[Word] = crawler.findWords().toList.filter(w => w.word.length > 2)
        if (printWhileTesting)
          timer.tack("Found words in table")
      })
    }


    //    printWords(words)

    //    val wordsMap = words.zipWithIndex.map(_.swap).toMap
    //    val wordsCoors = wordsMap.mapValues(x => x.coordinates.map(p => p.x * table.size + p.y).toSet)
    //    timer.tack("Done some intermediate conversions")
    //
    //    val coverer = new ExactCover(wordsCoors)
    //    timer.tack("Created coverer")
    //
    //    val indices: Set[Set[Int]] = coverer.getSolutions
    //    timer.tack("Combined words to a solid table")
    //
    //    printSolution(words, indices)
  }

  def printWords(words: List[Word]): Unit = {
    println("\n" + words.size + " words found " + words.toArray.sortBy(x => x.word).mkString("\n") + "\n")
  }

  def printSolution(words: List[Word], indices: Set[Set[Int]]): Unit = {
    val solutions: String = indices.map(_.toList.map(ind => words(ind).word).sorted.mkString(", ")).mkString("\n")
    println(solutions)
  }
}