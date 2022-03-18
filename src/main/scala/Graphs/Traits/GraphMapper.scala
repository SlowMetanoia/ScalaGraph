package Graphs.Traits

import scala.annotation.tailrec

trait GraphMapper[T] {

  /** Абстрактный обход графа
   *
   * @param graph обходимый граф
   * @param startFrom индекс начала обхода
   * @param traverseOrder функция задающая порядок обход
   * @return последовательность обхода вершин графа
   */
  def traverse(
                   graph:Graph[T],
                   startFrom:Int,
                   traverseOrder:(Int,Seq[Int], Graph[T], Array[Boolean])=>Seq[Int] = bfs):Seq[Int] =
    traverseOrder(startFrom,Seq(startFrom),graph,new Array[Boolean](graph.adjacencyMatrix.size._1))

  /**bfs (грязный?)
   *
   * @param index текущий индекс в порядке обхода
   * @param order текущий порядок обхода
   * @param graph обходимый граф
   * @param visited посещена вершина или не
   * @return порядок обхода графа при bfs
   */
  @tailrec
  final def bfs(index:Int, order:Seq[Int], graph:Graph[T], visited:Array[Boolean]): Seq[Int] = {
    val newNodes = graph.getNeighbours(order(index)).filterNot(node => visited(node))
    visited(order(index)) = true
    newNodes.foreach(node=> visited(node) = true)
    if(newNodes.isEmpty && (index == order.length-1) )
      order
    else
      bfs( index+1 , order.take(index+1) ++ newNodes ++ order.drop(index+1) , graph , visited )
  }

}
