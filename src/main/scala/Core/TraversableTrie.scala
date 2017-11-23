package Core

class TraversableTrie(private var innerTrie: Trie) extends Trie {
  private var route: List[Trie] = List()
  private var currentPosition: Trie = innerTrie

  override def isWordEnd = currentPosition.isWordEnd

  override def next(letter: Char) = {

    currentPosition.next(letter) match {
      case None => None
      case Some(t) =>
        route = currentPosition :: route
        currentPosition = t
        Some(this)
    }
  }

  override def findSubtrie(word: String) = ???

  override def add(word: String) = ???

  override def merge(second: Trie) = {
    innerTrie = innerTrie merge second
    this
  }

  override def edgesLetters = currentPosition.edgesLetters

  def goUp() = {
    (currentPosition, route) = (route.head, route.tail)
  }
}
