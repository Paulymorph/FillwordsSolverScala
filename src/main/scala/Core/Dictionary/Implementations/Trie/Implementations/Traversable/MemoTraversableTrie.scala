package Core.Dictionary.Implementations.Trie.Implementations.Traversable

class MemoTraversableTrie(root: TraversableTrie) extends TraversableTrie(root) {

  private var lastSearch: String = _
  private var lastSubtrie = root

  override def findSubtrie(prefix: String): Option[TraversableTrie] = {
    val (nUps, route) = MemoTraversableTrie.upsNeeded(lastSearch, prefix)
    if (nUps == lastSearch.length)
      reset()
    else for {
      i <- 1 to nUps
      up = lastSubtrie.goUp()
    } lastSubtrie = up

    lastSubtrie.findSubtrie(route) match {
      case None => None
      case res@Some(s) =>
        lastSubtrie = s
        res
    }
  }

  private def reset(): Unit = {
    lastSearch = null
    lastSubtrie = root
  }

  def containsFull(fullString: String): Boolean =
    root.findSubtrie(fullString) match {
      case None => false
      case Some(end) => end.isWordEnd
    }
}

object MemoTraversableTrie {
  def upsNeeded(from: String, to: String): (Int, String) = {


    val letterPairs = from.zip(to)
    val prefixLength = letterPairs
      .takeWhile { case (a, b) => a == b }
      .size

    val fromUpsNeeded = from.length - prefixLength
    val route = to.substring(prefixLength)
    (fromUpsNeeded, route)
  }
}
