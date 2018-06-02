package Core.Dictionary.Implementations.Trie.Implementations.Traversable

import Core.Dictionary.Implementations.Trie.Trie

class TraversableTrie(private val innerTrie: Trie) extends Trie {
  private var route: List[Trie] = List()
  private var currentPosition: Trie = innerTrie

  override def isWordEnd: Boolean = currentPosition.isWordEnd

  override def next(letter: Char): Option[TraversableTrie] = {

    currentPosition.next(letter) match {
      case None => None
      case Some(t) =>
        route = currentPosition :: route
        currentPosition = t
        Some(this)
    }
  }

  override def findSubtrie(word: String): Option[TraversableTrie] =
    currentPosition.findSubtrie(word).map(new TraversableTrie(_))

  override def add(word: String): TraversableTrie = new TraversableTrie(currentPosition.add(word))

  override def merge(second: Trie): TraversableTrie = {
    val merged = innerTrie merge second
    new TraversableTrie(merged)
  }

  override def edgesLetters: Iterable[Char] = currentPosition.edgesLetters

  def goUp(): TraversableTrie = {
    currentPosition = route.head
    route = route.tail
    this
  }
}
