package Graphs.Traits

trait GraphMatter[T] {

  final protected class GraphContext(val graph: Graph[T]){
     val visited = new Array[Boolean](graph.adjacencyMatrix.size._1)
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
  final def lazyPartTraverse(
                  graph: Graph[T],
                  startFrom: Int,
                  lazyTraverseOrder:(LazyList[Seq[Int]],GraphContext)=>LazyList[Seq[Int]]
                  ):LazyList[Seq[Int]] =
    lazyTraverseOrder(LazyList(Seq(startFrom)),GraphContext(graph))

  final def lazyBFS(
             order:LazyList[Seq[Int]],
             graphContext: GraphContext
             ):LazyList[Seq[Int]] = ???
}
