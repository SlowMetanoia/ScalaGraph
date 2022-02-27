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
   * @return если
   */
  def hasEdge(i:Int,j:Int):StoredType

  /**
   * @return матрицу инцидентности графа
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
}
