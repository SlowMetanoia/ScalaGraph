package Graph

/**
 *
 * @tparam StoredType тип хранимого значения
 */
trait Graph[StoredType] {
  /**
   * @param i номер вершины
   * @return соседей i-той вершины
   */
  def getNeighbours(i:Int):Seq[StoredType]

  /**
   * @param i номер 1-ой вершины
   * @param j номер 2-ой вершины
   * @return true если существует ребро между i и j иначе false
   */
  def hasEdge(i:Int,j:Int):StoredType

  /**
   * @return Матрица инцидентности графа
   */
  def incidenceMatrix:Matrix[StoredType]

  /**
   * @return Матрица смежности графа
   */
  def adjacencyMatrix:Matrix[StoredType]

  /**
   * @param source номер вершины-источника
   * @param receiver номер вершины-приёмника
   * @return true, если приёмник доступен из источника
   */
  def isAvailable(source:Int,receiver:Int):Boolean

  /**
   * @return Матрица доступности
   */
  def availabilityMatrix:Matrix[Boolean]

  /**
   * Связным (сильно-связным для ориентированных графов) является граф, для которого верно,
   * что для любой пары вершин существует путь из одной в другую и обратно.
   * Если ориентированный граф связен, но также сильно-связен
   *
   * Слабо-связный оринетированный граф - это ориентированный граф, при замене дуг которого на рёбра, получается
   * связный граф
   * @return true, если граф связен(сильно связен), иначе false
   */
  def isConnected:Boolean

  /**
   * Разбиение графа на связные (сильно-связные) части
   * @return последовательность свзяных частей графа
   */
  def connectedParts:Seq[Graph[StoredType]]
}
