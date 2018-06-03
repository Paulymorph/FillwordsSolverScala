package Core.Dictionary.Implementations.Trie.Implementations

import Core.Dictionary.Implementations.Trie.Implementations.EdgesContainers.{ArrayEdges, BufferListEdges, ListEdges, MapEdges}
import Core.Dictionary.Implementations.Trie._

trait EdgesFactory {
  def apply(): EdgesContainer
}

object MapEdgesFactory extends EdgesFactory {
  override def apply() = MapEdges()
}

object ListEdgesFactory extends EdgesFactory {
  override def apply() = ListEdges()
}

object BufferListEdgesFactory extends EdgesFactory {
  override def apply() = BufferListEdges()
}

final case class ArrayEdgesFactory(hash: Char => Int, space: Int) extends EdgesFactory {
  override def apply() = new ArrayEdges(hash, space)
}
