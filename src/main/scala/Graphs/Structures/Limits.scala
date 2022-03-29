package Graphs.Structures

case
class Limits( rowLim: Limit, colLim: Limit ) {
  def apply( i: Int, j: Int ): Boolean = rowLim(i) && colLim(j)
}
