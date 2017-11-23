package Core.TableSolver

import Core.TraversableTrie
import Core.TableSolver.InterimStructures.{Point, Table, Word}

import scala.collection.immutable.HashSet
import scala.collection.mutable.ListBuffer
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Random, Success}

class TableCrawler(table: Table, inputDictionary: TraversableTrie) {
  private val neighbours = Table.generateNeighbours(table.size)


  def findWords(): Iterable[Word] = {
    val futures = (for {
      x <- Iterator.range(0, table.size)
      y <- Iterator.range(0, table.size)
    } yield Future(tryToFindWords(new Point(x, y), new HashSet[Point](), "", inputDictionary.copy()))).toSeq

    Await.result(Future.sequence(futures), Duration.Inf).reduce(_ ++ _)

//    val resultFuture = Future.fold(futures)(new ListBuffer[Word]())(_ ++ _)
//    Await.result(resultFuture, Duration.Inf)
  }


  def tryToFindWords(currentPoint: Point, lastVisited: Set[Point], lastWord: String, dictionary: TraversableTrie): ListBuffer[Word] = {
//    Thread.sleep(Math.abs(new Random().nextLong() % 500))

    if (lastVisited.isEmpty)
      println(currentPoint)


    var result = new ListBuffer[Word]()

    val currentLetter = table.get(currentPoint)

    if (dictionary.next(currentLetter).isEmpty)
      return result

    if (lastVisited.contains(currentPoint)) {
      dictionary.goUp()
      return result
    }

    val currentWord = lastWord + currentLetter
    val currentVisited = lastVisited + currentPoint


    if (dictionary.isWordEnd) {
      //      println("NEW WORD:", currentWord, currentVisited.map(x => x + "" + table.get(x)).mkString(", "))
      result += Word(currentWord, currentVisited)
    }

    val neighbs = neighbours(currentPoint)
    result ++= neighbs.map((neigb: Point) => {
      tryToFindWords(neigb, currentVisited, currentWord, dictionary)
    }).reduce((a, b) => a ++ b)

    dictionary.goUp()
    result
  }
}
