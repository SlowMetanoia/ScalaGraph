package Graph.Traits

import Graph.Matrix

/**
 *
 * @tparam StoredType тип хранимого значения
 */
trait Graph[StoredType] {
  /**
   * @param i номер вершины
   * @return соседей i-той вершины
   */
  def getNeighbours(i: Int): Seq[StoredType]

  /**
   * @param source номер 1-ой вершины
   * @param receiver номер 2-ой вершины
   * @return true если существует ребро между i и j иначе false
   */
  def hasEdge(source: Int, receiver: Int): StoredType

  /**
   * @return Матрица инцидентности графа
   */
  def incidenceMatrix: Matrix[StoredType]

  /**
   * @return Матрица смежности графа
   */
  def adjacencyMatrix: Matrix[StoredType]

  /**
   * @param source   номер вершины-источника
   * @param receiver номер вершины-приёмника
   * @return true, если приёмник доступен из источника
   */
  def isAvailable(source: Int, receiver: Int): Boolean

  /**
   * @return Матрица доступности
   */
  def availabilityMatrix: Matrix[Boolean]

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
  def isConnected: Boolean

  /**
   * Разбиение графа на связные (сильно-связные) части
   *
   * @return последовательность свзяных частей графа
   */
  def connectedParts: Seq[Graph[StoredType]]

  /**
   * Путь минимальной длинны
   * @param source вершина-источник
   * @param receiver вершина-приёмник
   * @return Путь минимальной длинны из источника в приёмник, если такой путь существует.
   */
  def minimumLengthPath(source:Int,receiver:Int):Option[Seq[Int]]
}
