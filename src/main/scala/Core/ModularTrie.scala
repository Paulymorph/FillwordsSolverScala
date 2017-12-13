package Core

import scala.collection.mutable.ListBuffer

/**
  * A trie with map as a way to keep the next nodes
  *
  * @param isWordEnd a flag to state whether the
  * @param nextNodes a map of nodes that are on the next level of the trie
  */
final case class ModularTrie(isWordEnd: Boolean = false, nextNodes: EdgesModule) extends Trie {

  override def findSubtrie(word: String): Option[Trie] =
    if (word.isEmpty)
      Some(this)
    else next(word.head) match {
      case None => None
      case Some(nextNode) => nextNode.findSubtrie(word.tail)
    }

  override def next(letter: Char): Option[Trie] = nextNodes.next(letter)

  override def add(word: String): ModularTrie =
    word.length match {
      case 0 => this.copy(isWordEnd = true)
      case _ =>
        val (nextLetter, left) = (word.head, word.tail)

        next(nextLetter) match {
          case None =>
            val nextTrie = MapTrie().add(left)
            this.copy(nextNodes = nextNodes + (nextLetter -> nextTrie))
          case Some(node) => this.copy(nextNodes = nextNodes + (nextLetter -> node.add(left)))
        }
    }

  override def merge(second: Trie): ModularTrie = {
    val newRoot = if (second.isWordEnd && !this.isWordEnd)
      this.copy(isWordEnd = true)
    else this

    val newEdges = second.edgesLetters.foldLeft(nextNodes)((acc, letter) =>
      if (acc.contains(letter)) {
        val mergedNext = nextNodes.next(letter).get merge second.next(letter).get
        acc + (letter -> mergedNext)
      }
      else acc + (letter -> second.next(letter).get)
    )

    newRoot.copy(nextNodes = newEdges)
  }

  override def edgesLetters: Iterable[Char] = nextNodes.letters
}

object ModularTrie {
  def apply(words: Iterable[String], emptyEdges: EdgesModule): ModularTrie =
    words.foldLeft(ModularTrie(isWordEnd = false, emptyEdges))((acc, i) => acc.add(i))
}

trait EdgesModule {
  type Edge = (Char, Trie)

  def next(letter: Char): Option[Trie]

  def add(edge: Edge): EdgesModule

  final def +(edge: Edge): EdgesModule = add(edge)

  def contains(letter: Char): Boolean

  def letters: Iterable[Char]
}


final case class MapEdges(edges: Map[Char, Trie] = Map.empty) extends EdgesModule {
  override def add(edge: (Char, Trie)) = MapEdges(edges + edge)

  override def contains(letter: Char): Boolean = edges.contains(letter)

  override def letters: Iterable[Char] = edges.keys

  override def next(letter: Char): Option[Trie] = edges.get(letter)
}

final case class ListEdges(edges: List[(Char, Trie)] = List.empty) extends EdgesModule {
  override def add(edge: (Char, Trie)) = ListEdges(edge :: edges)

  override def contains(letter: Char): Boolean = edges.exists(edge => edge._1 == letter)

  override def letters: Iterable[Char] = edges.map(_._1)

  override def next(letter: Char): Option[Trie] = edges.find(_._1 == letter).map(_._2)
}

final case class ListBufferEdges(edges: ListBuffer[(Char, Trie)] = ListBuffer.empty) extends EdgesModule {
  override def add(edge: (Char, Trie)) = ListBufferEdges(edges += edge)

  override def contains(letter: Char): Boolean = edges.exists(edge => edge._1 == letter)

  override def letters: Iterable[Char] = edges.map(_._1)

  override def next(letter: Char): Option[Trie] = edges.find(_._1 == letter).map(_._2)
}

final case class ArrayEdges(edges: Array[Option[Trie]],
                            hash: Function[Char, Int],
                            space: Int,
                            private var lettersList: List[Char]) extends EdgesModule {
  override def add(edge: (Char, Trie)): ArrayEdges = {
    lettersList = edge._1 :: lettersList
    edges(hash(edge._1)) = Option(edge._2)
    this
  }

  override def letters: Iterable[Char] = lettersList

  override def contains(letter: Char): Boolean = edges(hash(letter)).isDefined

  def this(hash: Function[Char, Int], space: Int) =
    this(Array.fill(space)(None), hash, space, List())

  override def next(letter: Char) = edges(hash(letter))
}
