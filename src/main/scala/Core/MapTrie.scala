package Core

/**
  * A trie with map as a way to keep the next nodes
  *
  * @param isWordEnd a flag to state whether the
  * @param nextNodes a map of nodes that are on the next level of the trie
  */
final case class MapTrie(isWordEnd: Boolean = false, nextNodes: Map[Char, Trie] = Map.empty) extends Trie {

  override def next(letter: Char): Option[Trie] = nextNodes.get(letter)

  override def findSubtrie(word: String): Option[Trie] =
    if (word.isEmpty)
      Some(this)
    else next(word.head) match {
      case None => None
      case Some(nextNode) => nextNode.findSubtrie(word.tail)
    }

  override def add(word: String): MapTrie =
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

  /**
    * Todo: realize this for parallel trie creation
    *
    * @param second the second trie to merge with
    * @return the result of the merge
    */
  override def merge(second: Trie): Trie = {
    val newRoot = if (second.isWordEnd && !this.isWordEnd)
      this.copy(isWordEnd = true)
    else this

    val newEdges = second.edgesLetters.foldLeft(nextNodes) ((acc, next) =>
      if (acc.contains(next)) {
        val mergedNext = nextNodes(next) merge second.next(next).get
          acc + (next -> mergedNext)
        }
      else acc + (next -> second.next(next).get)
    )

    newRoot.copy(nextNodes = newEdges)
  }

  override def edgesLetters: Iterable[Char] = nextNodes.keys
}
