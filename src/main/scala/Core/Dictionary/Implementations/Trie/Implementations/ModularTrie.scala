package Core.Dictionary.Implementations.Trie.Implementations

import Core.Dictionary.Implementations.Trie.Implementations.EdgesContainers.MapEdges
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
                             nextNodes: EdgesContainer)
                            (implicit edgesFactory: EdgesFactory) extends Trie {

  override def findSubtrie(word: String): Option[Trie] =
    word.toList match {
      case firstLetter :: remainder =>
        follow(firstLetter).flatMap(_.findSubtrie(remainder.mkString))
      case _ => Some(this)
    }

  override def follow(letter: Char): Option[Trie] = nextNodes.follow(letter)

  override def +(word: String): ModularTrie =
    word.headOption
      .fold(this.copy(isWordEnd = true)) {
        firstLetter =>
          val edgeDestination = follow(firstLetter)
            .getOrElse(ModularTrie(nextNodes = edgesFactory.emptyEdges()))

          this.copy(nextNodes = nextNodes + Edge(firstLetter, edgeDestination + word.tail))
      }

  override def edgesLetters: Iterable[Char] = nextNodes.letters
}

object ModularTrie {
  def apply(words: Iterable[String])(implicit edgesFactory: EdgesFactory): ModularTrie = {
    val edgesContainer = edgesFactory.emptyEdges()
    words.foldLeft(ModularTrie(isWordEnd = false, edgesContainer))((trie, word) => trie + word)
  }

  def parallelConstruct(seq: Seq[String]): Trie = {
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
    ModularTrie(isWordEnd = false, rootEdges)
  }
}
