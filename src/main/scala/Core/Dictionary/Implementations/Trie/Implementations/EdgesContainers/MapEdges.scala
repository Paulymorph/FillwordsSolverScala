package Core.Dictionary.Implementations.Trie.Implementations.EdgesContainers

import Core.Dictionary.Implementations.Trie.Implementations.{Edge, EdgesContainer}
import Core.Dictionary.Implementations.Trie.Trie

final case class MapEdges(edges: Map[Char, Trie] = Map.empty) extends EdgesContainer {
  override def +(edge: Edge) = MapEdges(edges + edge.tuple)

  override def contains(letter: Char): Boolean = edges.contains(letter)

  override def letters: Iterable[Char] = edges.keys

  override def follow(letter: Char): Option[Trie] = edges.get(letter)
}
