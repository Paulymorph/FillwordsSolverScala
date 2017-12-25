package Core.PaulCombiner

import Core.TableSolver.InterimStructures.{Point, Word}

object ArrayMatrix {
  def transformWordsToNumbersSet(words: Iterable[Word], tableSize: Int): Map[Word, Set[Point]] = {
    //    val tableSize: Int = words.map(w => w.coordinates.map(coors => coors.x).max).max
    //    val wordsWith1DCoors: Set[Word] = words.map(w =>
    //      Word(w.word, w.coordinates.map(c => new Point(c.x * tableSize + c.y)))).toSet

    //    val coors1D: Map[Word, Set[Point]] =
    words.map(w => w ->
      w.coordinates.
        map(c => new Point(c.x * tableSize + c.y, 0)
        ).toSet
    ).toMap
  }
}


class ArrayMatrix[T, SetInnerType](inputSets: Iterable[Set[SetInnerType]]) extends ExactCoverMatrix[T] {

  override def removeColumn(colNumber: Int): ExactCoverMatrix[T] = ???

  override def removeRow(rowNumber: Int): ExactCoverMatrix[T] = ???

  override def findMinColumn: Int = ???

  override def isEmpty: Boolean = ???

  override def findRowsWithSameElements(colNumber: Int): Iterable[Int] = ???

  override def findColsWithElements(rowNumber: Int): Iterable[Int] = ???

  override def getRow(rowNumber: Int): Iterable[T] = ???
}
