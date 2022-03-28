package Graphs.Structures.Matrix

trait Matrix[ T ] {
  def matrix: Seq[ Seq[ T ] ]
  /**
   * Размеры матрицы
   */
  val size: (Int, Int)
  
  /**
   * Индексы матрицы
   */
  val indices: (Seq[ Int ], Seq[ Int ])
  
  /**
   * Преобразование для печати
   *
   * @return матрицу, которую возможно напечатать
   */
  def printConversion: Matrix[ String ]
  
  /**
   * @param i номер строки
   *
   * @return строку матрицы
   */
  def row( i: Int ): Seq[ T ]
  
  /**
   * @param j номер столбца
   *
   * @return столбец матрицы
   */
  def col( j: Int ): Seq[ T ]
  
  /**
   * matrix( i )( j ) = j элемент в i строке
   */
  def apply( i: Int ): Seq[ T ]
  
  /**
   * matrix( i , j ) = j элемент в i строке
   */
  def apply( i: Int, j: Int ): T
  
  /**
   * map обыкновенный
   *
   * @param f функция из T в T2
   *
   * @tparam T2 тип возвращаемого значения.
   *
   * @return Матрица с новыми значениями
   */
  def map[ T2 ]( f: T => T2 ): Matrix[ T2 ]
  
  /**
   * map с индексами
   *
   * @param f функция, получающая 2 координаты и значение из матрицы по этим координатам.
   *
   * @tparam T2 тип возвращаемого значения.
   *
   * @return Матрица с новыми значениями
   */
  def mapWithIndices[ T2 ]( f: Int => Int => T => T2 ): Matrix[ T2 ]
  
  /**
   * foreach обыкновенный
   *
   * @param f функция потребляющая T
   */
  def foreach( f: T => Unit ): Unit
  
  /**
   * foreach с индексами
   *
   * аналогично map с индексами
   *
   * @param f функция, потребляющая индексы и T
   */
  def foreachWithIndices( f: Int => Int => T => Unit ): Unit
  
  def flatten: Seq[ T ]
}
