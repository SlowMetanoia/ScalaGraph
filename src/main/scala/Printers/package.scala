import Graphs.Structures.Matrix.RectangleMatrix

package object Printers {
  object matrixConsolePrinter{
    def <<:[T](matrix:RectangleMatrix[T]):Unit = printMatrix(matrix.printConversion)

    private def printMatrix(matrix: RectangleMatrix[String]): Unit ={
      val width = matrix.matrix.map(_.map(_.length).max).max
      println(matrix.map{
        str => str + {
          " " * (width - str.length)
        }
      }
        .matrix.map(_.mkString(" ")).mkString("\n"))
    }
  }
  class valueTypeMismatchException(msg:String) extends Exception(msg)
}
