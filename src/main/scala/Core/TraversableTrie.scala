package Core

class TraversableTrie(private var innerTrie: Trie) extends Trie {
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

  override def findSubtrie(word: String): Option[Trie] = ???

  override def add(word: String): TraversableTrie = ???

  override def merge(second: Trie): TraversableTrie = {
    innerTrie = innerTrie merge second
    this
  }

  override def edgesLetters: Iterable[Char] = currentPosition.edgesLetters

  def goUp(): TraversableTrie = {
    currentPosition = route.head
    route = route.tail
    this
  }
}
