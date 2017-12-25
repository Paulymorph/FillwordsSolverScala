package Core.PaulCombiner

trait ExactCoverMatrix[T] {
  def removeColumn(colNumber: Int): ExactCoverMatrix[T]

  def removeRow(rowNumber: Int): ExactCoverMatrix[T]

  def findMinColumn: Int

  def isEmpty: Boolean

  def findRowsWithSameElements(colNumber: Int): Iterable[Int]

  def findColsWithElements(rowNumber: Int): Iterable[Int]

  def getRow(rowNumber: Int): Iterable[T]
}
