package CourseGraph

import Databases.Models.Domain.{ Course, IKSA }
import Graphs.Implementations.BipartiteOrientedGraph
import Graphs.Structures.Storeges.MapLinkKeeper

case class CourseGraph(courses:Seq[Course]){
  val curseNum: Int = courses.length
  val courseMap:MapLinkKeeper[Course,Int] = MapLinkKeeper(courses.zipWithIndex.toMap)
  private val KSA:Seq[IKSA] = courses.flatMap(course=>
                                                           course.inputSkills ++
                                                           course.outputSkills ++
                                                           course.inputAbility ++
                                                           course.outputAbility ++
                                                           course.inputKnowledge ++
                                                           course.outputKnowledge)
                                                            .distinct
  val KSANum:Int = KSA.length
  val KSAMap:MapLinkKeeper[IKSA,Int] = MapLinkKeeper(
    KSA.
    zipWithIndex.map(el=> (el._1,el._2 + curseNum))
    .toMap)
  val f:(Int,Int)=>Boolean = {
    ???
  }
  val courseGraph:BipartiteOrientedGraph = BipartiteOrientedGraph.graphFromSlices(KSANum + curseNum, KSANum, f)
}
