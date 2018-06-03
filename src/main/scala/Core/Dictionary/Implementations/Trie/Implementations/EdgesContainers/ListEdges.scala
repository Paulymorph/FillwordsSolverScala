package Core.Dictionary.Implementations.Trie.Implementations.EdgesContainers

import Core.Dictionary.Implementations.Trie.Implementations.EdgesContainer
import Core.Dictionary.Implementations.Trie.Trie
import Core.Dictionary.Implementations.Trie.Implementations.Edge

final case class ListEdges(edges: List[Edge] = List.empty) extends EdgesContainer {
  override def +(edge: Edge) = ListEdges(edge :: edges)

  override def contains(letter: Char): Boolean = edges.exists(edge => edge.letter == letter)

  override def letters: Iterable[Char] = edges.map(_.letter)

  override def follow(letter: Char): Option[Trie] = edges.find(_.letter == letter).map(_.destination)
}
