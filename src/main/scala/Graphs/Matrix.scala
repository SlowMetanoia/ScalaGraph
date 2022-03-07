package Graphs

/**
 * Матрица
 * @param matrix - передаваемые значения
 * @tparam T тип
 */
case class Matrix[T](matrix: Seq[Seq[T]]){
  import Matrix.Printable
  /**
   * Размеры матрицы
   */
  val size: (Int,Int) = if(matrix.nonEmpty) (matrix.length,matrix.head.length) else (0,0)
  /**
   * Индексы матрицы
   */
  lazy val indices: (Seq[Int], Seq[Int]) =
    if(matrix.nonEmpty) (matrix.indices,matrix.head.indices) else (Seq.empty[Int],Seq.empty[Int])

  override def toString: String = super.toString
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
  def map[T2](f:T=>T2):Matrix[T2] = Matrix(matrix.map(_.map(a=>f(a))))

  /**
   * map с индексами
   * @param f функция, получающая 2 координаты и значение из матрицы по этим координатам.
   * @tparam T2 тип возвращаемого значения.
   * @return Матрица с новыми значениями
   */
  def mapWithIndices[T2](f:Int=>Int=>T=>T2):Matrix[T2] = Matrix(
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

  /**
   * @param printable что именно мы хотим вывести
   */
  def prints(printable:Printable): Unit = printable match {
    case Matrix.Indices =>
      println(
        s"vertical:   " + indices._1.mkString(" ") + "\n" +
        s"horizontal: " + indices._2.mkString(" ")
      )
    case Matrix.Matrix =>
    case _=>
  }
}

object Matrix{
  /**
   * своего рода enum для открытого интерфейса печати
   */
  sealed trait Printable
  case object Indices extends Printable
  case object Matrix extends Printable
}