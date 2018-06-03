package Core.Dictionary.Implementations.Trie.Implementations

import Core.Dictionary.Implementations.Trie.Trie

import scala.collection.mutable.ListBuffer

case class Edge(letter: Char, destination: Trie) {
  def tuple: (Char, Trie) = letter -> destination
}

trait EdgesContainer {

  def follow(letter: Char): Option[Trie]

  def +(edge: Edge): EdgesContainer

  def contains(letter: Char): Boolean

  def letters: Iterable[Char]
}