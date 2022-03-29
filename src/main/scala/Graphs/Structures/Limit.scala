package Graphs.Structures

case class Limit( l: Int, r: Int ) {
  def apply( value: Int ): Boolean = value >= l && value < r
}