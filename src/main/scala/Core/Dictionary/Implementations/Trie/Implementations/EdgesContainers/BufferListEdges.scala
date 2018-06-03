package Core.Dictionary.Implementations.Trie.Implementations.EdgesContainers

import Core.Dictionary.Implementations.Trie.Implementations.{Edge, EdgesContainer}
import Core.Dictionary.Implementations.Trie.Trie

import scala.collection.mutable.ListBuffer

final case class BufferListEdges(edges: ListBuffer[(Char, Trie)] = ListBuffer.empty) extends EdgesContainer {
  override def +(edge: Edge) = BufferListEdges(edges += edge.tuple)

  override def contains(letter: Char): Boolean = edges.exists(edge => edge._1 == letter)

  override def letters: Iterable[Char] = edges.map(_._1)

  override def follow(letter: Char): Option[Trie] = edges.find(_._1 == letter).map(_._2)
}
