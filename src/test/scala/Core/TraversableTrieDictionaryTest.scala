package Core

import Core.TraversableTrieDictionary.upsNeeded
import org.scalatest.FlatSpec

class TraversableTrieDictionaryTest extends FlatSpec {
  behavior of "Traversable trie"
  it should "go up" in {
    assert(upsNeeded("abc", "de") == (3, "de"))
    assert(upsNeeded("abc", "ade") == (2, "de"))
    assert(upsNeeded("abc", "asdfsde") == (2, "asdfsde"))
    assert(upsNeeded("abc", "abc") == (0, ""))
    assert(upsNeeded("abc", "") == (3, ""))
    assert(upsNeeded("abc", "abcer") == (0, "er"))
    assert(upsNeeded("", "sdf") == (0, "sdf"))
  }
}
