package Core.Dictionary.Implementations.Trie.Implementations

import Core.Dictionary.Implementations.Trie.Trie

import scala.collection.mutable.ListBuffer

trait EdgesModule {
  type Edge = (Char, Trie)

  def next(letter: Char): Option[Trie]

  def add(edge: Edge): EdgesModule

  final def +(edge: Edge): EdgesModule = add(edge)

  def contains(letter: Char): Boolean

  def letters: Iterable[Char]
}


final case class MapEdges(edges: Map[Char, Trie] = Map.empty) extends EdgesModule {
  override def add(edge: Edge) = MapEdges(edges + edge)

  override def contains(letter: Char): Boolean = edges.contains(letter)

  override def letters: Iterable[Char] = edges.keys

  override def next(letter: Char): Option[Trie] = edges.get(letter)
}

final case class ListEdges(edges: List[(Char, Trie)] = List.empty) extends EdgesModule {
  override def add(edge: Edge) = ListEdges(edge :: edges)

  override def contains(letter: Char): Boolean = edges.exists(edge => edge._1 == letter)

  override def letters: Iterable[Char] = edges.map(_._1)

  override def next(letter: Char): Option[Trie] = edges.find(_._1 == letter).map(_._2)
}

final case class BufferListEdges(edges: ListBuffer[(Char, Trie)] = ListBuffer.empty) extends EdgesModule {
  override def add(edge: Edge) = BufferListEdges(edges += edge)

  override def contains(letter: Char): Boolean = edges.exists(edge => edge._1 == letter)

  override def letters: Iterable[Char] = edges.map(_._1)

  override def next(letter: Char): Option[Trie] = edges.find(_._1 == letter).map(_._2)
}

final case class ArrayEdges(edges: Array[Option[Trie]],
                            hash: Function[Char, Int],
                            space: Int,
                            private var lettersList: List[Char]) extends EdgesModule {

  override def add(edge: Edge): ArrayEdges = {
    lettersList = edge._1 :: lettersList
    getHash(edge._1) match {
      case Some(h) => edges(h) = Option(edge._2)
      case None => throw new IllegalArgumentException(s"$edge was out of bounds")
    }
    this
  }

  private def getHash(letter: Char): Option[Int] = {
    val letterHash = hash(letter)
    if (letterHash >= 0 && letterHash < space)
      Some(letterHash)
    else None
  }

  override def letters: Iterable[Char] = lettersList

  override def contains(letter: Char): Boolean =
    getHash(letter) match {
      case Some(h) => edges(h).isDefined
      case None => false
    }

  def this(hash: Function[Char, Int], space: Int) =
    this(Array.fill(space)(None), hash, space, List())

  override def next(letter: Char): Option[Trie] = getHash(letter).flatMap(edges(_))
}