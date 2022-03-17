package Excersises

import Graphs.Matrix.RectangleMatrix
import Graphs.Traits.Graph

import scala.Array.ofDim
import scala.collection.mutable


case class SimpleGraph(adjacencyMatrix:RectangleMatrix[Boolean]) extends Graph[Boolean]{


  def this(nodeNumber:Int,edges:Seq[(Int,Int)]){
    this(
      RectangleMatrix(
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
  override def incidenceMatrix: RectangleMatrix[Boolean] = {
    //это паршиво написанный код, путь и достаточно функционально.
    //ужас в for!
    //инициализация матрицы явно?
    RectangleMatrix(
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
  override def availabilityMatrix: RectangleMatrix[Boolean] = {
    //функционально, но очень неэффективно!
    val connectedParts = this.connectedParts
    RectangleMatrix(
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
  override def connectedParts: Seq[Seq[Int]] = ???/*{
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
        nodeSet --= knownConnected
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
  }*/

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

  /**
   * out:
   * Set
   * Seq
   * Unit
   * Graph
   * Tree
   * iterator
   *
   * n
   * O(nlogn)
   *
   * @return
   */
  def bfs0(node:Int):Set[Int] = {

    var nodesNotDone:Set[Int] = adjacencyMatrix.indices._1.toSet
    var shell:Set[Int] = Set(node)
    var result:Set[Int] = Set.empty

    while(shell.nonEmpty){
      nodesNotDone --= shell
      shell = shell.flatMap(node => getNeighbours(node).toSet & nodesNotDone)
      result ++= shell
    }
    result
  }

  def bfs1(node:Int):Seq[Set[Int]] = {

    var nodesNotDone:Set[Int] = adjacencyMatrix.indices._1.toSet
    var shell:Set[Int] = Set(node)
    var result:Seq[Set[Int]] = Seq.empty

    while(shell.nonEmpty){
      nodesNotDone --= shell
      shell = shell.flatMap(node => getNeighbours(node).toSet & nodesNotDone)
      result = result.appended(shell)
    }
    result
  }

  def dfs(node:Int):List[Int] = {
    var nodesNotDone:Set[Int] = adjacencyMatrix.indices._1.toSet
    var currentNode:Int = node
    val visitingQueue:mutable.Queue[Int] = mutable.Queue(node)
    val result:mutable.Queue[Int] = mutable.Queue.empty

    def step():Unit = {
      var newNodes = getNeighbours(currentNode).toSet & nodesNotDone
      nodesNotDone --= newNodes
      visitingQueue.enqueueAll(newNodes)
      result.enqueueAll(newNodes)
      currentNode = visitingQueue.dequeue()
    }

    do step() while(visitingQueue.nonEmpty)

    result.toList
  }

  /**
   * Степень вершины
   *
   * @param i номер вершины
   * @return количество рёбер инцидентных вершине (начинающихся либо заканчивающихся в вершине)
   */
  override def degrees(i: Int): Int = ???

  /**
   * Граф с новыми рёбрами
   *
   * @param f Правило построения ребра
   * @return Граф с рёбрами, соответствующими правилу построения
   */
  override def newEdgesGraph(f: Int => Int => Boolean): Graph[Boolean] = ???
}

object SimpleGraph extends App{
  println(SimpleGraph {
    RectangleMatrix(
      Seq(
        Seq(false, true, true, false, false, false, true),
        Seq(true, false, true, true, false, false, false),
        Seq(true, true, false, false, false, false, false),
        Seq(false, true, false, false, true, false, false),
        Seq(false, false, false, true, false, true, false),
        Seq(false, false, false, false, true, false, true),
        Seq(true, false, false, false, false, true, false)
      )
    )
  }.dfs(0).mkString(" "))

  /**
   *
   * @param f
   * @param node
   * @param graph
   * @return
   */
  def Traverse(f:((Int,Seq[Int],SimpleGraph,Array[Boolean]))=>(Int,Seq[Int],SimpleGraph,Array[Boolean]))
              (node:Int)
              (graph: SimpleGraph):Seq[Int] = {
    f(node,Seq(node),graph,new Array[Boolean](graph.adjacencyMatrix.size._1))._2
  }

  @tailrec
  def bfs(input:(Int,Seq[Int],SimpleGraph,Array[Boolean])):(Int,Seq[Int],SimpleGraph,Array[Boolean]) = {
    val (index,order,graph,visited) = input
    val newNodes = graph.getNeighbours(order(index)).filterNot(node => visited(node))
    newNodes.foreach(node=> visited(node) = true)
    if(newNodes.isEmpty)
      input
    else
      bfs((
        index + 1,
        order.take(index + 1) ++ newNodes ++ order.drop(index + 1),
        graph,
        visited
      ))
      }

  @tailrec
  def bfs2(index:Int, order:Seq[Int], graph:SimpleGraph, visited:Array[Boolean]): Seq[Int] = {
    val newNodes = graph.getNeighbours(order(index)).filterNot(node => visited(node))
    visited(order(index)) = true
    newNodes.foreach(node=> visited(node) = true)
    if(newNodes.isEmpty && (index == order.length-1) )
      order
    else
      bfs2( index+1 , order.take(index+1) ++ newNodes ++ order.drop(index+1) , graph , visited )
  }

  def Traverse2(graph:SimpleGraph,
                startFrom:Int,
                traverseOrder:(Int,Seq[Int], SimpleGraph, Array[Boolean])=>Seq[Int] = bfs2): Seq[Int] =
    traverseOrder(startFrom,Seq(startFrom),graph,new Array[Boolean](graph.adjacencyMatrix.size._1))
}
