package Graphs.Implementations

import Graphs.Algorithms.GraphTraverser
import Graphs.Matrix.RectangleMatrix
import Graphs.Traits.{Graph, WeightedGraph}

import scala.Array.ofDim

case class OrientedWeightGraph(weightMatrix:RectangleMatrix[Int]) extends Graph[Int] with WeightedGraph{
  /**
   * @param i номер вершины
   * @return соседей i-той вершины
   */
  override def getNeighbours(i: Int): Seq[Int] = weightMatrix(i).filterNot(_ == 0)

  /**
   * Степень вершины
   *
   * @param i номер вершины
   * @return количество рёбер инцидентных вершине (начинающихся либо заканчивающихся в вершине)
   */
  override def degrees(i: Int): Int = getNeighbours(i).length

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
override def edges: Seq[(Int, Int)] = for {
  i <- 0 to adjacencyMatrix.size._1
  j <- 0 to adjacencyMatrix.size._2
  if adjacencyMatrix(i, j)
} yield (i, j)

  /**
   * Матрица инцидентности графа
   *
   * @return Матрица, где по индексам (i,j) лежит true, если i-тому ребру инцидентна j-ая вершина
   */
  override def incidenceMatrix: RectangleMatrix[Boolean] = RectangleMatrix(
    edges.map{edge=>
    for(i<-weightMatrix.indices._1)
      yield edge._1==i
  })

  /**
   * Матрица смежности графа
   *
   * @return Матрица, где по индексам (i,j) лежит true, если существует ребро из i в j
   */
  override def adjacencyMatrix: RectangleMatrix[Boolean] =
    RectangleMatrix(
      weightMatrix.matrix.map(i =>
        i.map(j =>
          j != 0
      ))
    )


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
  override def isConnected: Boolean = {
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
  override def connectedParts: Seq[Seq[Int]] = ???

  /**
   * Путь минимальной длинны
   *
   * @param source   вершина-источник
   * @param receiver вершина-приёмник
   * @return Путь минимальной длинны из источника в приёмник, если такой путь существует.
   */
  override def minimumLengthPath(source: Int, receiver: Int): Option[Seq[Int]] = ???

  /**
   * Подграф.
   *
   * @param nodes набор вершин для генерации подграфа
   * @return Подграф из выбранных вершин и всех рёбер между ними.
   */
  override def subgraph(nodes: Seq[Int]): Graph[Int] = ???

  /**
   * Граф с новыми рёбрами
   *
   * @param f Правило построения ребра
   * @return Граф с рёбрами, соответствующими правилу построения
   */
  override def newEdgesGraph(f: Int => Int => Boolean): Graph[Int] = ???

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
}

object OrientedWeightGraph extends GraphTraverser[Int]