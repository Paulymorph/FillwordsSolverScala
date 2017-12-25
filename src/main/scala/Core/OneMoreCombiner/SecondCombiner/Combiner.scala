package Core.OneMoreCombiner.SecondCombiner

import java.util.Comparator

import scala.collection.mutable

class Combiner[T](indexToSet: Map[Int, Set[T]]) {

  val elemToIndex: Map[T, Set[Int]] =
    indexToSet.
      toList.
      flatMap { case (ind, set) => set.map((_, ind)) }.
      groupBy(_._1).
      mapValues(_.map(_._2).toSet)

  var allSolutions: mutable.HashSet[Set[Int]] = mutable.HashSet.empty

  def getSolutions: Set[Set[Int]] = {
    solve()
    allSolutions.toSet
  }

  private def solve(leftIndicesRows: Set[Int] = indexToSet.keySet,
                    leftElemsColumns: Set[T] = elemToIndex.keySet,
                    inSolutionRows: Set[Int] = Set.empty): Unit = {

//    println(f"\nLeft indices: ${leftIndicesRows.toSeq.sorted.mkString(", ")}")
//    println(f"Left elements: ${leftElemsColumns.mkString(", ")}")
//    println(f"InSolution: ${inSolutionRows.toSeq.mkString(", ")} = ${indexToSet.filterKeys(inSolutionRows.contains).values.flatten.mkString(", ")}")

    if (leftElemsColumns.isEmpty) {
      allSolutions += inSolutionRows
      println(f"!!! Solution: $inSolutionRows")
      return
    }

    // Drop used rows and elems
    val leftElementsWithIndices: Map[T, Set[Int]] = elemToIndex.filterKeys(leftElemsColumns.contains).mapValues(_ & leftIndicesRows)
    val (elem, indices) = leftElementsWithIndices.minBy(_._2.size)



//    println(f"Chose: ($elem, $indices)")

    indices.foreach{x =>
      val indicesToDrop = indexToSet(x).flatMap(elInSet => elemToIndex(elInSet))
//      print(f"\t chose $x index: ${indexToSet(x)}. Indices to drop $indicesToDrop\n")
      solve(leftIndicesRows -- indicesToDrop,
        (leftElemsColumns -- indexToSet(x)) - elem,
        inSolutionRows + x)
    }
  }
}
