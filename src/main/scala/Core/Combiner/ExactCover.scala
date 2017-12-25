package Core.Combiner

import scala.collection.mutable

class ExactCover[T](indexToSet: Map[Int, Set[T]]) {

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
    if (leftElemsColumns.isEmpty) {
      allSolutions += inSolutionRows
//      println(f"!!! Solution: $inSolutionRows") // TODO Remove similar sets of solutions
      return
    }

    // Drop used rows and elems
    val leftElementsWithIndices: Map[T, Set[Int]] = elemToIndex.filterKeys(leftElemsColumns.contains).mapValues(_ & leftIndicesRows)
    val (elem, indices) = leftElementsWithIndices.minBy(_._2.size)


    indices.foreach{x =>
      val indicesToDrop = indexToSet(x).flatMap(elInSet => elemToIndex(elInSet))
      solve(leftIndicesRows -- indicesToDrop,
        (leftElemsColumns -- indexToSet(x)) - elem,
        inSolutionRows + x)
    }
  }
}
