package Matrix

import Graphs.Matrix.{BipartiteGraphAdjacencyMatrix, RectangleMatrix}
import Printers.matrixConsolePrinter
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class BipartiteGraphAdjacencyMatrixTest extends AnyFlatSpec with should.Matchers{
  val bipartiteGraphAdjacencyMatrixTest0: RectangleMatrix[Boolean] = RectangleMatrix(
    Seq(
      Seq(0, 0, 0, 0, 1, 1, 0, 0),
      Seq(0, 0, 0, 0, 1, 0, 1, 0),
      Seq(0, 0, 0, 1, 0, 1, 0, 1),
      Seq(0, 0, 1, 0, 0, 0, 0, 0),
      Seq(1, 1, 0, 0, 0, 0, 0, 0),
      Seq(1, 0, 1, 0, 0, 0, 0, 0),
      Seq(0, 1, 0, 0, 0, 0, 0, 0),
      Seq(0, 0, 1, 0, 0, 0, 0, 0)
    )).map(_==1)
  "BipartiteGraphAdjacencyMatrix methods " should "work well" in {
    val testMtx = BipartiteGraphAdjacencyMatrix.
      fromIndicesSequences(
        Seq(0,1,2),
        Seq(3,4,5,6,7),
        bipartiteGraphAdjacencyMatrixTest0(_,_)
      )
    testMtx.leftPart.map(el=>if(el) 1 else 0) <<: matrixConsolePrinter
    testMtx.rightPart.map(el=>if(el) 1 else 0) <<: matrixConsolePrinter
  }
}
