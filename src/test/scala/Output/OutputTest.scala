package Output

import Graphs.Matrix.RectangleMatrix
import Printers.matrixConsolePrinter
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class OutputTest extends AnyFlatSpec with should.Matchers{
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
  "*something* printedBy *printer*" should "print *something* anywhere" in {
    testMatrix <<: matrixConsolePrinter
    println
    testBoolMatrix <<: matrixConsolePrinter
  }
}
