package Core.TableSolver.InterimStructures

import java.io.File

import Core.SupportMethods
import Core.TableSolver.InterimStructures.Point.getNeighbours
import Core.TableSolver.InterimStructures.Table.Matrix

object Table {
  type Matrix[T] = Array[Array[T]]

  def charsMatrixFromString(lines: Iterable[String]): Matrix[Char] =
    lines.map(line => line.toCharArray.filter(c => c != ' ')).toArray

  def createVisitedMatrix(size: Int): Matrix[Boolean] = Array.ofDim(size, size)

  def generateNeighbours(tableSize: Int): Map[Point, Iterable[Point]] =
    Iterator.range(0, tableSize).flatMap(x =>
      Iterator.range(0, tableSize).map(y =>
        new Point(x, y) -> getNeighbours(x, y, tableSize))).toMap
}


class Table(inputTable: Matrix[Char],
            visited: Matrix[Boolean]) {

  val size: Int = inputTable.length

  def get(point: Point): Char = inputTable(point.x)(point.y)

  def this(inputTable: Matrix[Char]) {
    this(inputTable, Table.createVisitedMatrix(inputTable.length))
  }

  def this(file: File) {
    this(Table.charsMatrixFromString(SupportMethods.readFile(file)))
  }

  override def toString: String =
    inputTable.map(line => line.mkString(" ")).mkString("\n")


  @Override
  override def clone(): Table = new Table(inputTable)


}
