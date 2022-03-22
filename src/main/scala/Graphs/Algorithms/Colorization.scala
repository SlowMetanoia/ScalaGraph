package Graphs.Algorithms

import Graphs.Traits.Graph

object Colorization {
  def ChromaticNumber[T](graph:Graph[T]):Int = ???

  /**
   * Является ли граф двудольным
   * @param graph граф
   * @param handler обработчик графа
   * @tparam T Тип графа
   * @return true, если граф двудольный и связный
   */
  final def isBipartite[T](graph: Graph[T])(implicit handler: GraphTraverser[T]):Boolean =
    graph.isConnected &&
      handler.partTraverse(graph,handler.bfs)
    .forall{ _.combinations(2)
        .forall{ pair=>
          !graph.hasEdge(pair.head,pair.last)
        }
  }

  final def toBipartiteGraphParts[T](graph: Graph[T])(implicit handler: GraphTraverser[T]):Iterable[LazyList[Int]] = {
    if (!isBipartite(graph))
      throw new ClassCastException("unable to cast graph into bipartite")
    else
      for {
        (_, v)
          <- handler.partTraverse(graph).zipWithIndex.groupMap(_._2 % 2 == 0)(_._1)
      } yield v.flatten
  }

  def PreciseColorize[T](graph: Graph[T]):Seq[Seq[Int]] = ???
}
