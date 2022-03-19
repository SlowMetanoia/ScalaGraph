package Graphs

import Excersises.SimpleGraph
import Graphs.Matrix.RectangleMatrix
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class SimpleGraphTest extends AnyFlatSpec with should.Matchers{
  val testGraph1: SimpleGraph = SimpleGraph(
    RectangleMatrix(
      Seq(
        Seq(false, true, true, false, false, false, true),
        Seq(true, false, true, true, false, false, false),
        Seq(true, true, false, false, false, false, false),
        Seq(false, true, false, false, true, false, false),
        Seq(false, false, false, true, false, true, false),
        Seq(false, false, false, false, true, false, true),
        Seq(true, false, false, false, false, true, false)
      )
    )
  )
  val testGraph2: SimpleGraph = SimpleGraph(
    RectangleMatrix(
      Seq{
        Seq(false,true,false,false)
        Seq(true,false,true,false)
        Seq(false,true,false,true)
        Seq(false,false,true,false)
      }
    )
  )

  "Traverse(testGraph,bfs,0)" should "return seq of nodes in bfs order" in{
    println(SimpleGraph.traverse(
      testGraph1,
      0,
      SimpleGraph.bfs
    ).mkString(" "))

    SimpleGraph.traverse(
      testGraph2,
      0,
      SimpleGraph.bfs
    ) should be(Seq(0,1,2,3))
  }
}
