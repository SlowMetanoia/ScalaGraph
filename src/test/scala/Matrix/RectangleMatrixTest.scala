package Matrix

import Graphs.Matrix.RectangleMatrix
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class RectangleMatrixTest extends AnyFlatSpec with should.Matchers {
  val testMatrix:RectangleMatrix[Int] = RectangleMatrix(
    Seq(
      Seq(1,2,3,4),
      Seq(5,6,7,8),
      Seq(9,10,11,12)
    )
  )
  val testBoolMatrix: RectangleMatrix[Boolean] = RectangleMatrix(
    Seq(
      Seq(false,true,true,false),
      Seq(true,false,true,true),
      Seq(true,true,false,true),
      Seq(false,true,true,false)
    )
  )
  "row(i)" should "return row with i index" in {
    testMatrix.row(0) should be(Seq(1,2,3,4))
    testMatrix.row(2) should be(Seq(9,10,11,12))
  }
  "col(j)" should "return col with j index" in {
    testMatrix.col(0) should be(Seq(1,5,9))
    testMatrix.col(3) should be(Seq(4,8,12))
  }
  "apply(i)(j)" should "return i,j matrix element" in {
    testMatrix.apply(0)(1) should be(2)
  }
  "apply(i,j)" should "return i,j matrix element" in {
    testMatrix.apply(0,1) should be(2)
  }


  "prints Matrix" should "print it, lol" in{

  }

}