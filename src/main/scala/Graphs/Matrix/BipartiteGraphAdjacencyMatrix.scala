package Graphs.Matrix

import Graphs.Traits.Matrix

case class BipartiteGraphAdjacencyMatrix(leftPart:RectangleMatrix[Boolean],rightPart:RectangleMatrix[Boolean]) extends Matrix[Boolean] {

  /**
   * Размеры матрицы
   */
  override val size: (Int, Int) = (leftPart.size._1 + rightPart.size._1,leftPart.size._2 + rightPart.size._2)

  /**
   * Реальные размеры матрицы
   */
  val actualSize: (Int, Int) = leftPart.size

  /**
   * Реальные индексы матрицы
   */
  val actualIndices: (Seq[Int], Seq[Int]) = leftPart.indices

  /**
   * полноразмерная матрица
   */
  def actualMatrix: RectangleMatrix[Boolean] = RectangleMatrix{
    indices._1.map(i=>
      indices._2.map { j =>
        apply(i,j)
      })
  }

  /**
   * matrix( i )( j ) = j элемент в i строке
   */
  def apply(i: Int): Seq[Boolean] = ???

  /**
   * matrix( i , j ) = j элемент в i строке
   */
  def apply(i: Int,j:Int): Boolean = {
    val sizeDiff = size._1-actualSize._1
    (i,j) match {
      case (i,j) if (i < sizeDiff && j < sizeDiff) || (i > sizeDiff && j > sizeDiff) => false
      case (i,j) if  i < sizeDiff && j > sizeDiff => rightPart(i,j-sizeDiff)
      case (i,j) if  i > sizeDiff && j < sizeDiff => leftPart(i-sizeDiff,j)
    }
  }

  /**
   * Индексы матрицы
   */
  override val indices: (Seq[Int], Seq[Int]) = (0 to size._1,0 to size._2)

  override def printConversion: Matrix[String] = actualMatrix.printConversion

  /**
   * @param i номер строки
   * @return строку матрицы
   */
  override def row(i: Int): Seq[Boolean] = ???

  /**
   * @param j номер столбца
   * @return столбец матрицы
   */
  override def col(j: Int): Seq[Boolean] = ???

  /**
   * map обыкновенный
   *
   * @param f функция из T в T2
   * @tparam T2 тип возвращаемого значения.
   * @return Матрица с новыми значениями
   */
  override def map[T2](f: Boolean => T2): Matrix[T2] = ???

  /**
   * map с индексами
   *
   * @param f функция, получающая 2 координаты и значение из матрицы по этим координатам.
   * @tparam T2 тип возвращаемого значения.
   * @return Матрица с новыми значениями
   */
override def mapWithIndices[T2](f: Int => Int => Boolean => T2): Matrix[T2] = ???

  /**
   * foreach обыкновенный
   *
   * @param f функция потребляющая T
   */
  override def foreach(f: Boolean => Unit): Unit = ???

  /**
   * foreach с индексами
   *
   * аналогично map с индексами
   *
   * @param f функция, потребляющая индексы и T
   */
  override def foreachWithIndices(f: Int => Int => Boolean => Unit): Unit = ???

  override def flatten: Seq[Boolean] = ???

  override lazy val matrix: Seq[Seq[Boolean]] = actualMatrix.matrix
}