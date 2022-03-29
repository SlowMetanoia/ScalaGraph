package CourseGraph

import Databases.Models.Domain.Course
import Databases.Services.Traits.CourseServiceImpl
import Graphs.Implementations.BipartiteOrientedGraph
import Graphs.Structures.Matrix.RectangleMatrix
import Graphs.Structures.Storeges.CourseLinkKeepers.{ CourseLinkKeeper, KSALinkKeeper }
import Printers.matrixConsolePrinter

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
    case (i,j) if i <  curseNum && j>=curseNum => courseMap(i).isOutput(KSAMap(j))
    case (i,j) if i >= curseNum && j<curseNum  => courseMap(j).isInput(KSAMap(i))
    //todo:rew
    case _ => throw new IndexOutOfBoundsException
  }
  
  val courseGraph: BipartiteOrientedGraph = BipartiteOrientedGraph.graphFromSlices(KSANum + curseNum, KSANum, f)
}
object CourseGraph extends App{
  import scalikejdbc.config.DBs
  DBs.setupAll()
  val courseService = CourseServiceImpl("reticulated")
  val courseGraph = CourseGraph(courseService.findAll(
    limit = 4
  ))
  RectangleMatrix(courseGraph.courseGraph.bipartiteAdjacencyMatrix.matrix).map{
    case true  => 1
    case false => 0
  } <<: matrixConsolePrinter
}