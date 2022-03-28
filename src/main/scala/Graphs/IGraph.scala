package Graphs

import Graphs.Structures.Matrix.RectangleMatrix

import scala.Array.ofDim

/**
 *
 * @tparam StoredType тип хранимого значения
 */
trait IGraph[ StoredType ] {
  
  def indices: (Seq[ Int ], Seq[ Int ])
  def size:(Int,Int)
  
  /**
   * @param i номер вершины
   *
   * @return соседей i-той вершины
   */
  def getNeighbours( i: Int ): Seq[ Int ] = indices._2.filter(adjacencyMatrix(i, _))
  
  /**
   * Степень вершины
   *
   * @param i номер вершины
   *
   * @return количество рёбер инцидентных вершине (начинающихся либо заканчивающихся в вершине)
   */
  def degrees( i: Int ): Int = getNeighbours(i).length
  
  /**
   * @param source   номер 1-ой вершины
   *
   * @param receiver номер 2-ой вершины
   *
   * @return true если существует ребро между source и receiver иначе false
   */
  def hasEdge( source: Int, receiver: Int ): Boolean
  
  /**
   * Рёбра/дуги графа
   *
   * @return последовательность всех рёбер графа
   */
  def edges: Seq[ (Int, Int) ] = {
    for {
      i <- indices._1
      j <- indices._2
      if adjacencyMatrix(i, j)
    } yield (i, j)
  }
  
  /**
   * Матрица смежности графа
   *
   * @return Матрица, где по индексам (i,j) лежит true, если существует ребро из i в j
   */
  val adjacencyMatrix: (Int, Int) => Boolean
  
  /**
   * Двунаправленный поиск из source в receiver
   *
   * @param source   номер вершины-источника
   *
   * @param receiver номер вершины-приёмника
   *
   * @return true, если приёмник доступен из источника
   */
  def isAvailable( source: Int, receiver: Int ): Boolean = {
    ???
  }
  
  /**
   * Матрица доступности
   *
   * @return Матрица, где по индексам (i,j) лежит true, если существует путь из i в j
   */
  def availabilityMatrix: RectangleMatrix[ Boolean ] = {
    val result = ofDim[Boolean](size._1, size._2)
    for {
      k <- indices._1
      i <- indices._1
      j <- indices._2
    } result(i)(j) = adjacencyMatrix(i,j) || (adjacencyMatrix(i, k) && adjacencyMatrix(k, j))
    RectangleMatrix(result.map(_.toSeq).toSeq)
  }
  
  /**
   * Подграф.
   *
   * @param nodes набор вершин для генерации подграфа
   *
   * @return Подграф из выбранных вершин и всех рёбер между ними.
   */
  def subgraph( nodes: Seq[ Int ] ): IGraph[ StoredType ]
  
}
