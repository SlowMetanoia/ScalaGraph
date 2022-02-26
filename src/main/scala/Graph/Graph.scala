package Graph

/**
 * Трейт для моделей хранения.
 */
trait Graph {
  def getNeighbours(i:Int)
  def hasEdge(i:Int,j:Int)

}
