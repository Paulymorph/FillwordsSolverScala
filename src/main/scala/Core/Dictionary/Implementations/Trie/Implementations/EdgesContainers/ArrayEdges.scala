package Core.Dictionary.Implementations.Trie.Implementations.EdgesContainers

import Core.Dictionary.Implementations.Trie.Implementations.{Edge, EdgesContainer}
import Core.Dictionary.Implementations.Trie.Trie

final case class ArrayEdges(edges: Array[Option[Trie]],
                            hash: Function[Char, Int],
                            space: Int,
                            private var lettersList: List[Char]) extends EdgesContainer {

  def this(hash: Function[Char, Int], space: Int) =
    this(Array.fill(space)(None), hash, space, List())

  override def +(edge: Edge): ArrayEdges = {
    getHash(edge.letter).fold(throw new IllegalArgumentException(s"$edge was out of bounds")) {
      hash =>
        edges(hash) = Option(edge.destination)
    }

    lettersList = edge.letter :: lettersList

    this
  }

  override def letters: Iterable[Char] = lettersList

  override def contains(letter: Char): Boolean = follow(letter).isDefined

  override def follow(letter: Char): Option[Trie] = getHash(letter).flatMap(edges)

  private def getHash(letter: Char): Option[Int] = {
    val letterHash = hash(letter)
    if (letterHash >= 0 && letterHash < space)
      Some(letterHash)
    else None
  }
}
