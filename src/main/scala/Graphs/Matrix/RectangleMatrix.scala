package Graphs.Matrix

import Graphs.Traits.Matrix
import Printers.Printable

/**
 * Матрица
 *
 * @param matrix - передаваемые значения
 * @tparam T тип
 */
case class RectangleMatrix[T](matrix: Seq[Seq[T]]) extends Printable[RectangleMatrix[String]] with Matrix[T]{
  //технически эта ерунда работает за n, что печально, если захочется создавать много больших матриц, но только это даёт безопасно использовать indices
    //matrix.headOption.foreach {
    //  head => if (matrix.map(_.length == head.length).reduce(_ & _))
    //    throw new Matrix.NotEqualLineLengthException
    //}

  /**
   * Размеры матрицы
   */
  val size: (Int,Int) = if(matrix.nonEmpty) (matrix.length,matrix.head.length) else (0,0)
  /**
   * Индексы матрицы
   */
  lazy val indices: (Seq[Int], Seq[Int]) =
    if(matrix.nonEmpty) (matrix.indices,matrix.head.indices) else (Seq.empty[Int],Seq.empty[Int])

  def printConversion: RectangleMatrix[String] = RectangleMatrix {
    matrix.view.map(_.map(_.toString))
      .zipWithIndex.map(line => line._1.prepended(line._2.toString))
      .toSeq.prepended(
      indices._2.map(_.toString).prepended(""))
  }

  /**
   * @param i номер строки
   * @return строку матрицы
   */
  def row(i: Int): Seq[T] = matrix(i)

  /**
   * @param j номер столбца
   * @return столбец матрицы
   */
  def col(j: Int): Seq[T] = matrix.map(_(j))

  /**
   * matrix( i )( j ) = j элемент в i строке
   */
  def apply(i: Int): Seq[T] = matrix(i)

  /**
   * matrix( i , j ) = j элемент в i строке
   */
  def apply(i: Int,j:Int): T = matrix(i)(j)

  /**
   * map обыкновенный
   * @param f функция из T в T2
   * @tparam T2 тип возвращаемого значения.
   * @return Матрица с новыми значениями
   */
  def map[T2](f:T=>T2):RectangleMatrix[T2] = RectangleMatrix(matrix.map(_.map(a=>f(a))))

  /**
   * map с индексами
   * @param f функция, получающая 2 координаты и значение из матрицы по этим координатам.
   * @tparam T2 тип возвращаемого значения.
   * @return Матрица с новыми значениями
   */
  def mapWithIndices[T2](f:Int=>Int=>T=>T2):RectangleMatrix[T2] = RectangleMatrix(
    for(i<-indices._1) yield
      for(j<-indices._2) yield f(i)(j)(matrix(i)(j))
  )

  /**
   * foreach обыкновенный
   * @param f функция потребляющая T
   */
  def foreach(f:T=>Unit):Unit = matrix.foreach(_.foreach(a=>f(a)))

  /**
   * foreach с индексами
   *
   * аналогично map с индексами
   * @param f функция, потребляющая индексы и T
   */
  def foreachWithIndices(f:Int=>Int=>T=>Unit):Unit = for{
    i<-indices._1
    j<-indices._2
  } f(i)(j)(matrix(i)(j))
  def flatten: Seq[T] = matrix.flatten
}

object RectangleMatrix{
  class NotEqualLineLengthException extends Exception

  def empty[T]:RectangleMatrix[T] = new RectangleMatrix(
    Seq.empty[Seq[T]]
  )
}