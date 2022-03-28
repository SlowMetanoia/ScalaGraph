
package Graphs.Implementations

import Graphs.Algorithms.GraphTraverser
import Graphs.Matrix.{ BipartiteGraphAdjacencyMatrix, RectangleMatrix }
import Graphs._

/**
 * Рeализация двудольного орграфа
 * @param bipartiteAdjacencyMatrix даёт интерфейс матрицы смежности
 */
case class BipartiteOrientedGraph( bipartiteAdjacencyMatrix:BipartiteGraphAdjacencyMatrix) extends IGraph[Boolean] {
  
  /**
   * Матрица инцидентности графа
   *
   * @return Матрица, где по индексам (i,j) лежит true, если i-тому ребру инцидентна j-ая вершина
   */
  def incidenceMatrix: RectangleMatrix[ Boolean ] = ???
  
  
  
  /**
   * Матрица доступности
   *
   * @return Матрица, где по индексам (i,j) лежит true, если существует путь из i в j
   */
  override
  def availabilityMatrix: RectangleMatrix[ Boolean ] = ???
  
  /**
   * Подграф.
   *
   * @param nodes набор вершин для генерации подграфа
   *
   * @return Подграф из выбранных вершин и всех рёбер между ними.
   */
  override
  def subgraph( nodes: Seq[ Int ] ): IGraph[ Boolean ] = ???
  
  /**
   * @param source   номер 1-ой вершины
   *
   * @param receiver номер 2-ой вершины
   *
   * @return true если существует ребро между source и receiver иначе false
   */
  override
  def hasEdge( source: Int, receiver: Int ): Boolean = bipartiteAdjacencyMatrix(source,receiver)
  
  override def indices: (Seq[ Int ], Seq[ Int ]) = bipartiteAdjacencyMatrix.indices
  
  /**
   * Матрица смежности графа
   *
   * @return Матрица, где по индексам (i,j) лежит true, если существует ребро из i в j
   */
  override
  val adjacencyMatrix: (Int, Int) => Boolean = bipartiteAdjacencyMatrix(_,_)
  
  override def size: (Int, Int) = bipartiteAdjacencyMatrix.size
}
object BipartiteOrientedGraph extends GraphTraverser[BipartiteOrientedGraph]{
  def graphFromSlices(
                     size:Int,
                     n:Int,
                     f:(Int,Int)=>Boolean
                     ):BipartiteOrientedGraph =
    BipartiteOrientedGraph(BipartiteGraphAdjacencyMatrix.fromSlices(size,n,f))
}