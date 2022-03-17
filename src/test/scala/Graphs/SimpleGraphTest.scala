package Graphs

import Excersises.SimpleGraph
import Graphs.Matrix.RectangleMatrix
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class SimpleGraphTest extends AnyFlatSpec with should.Matchers{
  val testGraph = SimpleGraph(
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
  "Traverse(testGraph,bfs,0)" should "return seq of nodes in bfs order" in{
    println(SimpleGraph.bfs2(
      0,
      Seq(0),
      testGraph,
      new Array[Boolean](testGraph.adjacencyMatrix.size._1)).mkString(" "))
  }
}
