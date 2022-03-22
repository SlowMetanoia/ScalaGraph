package Graphs

import Excersises.SimpleGraph
import Graphs.Algorithms.Colorization
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
      Seq(
        Seq(false,true,false,false),
        Seq(false,false,true,false),
        Seq(false,false,false,true),
        Seq(false,false,false,false)
      )
    )
  )
  val bipartiteGraphTest0:SimpleGraph = SimpleGraph(RectangleMatrix(
    Seq(
      Seq(0, 0, 0, 0, 1, 1, 0, 0),
      Seq(0, 0, 0, 0, 1, 0, 1, 0),
      Seq(0, 0, 0, 1, 0, 1, 0, 1),
      Seq(0, 0, 1, 0, 0, 0, 0, 0),
      Seq(1, 1, 0, 0, 0, 0, 0, 0),
      Seq(1, 0, 1, 0, 0, 0, 0, 0),
      Seq(0, 1, 0, 0, 0, 0, 0, 0),
      Seq(0, 0, 1, 0, 0, 0, 0, 0)
    )).map(_==1))

  "Traverse(testGraph,bfs,0)" should "return seq of nodes in bfs order" in{
    SimpleGraph.partTraverse(testGraph1, SimpleGraph.bfs, 4).flatten should be(LazyList(4,3,5,1,6,0,2))
  }
  "bipartiteGraphTest0" should "be bipartite" in{
    Colorization.isBipartite(testGraph2)(SimpleGraph) should be(true)
    Colorization.isBipartite(bipartiteGraphTest0)(SimpleGraph) should be(true)
  }
}
