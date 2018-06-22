package Core.Dictionary.Implementations.Trie

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
  def follow(letter: Char): Option[Trie]

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
  def +(word: String): Trie

  /**
    * Get letters of the edges of the current root
    * @return a list of edges' letters of the current root
    */
  def edgesLetters: Iterable[Char]
}