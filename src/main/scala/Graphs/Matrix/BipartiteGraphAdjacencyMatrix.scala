package Graphs.Matrix

import Graphs.Algorithms.SeqCartesianProduct


case class BipartiteGraphAdjacencyMatrix( private val left:RectangleMatrix[Boolean], private val right:RectangleMatrix[Boolean]) extends Matrix[Boolean] {
  import Graphs.Matrix.BipartiteGraphAdjacencyMatrix._
  
  override def apply( i: Int ): Seq[ Boolean ] = i match {
    case i if leftLimits.rowLim(i)  => left(i - leftLimits.rowLim.l) ++ new Array(right.size._2)
    case i if rightLimits.rowLim(i) => new Array(left.size._2) ++ right(i)
    case _ => throw new IndexOutOfBoundsException
  }
  override def apply( i: Int, j: Int ): Boolean = (i,j) match {
    case (i,j) if leftLimits(i,j)  => left (i - leftLimits.rowLim.l)(j)
    case (i,j) if rightLimits(i,j) => right(i)(j - rightLimits.colLim.l)
    case _ => false
  }
  override def matrix: Seq[ Seq[ Boolean ] ] = indices._1.map(i=>indices._2.map(j=> apply(i,j)))
  /**
   * Размеры матрицы
   */
  override
  val size: (Int, Int) = (left.size._1 + right.size._1,left.size._2 + right.size._2)
  
  val leftLimits:Limits = Limits(
    rowLim = Limit(right.size._1,size._1),
    colLim = Limit(0,left.size._2)
  )
  val rightLimits:Limits = Limits(
    rowLim = Limit(0,right.size._1),
    colLim = Limit(left.size._2,size._2)
    )
  /**
   * Индексы матрицы
   */
  override
  val indices: (Seq[ Int ], Seq[ Int ]) = (0 until size._1,0 until size._2)
  
  /**
   * Преобразование для печати
   *
   * @return матрицу, которую возможно напечатать
   */
  override
  def printConversion: Matrix[ String ] = RectangleMatrix(matrix).printConversion
  
  /**
   * @param i номер строки
   *
   * @return строку матрицы
   */
  override
  def row( i: Int ): Seq[ Boolean ] = apply(i)
  
  /**
   * @param j номер столбца
   *
   * @return столбец матрицы
   */
  override
  def col( j: Int ): Seq[ Boolean ] = indices._1.map(i=> apply(i,j))
  
  /**
   * map обыкновенный
   *
   * @param f функция из T в T2
   *
   * @tparam T2 тип возвращаемого значения.
   *
   * @return Матрица с новыми значениями
   */
  override
  def map[ T2 ]( f: Boolean => T2 ): Matrix[ T2 ] = RectangleMatrix(matrix).map(f)
  
  /**
   * map с индексами
   *
   * @param f функция, получающая 2 координаты и значение из матрицы по этим координатам.
   *
   * @tparam T2 тип возвращаемого значения.
   *
   * @return Матрица с новыми значениями
   */
  override
  def mapWithIndices[ T2 ]( f: Int => Int => Boolean => T2 ): Matrix[ T2 ] = RectangleMatrix(matrix).mapWithIndices(f)
  
  /**
   * foreach обыкновенный
   *
   * @param f функция потребляющая T
   */
  override
  def foreach( f: Boolean => Unit ): Unit = RectangleMatrix(matrix).foreach(f)
  
  /**
   * foreach с индексами
   *
   * аналогично map с индексами
   *
   * @param f функция, потребляющая индексы и T
   */
  override
  def foreachWithIndices( f: Int => Int => Boolean => Unit ): Unit = RectangleMatrix(matrix).foreachWithIndices(f)
  
  override def flatten: Seq[ Boolean ] = matrix.flatten
}
object BipartiteGraphAdjacencyMatrix{
  case class Limit(l:Int,r:Int){
    def apply(value:Int):Boolean = value >= l && value < r
  }
  case class Limits(rowLim:Limit,colLim:Limit) {
    def apply(i:Int,j:Int): Boolean = rowLim(i) && colLim(j)
  }
  def fromSlices(
                  size: Int,
                  delimiter: Int,
                  f: (Int, Int) => Boolean
                ): BipartiteGraphAdjacencyMatrix = {
    if(size < delimiter) throw new IndexOutOfBoundsException
    BipartiteGraphAdjacencyMatrix(
      right = SeqCartesianProduct(
        0 until delimiter,
        delimiter until size
        ).matrix.map(pair => f(pair._1, pair._2)),
      left = SeqCartesianProduct(
        delimiter until size,
        0 until delimiter
        ).matrix.map(pair => f(pair._1, pair._2))
      )
  }
}
