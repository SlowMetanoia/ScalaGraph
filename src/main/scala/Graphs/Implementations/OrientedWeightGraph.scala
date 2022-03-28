package Graphs.Implementations

import Graphs.Algorithms.GraphTraverser
import Graphs.Structures.Matrix.RectangleMatrix
import Graphs.{ IGraph, IWeightedIGraph }

import scala.Array.ofDim

case class OrientedWeightGraph( weightMatrix:RectangleMatrix[Int]) extends IGraph[Int] with IWeightedIGraph{
  
  /**
   * @param source   номер 1-ой вершины
   * @param receiver номер 2-ой вершины
   * @return true если существует ребро между source и receiver иначе false
   */
  override def hasEdge(source: Int, receiver: Int): Boolean = weightMatrix(source,receiver) != 0

  /**
   * Рёбра/дуги графа
   *
   * @return последовательность всех рёбер графа
   */


  /**
   * Матрица инцидентности графа
   *
   * @return Матрица, где по индексам (i,j) лежит true, если i-тому ребру инцидентна j-ая вершина
   */
  def incidenceMatrix: RectangleMatrix[Boolean] = RectangleMatrix(
    edges.map{edge=>
    for(i<-weightMatrix.indices._1)
      yield edge._1==i
  })

  /**
   * Матрица смежности графа
   *
   * @return Матрица, где по индексам (i,j) лежит true, если существует ребро из i в j
   */
  override val adjacencyMatrix: (Int, Int) => Boolean = ( i:Int, j:Int)=>
      weightMatrix(i,j) > 0


  override def isAvailable(source: Int, receiver: Int): Boolean = ???

  /**
   * Матрица доступности
   *
   * @return Матрица, где по индексам (i,j) лежит true, если существует путь из i в j
   */
  override def availabilityMatrix: RectangleMatrix[Boolean] = {
    val result = ofDim[Boolean](weightMatrix.size._1, weightMatrix.size._2)
    for {
      k <- weightMatrix.indices._1
      i <- weightMatrix.indices._1
      j <- weightMatrix.indices._2
    } result(i)(j) = weightMatrix(i,j) != 0 || (weightMatrix(i, k) != 0 && weightMatrix(k, j)!= 0)
    RectangleMatrix(result.map(_.toSeq).toSeq)
  }

  /**
   * Связным (сильно-связным для ориентированных графов) является граф, для которого верно,
   * что для любой пары вершин существует путь из одной в другую и обратно.
   * Если ориентированный граф связен, но также сильно-связен
   *
   * Слабо-связный ориентированный граф - это ориентированный граф, при замене дуг которого на рёбра, получается
   * связный граф
   *
   * @return true, если граф связен(сильно связен), иначе false
   */
  @deprecated("НЕ СИЛЬНО, А ПРОСТО СВЯЗЕН!")
  def isConnected: Boolean = {
    val avlm = availabilityMatrix
    for{i<-weightMatrix.indices._1
        j<-weightMatrix.indices._2
        if i != j } if(!avlm(i,j)) return false
    true
  }

  /**
   * Разбиение графа на связные (сильно-связные) части
   *
   * @return последовательность связных частей графа
   */
  def connectedParts: Seq[Seq[Int]] = ???

  /**
   * Путь минимальной длинны
   *
   * @param source   вершина-источник
   * @param receiver вершина-приёмник
   * @return Путь минимальной длинны из источника в приёмник, если такой путь существует.
   */
  def minimumLengthPath(source: Int, receiver: Int): Option[Seq[Int]] = ???

  /**
   * Подграф.
   *
   * @param nodes набор вершин для генерации подграфа
   * @return Подграф из выбранных вершин и всех рёбер между ними.
   */
  override def subgraph(nodes: Seq[Int]): IGraph[Int] = ???
  
  /**
   * Вес ребра
   *
   * @param source   вершина-источник
   * @param receiver вершина-приёмник
   * @return Вес ребра между вершиной источником и вершиной-приёмником, если такое ребро есть. Иначе - неопределенно.
   */
  override def weight(source: Int, receiver: Int): Option[Int] = ???

  /**
   * Вес пути
   *
   * @param path последовательность вершин пути
   * @return Вес пути, если такой путь существует. Иначе - неопределенно.
   */
  override def pathWeight(path: Seq[Int]): Option[Int] = ???

  /**
   * Минимальный по весу путь
   *
   * @param source   вершина - источник
   * @param receiver вершина - приёмник
   * @return Минимальный по весу путь между двумя вершинами, если такой путь возможен. Иначе - неопределенно.
   */
  override def minimumWeightPath(source: Int, receiver: Int): Option[Seq[Int]] = ???
  
  override def indices: (Seq[ Int ], Seq[ Int ]) = weightMatrix.indices
  
  override def size: (Int, Int) = weightMatrix.size
}

object OrientedWeightGraph extends GraphTraverser[Int]