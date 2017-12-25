package Core.Combiner

import scala.collection.mutable
import scala.collection.mutable.ListBuffer


class ExactCover[T](input: Seq[Set[T]]) {
  private val inputSets: Map[Int, Set[T]] = input.zipWithIndex.map(_.swap).toMap

  private val distinctCount: Int = inputSets.values.flatten.toSet.size

  val columns: Map[T, Set[Int]] =
    inputSets
      .map { case (ind, set) => set.map(el => el -> ind)}
      .reduce(_ ++ _)
      .groupBy(_._1)
      .mapValues(_.map(_._2))



  private def printChoice(lst: Iterable[Int], s: String = ""): Unit = {
    println(s + " " + lst + " => " + lst.map(x => inputSets(x)).toList.flatten
      + " = " + lst.toList.sorted.map(x => x + "->" + inputSets(x)).mkString(", "))
  }

  private var allSolutions = ListBuffer[Set[Int]]()

  private var isSolved = false

  def getSolutions: ListBuffer[Set[Int]] = {
    println("inputSets: " + inputSets)
    println("columns: " + columns)
    if (!isSolved)
      solve()
    allSolutions
  }


  private def solve(inSolution: mutable.HashSet[Int] = new mutable.HashSet[Int],
                    chosenColumns: mutable.HashSet[T] = new mutable.HashSet[T],
                    chosenRows: mutable.HashSet[Int] = new mutable.HashSet[Int]): Unit = {

    if (chosenColumns.size == distinctCount) {
      allSolutions += inSolution.toSet
      printChoice(inSolution, "\nSolution")
      return
    }
    //    print("\nIn solution: " + inSolution + ", chosen cols: " + chosenColumns + ", chosen rows: " + chosenRows + "\n")

    val leftColumns: Seq[T] = (columns.keySet -- chosenColumns).toSeq
    val leftColumnsSizes: Seq[Int] = leftColumns.map(x => (columns(x) -- chosenRows).size)
    val leftColumnsSizesMin: Int = leftColumnsSizes.min

    //    printChoice(chosenRows, "Chosen rows:")
    //    println(leftColumns)
    //    println(leftColumnsSizesMin)

    //    if (leftColumnsSizesMin == 0)
    //      return

    val bestColumnIndex: Int = leftColumnsSizes.indexOf(leftColumnsSizesMin)
    val bestColumnElement: T = leftColumns.apply(bestColumnIndex)
    val rowsOfBestColumn: Set[Int] = columns(bestColumnElement) -- chosenRows
    //    println("Best index: " + bestColumnIndex + ", element: " + bestColumnElement + ", rowsOfBestColumn: " + rowsOfBestColumn)


    rowsOfBestColumn.foreach { row =>
      inSolution += row
      chosenColumns ++= inputSets(row)

      val intersectingSets: Set[Int] = inputSets(row).flatMap(el => columns(el))

      //      println("With " + row + " intersect " + (intersectingSets - row))

      chosenRows ++= intersectingSets

      solve(inSolution, chosenColumns, chosenRows)

      inSolution -= row
      chosenRows --= intersectingSets
      chosenColumns --= inputSets(row)
    }
  }
}


