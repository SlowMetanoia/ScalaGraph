package Graph

/**
 * Матрица
 * @param matrix - передаваемые значения
 * @tparam T тип
 */
case class Matrix[T](matrix: Seq[Seq[T]]){
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
   * matrix(i)(j) = j элемент в i строке
   */
  def apply(i: Int): Seq[T] = matrix(i)

  /**
   * matrix(i,j) = j элемент в i строке
   */
  def apply(i: Int,j:Int): T = matrix(i)(j)
}
