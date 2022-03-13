package Graphs.Traits

import Graphs.Matrix

/**
 *
 * @tparam StoredType тип хранимого значения
 */
trait Graph[StoredType] {

  /**
   * @param i номер вершины
   * @return соседей i-той вершины
   */
  def getNeighbours(i: Int): Seq[Int]

  /**
   * Степень вершины
   * @param i номер вершины
   * @return количество рёбер инцидентных вершине (начинающихся либо заканчивающихся в вершине)
   */
  def degrees(i:Int):Int

  /**
   * @param source номер 1-ой вершины
   * @param receiver номер 2-ой вершины
   * @return true если существует ребро между source и receiver иначе false
   */
  def hasEdge(source: Int, receiver: Int): StoredType

  /**
   * Рёбра/дуги графа
   * @return последовательность всех рёбер графа
   */
  def edges:Seq[(Int,Int)]

  /**
   * Матрица инцидентности графа
   * @return Матрица, где по индексам (i,j) лежит true, если i-тому ребру инцидентна j-ая вершина
   */
  def incidenceMatrix: Matrix[StoredType]

  /**
   * Матрица смежности графа
   * @return Матрица, где по индексам (i,j) лежит true, если существует ребро из i в j
   */
  def adjacencyMatrix: Matrix[StoredType]

  /**
   * @param source   номер вершины-источника
   * @param receiver номер вершины-приёмника
   * @return true, если приёмник доступен из источника
   */
  def isAvailable(source: Int, receiver: Int): Boolean

  /**
   * Матрица доступности
   * @return Матрица, где по индексам (i,j) лежит true, если существует путь из i в j
   */
  def availabilityMatrix: Matrix[Boolean]

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
  def isConnected: Boolean

  /**
   * Разбиение графа на связные (сильно-связные) части
   *
   * @return последовательность связных частей графа
   */
  def connectedParts: Seq[Seq[Int]]

  /**
   * Путь минимальной длинны
   * @param source вершина-источник
   * @param receiver вершина-приёмник
   * @return Путь минимальной длинны из источника в приёмник, если такой путь существует.
   */
  def minimumLengthPath(source:Int,receiver:Int):Option[Seq[Int]]

  /**
   * Подграф.
   * @param nodes набор вершин для генерации подграфа
   * @return Подграф из выбранных вершин и всех рёбер между ними.
   */
  def subgraph(nodes:Seq[Int]):Graph[StoredType]

  /**
   * Граф с новыми рёбрами
   * @param f Правило построения ребра
   * @return Граф с рёбрами, соответствующими правилу построения
   */
  def newEdgesGraph(f:Int=>Int=>Boolean):Graph[StoredType]

  //3. Тема:"Пакет алгоритмов вычисления центральности на языке Scala" (КР по ФиЛП)
  //4. Тема:"Пакет алгоритмов выделения сообществ на языке Scala" (КР по ФиЛП)
}
