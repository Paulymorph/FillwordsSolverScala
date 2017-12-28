package Core.Dictionary

trait WordsDictionary {
  /**
    * Checks whether the dictionary contains a word that starts with the prefix
    * @param prefix the prefix to search for
    * @return true if there is a word that starts with the prefix, false otherwise
    */
  def containsStringThatStartsWith(prefix: String): Boolean

  /**
    * Checks if the full word is contained in the dictionary
    * @param fullString the word to look for
    * @return true if the word is in the dictionary, false otherwise
    */
  def containsFull(fullString: String): Boolean
}

object WordsDictionary {
  /**
    * Creates a dictionary from the sequence of the words
    * @param collection the words to add to the dictionary
    * @return the formed dictionary
    */
  def apply(collection: Iterable[String]): WordsDictionary =
    TrieWordsDictionary(collection)
}
