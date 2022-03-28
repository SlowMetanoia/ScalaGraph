package Algorithms

import Graphs.Algorithms.SeqCartesianProduct
import Printers.matrixConsolePrinter
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class SeqCartesianProductTest extends AnyFlatSpec with should.Matchers{
  val seq1 = Seq(0,1,2)
  val seq2 = Seq(3,4,5,6,7)
  SeqCartesianProduct(seq1, seq2).matrix <<: matrixConsolePrinter
  SeqCartesianProduct(seq2, seq1).matrix <<: matrixConsolePrinter
}
