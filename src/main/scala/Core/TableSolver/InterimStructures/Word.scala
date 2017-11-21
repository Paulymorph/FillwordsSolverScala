package Core.TableSolver.InterimStructures

case class Word(word: String, coordinates: Iterable[Point]) {
  override def toString: String =
    String.format("%s: %s", word, coordinates.mkString(", "))
}
