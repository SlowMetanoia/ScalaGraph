package Mappers

import Excersises.SimpleGraph
import Graphs.Matrix.RectangleMatrix

object InnerMapper {
  def toSimpleGraph[T](coll:Iterable[T])(edgeRule:(T,T)=>Boolean):(Excersises.SimpleGraph,Map[T,Int],Map[Int,T]) = {
    val mapT2Int = coll.zipWithIndex.toMap
    val mapInt2T = for((k,v)<-mapT2Int) yield (v,k)
    (
      SimpleGraph(
        RectangleMatrix(
          (0 to coll.size).map(i=> (0 to coll.size).map{ j =>
            edgeRule(mapInt2T(i), mapInt2T(j))
          }
          ))
      ),mapT2Int,mapInt2T
    )
  }
  def fromSimpleGraph[T](simpleGraph: SimpleGraph,int2T:Map[Int,T]): (Seq[T], Seq[(Int, Int)]) = {
    (simpleGraph.adjacencyMatrix.indices._1.map(i=>int2T(i)),simpleGraph.edges)
  }
}
