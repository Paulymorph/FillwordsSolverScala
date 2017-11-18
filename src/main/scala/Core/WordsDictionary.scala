package Core

trait WordsDictionary {
  def containsStringThatStartsWith(prefix: String): Boolean
  def containsFull(fullString: String): Boolean
}

object WordsDictionary {
  def create(collection: Seq[String]): WordsDictionary = new NaiveWordsDictionary(collection)
}
