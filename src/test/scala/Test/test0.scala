package Test

object test0 extends App{
  println(Set(3, 4, 5).zipWithIndex.map(_.swap).toMap.mkString(" "))
}
