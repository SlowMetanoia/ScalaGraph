package Graph.Traits

/**
 * Трейт для любых взвешенных графов - графов для рёбер которых определён вес.
 */
trait WightedGraph extends Graph[Int] {
  /**
   * Вес ребра
   * @param source вершина-источник
   * @param receiver вершина-приёмник
   * @return Вес ребра между вершиной источником и вершиной-приёмником, если такое ребро есть. Иначе - неопределенно.
   */
  def weight(source:Int, receiver:Int):Option[Int]

  /**
   * Вес пути
   * @param path последовательность вершин пути
   * @return Вес пути, если такой путь существует. Иначе - неопределенно.
   */
  def pathWeight(path:Seq[Int]):Option[Int]

  /**
   * Минимальный по весу путь
   * @param source вершина - источник
   * @param receiver вершина - приёмник
   * @return Минимальный по весу путь между двумя вершинами, если такой путь возможен. Иначе - неопределенно.
   */
  def minimumWeightPath(source:Int, receiver:Int):Option[Seq[Int]]
}
