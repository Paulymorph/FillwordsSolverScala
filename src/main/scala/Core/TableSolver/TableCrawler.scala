package Core.TableSolver

import Core.SetDictionary
import Core.TableSolver.InterimStructures.{Point, Table, Word}
import monix.eval.Task

import scala.collection.immutable.HashSet
import scala.collection.mutable.ListBuffer
import scala.concurrent.{Await, Future}
import scala.concurrent.duration.Duration
import monix.execution.Scheduler.Implicits.global


class TableCrawler(table: Table, dictionary: SetDictionary) {
  private val neighbours = Table.generateNeighbours(table.size)


  def findWords(): Iterable[Word] = {
    (for {
      x <- Iterator.range(0, table.size)
      y <- Iterator.range(0, table.size)
    } yield tryToFindWords(new Point(x, y), new HashSet[Point](), "")).reduce(_ ++ _).toSet
  }


  def findWordsParallelTasks(): Iterable[Word] = {
    val tasks = for {
      x <- Iterator.range(0, table.size)
      y <- Iterator.range(0, table.size)
    } yield Task(tryToFindWords(new Point(x, y), new HashSet[Point](), ""))

    val future = Task.gatherUnordered(tasks).runAsync
    Await.ready(future, Duration.Inf)
    val result = future.value.get.getOrElse(List())
    result.reduce(_ ++ _).toSet
  }

  def findWordsParallelFutures(): Iterable[Word] = {
    val tasks = for {
      x <- Iterator.range(0, table.size)
      y <- Iterator.range(0, table.size)
    } yield Future(tryToFindWords(new Point(x, y), new HashSet[Point](), ""))

    val future = Future.sequence(tasks)
    Await.ready(future, Duration.Inf)
    val result = future.value.get.getOrElse(List())
    result.reduce(_ ++ _).toSet
  }


  def tryToFindWords(currentPoint: Point, lastVisited: Set[Point], lastWord: String): ListBuffer[Word] = {

    var result = new ListBuffer[Word]()

    if (lastVisited.contains(currentPoint) || !dictionary.containsStringThatStartsWith(lastWord))
      return result

    val currentWord = lastWord + table.get(currentPoint)
    val currentVisited = lastVisited + currentPoint


    if (dictionary.containsFull(currentWord)) {
      //      println("NEW WORD:", currentWord, currentVisited.map(x => x + "" + table.get(x)).mkString(", "))
      result += Word(currentWord, currentVisited)
    }

    val neighbs = neighbours(currentPoint)
    result ++= neighbs.map((neigb: Point) => {
      tryToFindWords(neigb, currentVisited, currentWord)
    }).reduce((a, b) => a ++ b)

    result
  }
}
