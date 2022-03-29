package Graphs.Structures.Storeges

/**
 * Однозначно связывает объекты
 * @tparam L первый тип
 * @tparam R второй тип
 */
trait ILinkKeeper[L,R] {
  def left2Right(l:L):R
  def right2Left(r:R):L
  def add( p1:L, p2:R): this.type
  def addAll(cc:Iterable[(L,R)]): this.type
}
