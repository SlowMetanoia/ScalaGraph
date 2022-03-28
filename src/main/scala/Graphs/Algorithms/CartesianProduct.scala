package Graphs.Algorithms

import Graphs.Structures.Matrix.RectangleMatrix

case class SeqCartesianProduct[T1,T2](seq1:Seq[T1],seq2:Seq[T2]) {
  def matrix:RectangleMatrix[(T1,T2)] = RectangleMatrix(
    seq1.map(i=> seq2.map(j=> (i,j)))
  )
}

object CartesianProduct {
}
