package Core

trait Trie {
  /**
    * Whether the route from the root to the node means a complete word
    * @return true if the route means a complete word, false otherwise
    */
  def isWordEnd: Boolean

  /**
    * Gets the root of the subtrie by the letter
    * @param letter the letter to the subtrie
    * @return if exists Some of the subtrie by the letter, None otherwise
    */
  def next(letter: Char): Option[Trie]

  /**
    * Finds the subtrie by the route of the word's letters
    * @param word the word that specifies the root to the subtrie
    * @return Some of the subtrie if exists, None otherwise
    */
  def findSubtrie(word: String): Option[Trie]

  /**
    * Creates a trie with the word added
    * @param word the word to add to the trie
    * @return a trie with the word added
    */
  def add(word: String): Trie

  /**
    * Merges two tries
    * @param second the second trie to merge with
    * @return the result of the merge
    */
  def merge(second: Trie): Trie
}

object Trie {
  /**
    * Creates an empty trie
    * @return an empty trie
    */
  def apply(): Trie = MapTrie()

  /**
    * Creates a trie with the words
    * @param words the words the trie should include
    * @return a trie with the words
    */
  def apply(words: Seq[String]): Trie =
    words.foldLeft(Trie())((acc, i) => acc.add(i))
}

