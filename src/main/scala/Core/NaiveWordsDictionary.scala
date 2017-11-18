package Core

class NaiveWordsDictionary(collection: Seq[String]) extends WordsDictionary {
  def containsStringThatStartsWith(prefix: String): Boolean =
    collection.exists(_.startsWith(prefix))

  def containsFull(fullString: String): Boolean =
    collection.contains(fullString)
}
