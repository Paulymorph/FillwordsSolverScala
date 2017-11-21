package Core.TableSolver.InterimStructures

import scala.collection.mutable.ListBuffer

final case class Point(x: Int, y: Int) {
  override def toString: String =
    s"($x, $y)"
}

object Point {
  def getNeighbours(x: Int, y: Int, tableSize: Int): Iterable[Point] = {
    val neighbours = new ListBuffer[Point]
    if (x > 0)
      neighbours += new Point(x - 1, y)
    if (x < tableSize - 1)
      neighbours += new Point(x + 1, y)
    if (y > 0)
      neighbours += new Point(x, y - 1)
    if (y < tableSize - 1)
      neighbours += new Point(x, y + 1)

    //    println(x, y, "\t", neighbours.mkString(", "))
    neighbours.toList
  }
}
