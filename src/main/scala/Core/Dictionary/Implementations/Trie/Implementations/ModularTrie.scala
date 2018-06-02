package Core.Dictionary.Implementations.Trie.Implementations

import Core.Dictionary.Implementations.Trie._

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

/**
  * A trie with map as a way to keep the next nodes
  *
  * @param isWordEnd a flag to state whether the
  * @param nextNodes a map of nodes that are on the next level of the trie
  */
final case class ModularTrie(isWordEnd: Boolean = false,
                             nextNodes: EdgesModule)
                            (implicit ef: EdgesFactory) extends Trie {

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
            val nextTrie = ModularTrie(nextNodes = ef()).add(left)
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
  def apply(words: Iterable[String])(implicit ef: EdgesFactory): ModularTrie =
    words.foldLeft(ModularTrie(isWordEnd = false, ef())(ef))((acc, i) => acc.add(i))

  def parallelConctruct(seq: Seq[String]): Trie = {
    import scala.concurrent.ExecutionContext.Implicits.global
    implicit val edgesFactory = MapEdgesFactory
    val charToTrieFut = Future.sequence(
      seq
        .filter(_.nonEmpty)
        .groupBy(_.head)
        .map {
          case (firstLetter, words) =>
            Future(firstLetter -> ModularTrie(words.map(_.tail)))
        })

    val charToTrie = Await.result(charToTrieFut, Duration.Inf)
    val rootEdges = MapEdges(charToTrie.toMap)
    ModularTrie(false, rootEdges)
  }
}
