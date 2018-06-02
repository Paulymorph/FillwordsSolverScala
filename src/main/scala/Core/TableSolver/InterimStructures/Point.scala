package Core.TableSolver.InterimStructures

final case class Point(x: Int, y: Int) {
  override def toString: String =
    s"($x, $y)"
}

object Point {
  def getNeighbours(x: Int, y: Int, tableSize: Int): Iterable[Point] = {
    def within(number: Int, min: Int = 0, max: Int = tableSize): Boolean = {
      min <= number && number < max
    }

    val difs = (-1 to 1).zip(-1 to 1)

    val neighbours = difs
      .map { case (dx, dy) =>
        Point(x + dx, y + dy)
      }
      .filter { candidate =>
        (candidate.x == x) ^ (candidate.y == y)
      }
      .filter { candidate =>
        within(candidate.x) && within(candidate.y)
      }

    neighbours
  }
}
