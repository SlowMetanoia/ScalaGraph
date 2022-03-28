package Matrix

import Graphs.Matrix.{ BipartiteGraphAdjacencyMatrix, RectangleMatrix }
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class BipartiteIGraphAdjacencyMatrixTest extends AnyFlatSpec with should.Matchers {
  val boolean2Int:Boolean=> Int = {
    case true=> 1
    case _ => 0
  }
  val int2Boolean:Int=>Boolean = _!=0
  val testMtxst: RectangleMatrix[ Boolean ] = RectangleMatrix(
    Seq(
      Seq(0, 0, 0, 0, 1, 1, 0, 0),
      Seq(0, 0, 0, 0, 1, 0, 1, 0),
      Seq(0, 0, 0, 1, 0, 1, 0, 1),
      Seq(1, 1, 1, 0, 0, 0, 0, 0),
      Seq(1, 1, 1, 0, 0, 0, 0, 0),
      Seq(1, 1, 1, 0, 0, 0, 0, 0),
      Seq(1, 1, 1, 0, 0, 0, 0, 0),
      Seq(1, 1, 1, 0, 0, 0, 0, 0)
      )).map(_ == 1)
  "BipartiteGraphAdjacencyMatrix methods " should "work well" in {
    val testMtx = BipartiteGraphAdjacencyMatrix.
                  fromSlices(
                    size = 8,
                    delimiter = 3,
                    testMtxst(_, _)
                    )
    testMtx.matrix(3) should be(Seq(1, 1, 1, 0, 0, 0, 0, 0).map(int2Boolean))
    RectangleMatrix(testMtx.matrix) should be(testMtxst)
  }
}
