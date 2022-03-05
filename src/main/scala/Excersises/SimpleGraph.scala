package Excersises

import Graphs.Matrix
import Graphs.Traits.Graph

import scala.Array.ofDim
import scala.annotation.tailrec

case class SimpleGraph(adjacencyMatrix:Matrix[Boolean]) extends Graph[Boolean]{


  def this(nodeNumber:Int,edges:Seq[(Int,Int)]){
    this(
      Matrix(
        {
          val arr = ofDim[Boolean](nodeNumber,nodeNumber)
          //по-умолчанию инициализируется false
          //for(i<-arr.indices;j<-arr(i).indices) arr(i)(j) = false
          edges.foreach(e => {arr(e._1)(e._2) = true; arr(e._2)(e._1) = true})
          arr.map(_.toSeq).toSeq
        }
      )
    )
  }

  /**
   * @param i номер вершины
   * @return соседей i-той вершины
   */
  override def getNeighbours(i: Int): Seq[Int] = adjacencyMatrix.row(i).zipWithIndex.filter(_._1).map(_._2)

  /**
   * @param source   номер 1-ой вершины
   * @param receiver номер 2-ой вершины
   * @return true если существует ребро между source и receiver иначе false
   */
  override def hasEdge(source: Int, receiver: Int): Boolean = adjacencyMatrix(source,receiver)

  /**
   * Рёбра/дуги графа
   * @return последовательность всех рёбер графа
   */
  override def edges: Seq[(Int, Int)] = for{
    i<-0 to adjacencyMatrix.size._1
    j<-0 to adjacencyMatrix.size._2
    if adjacencyMatrix(i,j)
  } yield (i,j)

  /**
   * Матрица инцидентности графа
   * @return Матрица инцидентности графа
   */
  override def incidenceMatrix: Matrix[Boolean] = {
    //это паршиво написанный код, путь и достаточно функционально.
    //ужас в for!
    //инициализация матрицы явно?
    Matrix(
      edges.map{e=>
        for(i<-adjacencyMatrix.row(0).indices) yield
          (e._1 == i) || (e._2 == i)
      }
    )
  }

  /**
   * @param source   номер вершины-источника
   * @param receiver номер вершины-приёмника
   * @return true, если приёмник доступен из источника
   */
  override def isAvailable(source: Int, receiver: Int): Boolean = ???

  /**
   * @return Матрица доступности
   */
  override def availabilityMatrix: Matrix[Boolean] = {
    //функционально, но очень неэффективно!
    val connectedParts = this.connectedParts
    Matrix(
    for (i<-adjacencyMatrix.row(0).indices) yield
      for (j<-adjacencyMatrix.col(0).indices) yield
        adjacencyMatrix(i,j) || {
          connectedParts.groupBy(a=>
            a.contains(i) || a.contains(j)
          )(true).length == 1
        }
    )
  }

  /**
   * Связным (сильно-связным для ориентированных графов) является граф, для которого верно,
   * что для любой пары вершин существует путь из одной в другую и обратно.
   * Если ориентированный граф связен, но также сильно-связен
   *
   * Слабо-связный оринетированный граф - это ориентированный граф, при замене дуг которого на рёбра, получается
   * связный граф
   *
   * @return true, если граф связен(сильно связен), иначе false
   */
  override def isConnected: Boolean = ???

  /**
   * Разбиение графа на связные (сильно-связные) части
   *
   * @return последовательность связных частей графа
   */
  override def connectedParts: Seq[Seq[Int]] = {
    //Это императивное решение!
    //плохо оптимизировано
    //негибкое
    //не учитывает специфику конкретных условий
    var result = Seq.empty[Seq[Int]]
    var nodeSet = adjacencyMatrix.row(0).indices.toSet

    @tailrec
    def reducePart(knownConnected:Set[Int]):Set[Int] = {
      val newConnected = knownConnected.flatMap(a => getNeighbours(a)) & knownConnected
      if (newConnected.isEmpty) {
        nodeSet -= knownConnected
        knownConnected
      } else
        reducePart(newConnected)
    }

    while(nodeSet.nonEmpty){
      result = result.appended(
        reducePart(Set(nodeSet.head))
      )
    }
  result
  }

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
  override def subgraph(nodes: Seq[Int]): Graph[Boolean] = ???
}
