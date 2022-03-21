package Graphs.Traits

import scala.annotation.tailrec

trait GraphMatter[T] {

  /**
   * Мутабельный объект, отслеживающий посещённые вершины
   * @param graph граф
   */
  final protected class GraphContext(val graph: Graph[T]){
     val isVisited = new Array[Boolean](graph.adjacencyMatrix.size._1)
     def visit(node:Int):Boolean = if(isVisited(node))
       true
     else {
       isVisited(node) = true
       false
     }
  }
  final protected object GraphContext{
    def apply(graph: Graph[T]):GraphContext = new GraphContext(graph)
  }

  /**
   * Ленивый обход связной компоненты графа
   * @param graph обходимый граф
   * @param startFrom начальная вершина
   * @param lazyTraverseOrder функция обхода
   * @return
   */
  final def partTraverse(
                  graph: Graph[T],
                  lazyTraverseOrder:(LazyList[Seq[Int]],GraphContext)=>LazyList[Seq[Int]] = bfs,
                  startFrom: Int = 0
                  ):LazyList[Seq[Int]] =
    lazyTraverseOrder(LazyList(Seq(startFrom)),GraphContext(graph))

  /** bfs ленивый
   * @param order последовательность обхода вершин
   * @param graphContext вычислительный контекст для графа
   * @return ленивую коллекцию, содержащую последовательности вершин в порядке распространения bfs.
   */
  @tailrec
  final def bfs(
             order:LazyList[Seq[Int]],
             graphContext: GraphContext
             ):LazyList[Seq[Int]] = {
    val newNodes = order.last.flatMap{ node =>
      graphContext.visit(node)
      graphContext.graph.getNeighbours(node)
        .filterNot(graphContext.visit)
    }
    if(newNodes.isEmpty)
      order
    else
      bfs(
        order.appended(newNodes),
        graphContext
      )
  }
}
