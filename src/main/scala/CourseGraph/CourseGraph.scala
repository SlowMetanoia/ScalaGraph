package CourseGraph

import Databases.Models.Domain.Course
import Graphs.Implementations.BipartiteOrientedGraph
import Graphs.Structures.Storeges.CourseLinkKeepers.{ CourseLinkKeeper, KSALinkKeeper }

case
class CourseGraph( courses: Seq[ Course ] ) {
  val curseNum: Int = courses.length
  val courseMap: CourseLinkKeeper = CourseLinkKeeper(courses.zipWithIndex.toMap)
  private
  val KSAMap: KSALinkKeeper = KSALinkKeeper(courses.flatMap(course =>
                                           course.inputSkills ++
                                             course.outputSkills ++
                                             course.inputAbility ++
                                             course.outputAbility ++
                                             course.inputKnowledge ++
                                             course.outputKnowledge)
                                                   .distinct
                                                   .zipWithIndex.map(elem=> (elem._1,elem._2 + curseNum))
                                                   .toMap)
  val KSANum: Int = KSAMap.size
  
  def f( i: Int, j: Int ): Boolean = (i,j) match {
    case (i,j) if i<curseNum&&j>=curseNum=> courseMap(i).isOutput(KSAMap(j))
    case (i,j) if i>=curseNum&&j<curseNum=> courseMap(j).isInput(KSAMap(i))
    case _ => throw new IndexOutOfBoundsException
  }
  
  val courseGraph: BipartiteOrientedGraph = BipartiteOrientedGraph.graphFromSlices(KSANum + curseNum, KSANum, f)
}
