package Core

sealed trait Trie {

  def isWordEnd: Boolean

  def next(letter: Char): Option[Trie]

  def add(word: String): Trie

  def merge(second: Trie): Trie
}


case class MapTrie(isWordEnd: Boolean = false, nextNodes: Map[Char, Trie] = Map.empty) extends Trie {
  override def next(letter: Char): Option[Trie] = nextNodes.get(letter)

  override def add(word: String): Trie = word.length match {
    case 0 => this.copy(isWordEnd = true)
    case _ =>
      val nextLetter = word.charAt(0)
      val left = word.substring(1)
      next(nextLetter) match {
        case None =>
          val nextTrie = MapTrie().add(left)
          this.copy(nextNodes = nextNodes + (nextLetter -> nextTrie))
        case Some(node) => this.copy(nextNodes = nextNodes + (nextLetter -> node.add(left)))
      }
  }

  override def merge(second: Trie): Trie = ???
}

object Trie {
  def apply() = MapTrie()
}

