package Core.Dictionary.Implementations.Trie.Implementations

import Core.Dictionary.Implementations.Trie.Implementations.EdgesContainers.{ArrayEdges, BufferListEdges, ListEdges, MapEdges}

trait EdgesFactory {
  def emptyEdges(): EdgesContainer
}

object MapEdgesFactory extends EdgesFactory {
  override def emptyEdges() = MapEdges()
}

object ListEdgesFactory extends EdgesFactory {
  override def emptyEdges() = ListEdges()
}

object BufferListEdgesFactory extends EdgesFactory {
  override def emptyEdges() = BufferListEdges()
}

final case class ArrayEdgesFactory(hash: Char => Int, space: Int) extends EdgesFactory {
  override def emptyEdges() = new ArrayEdges(hash, space)
}
