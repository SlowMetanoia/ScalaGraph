package Graphs

import CourseGraph.CourseGraph
import Databases.Models.Domain.Course
import Databases.Services.Traits.CourseServiceImpl
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class CourseGraphTest extends AnyFlatSpec with should.Matchers{
  import scalikejdbc.config.DBs
  DBs.setupAll()
  val courseService: CourseServiceImpl = CourseServiceImpl("reticulated")
  val courses: Seq[ Course ] = courseService.findAll(limit = 4)
  println("Courses:")
  println(courses.mkString("\n"))
  "courses linked and got back" should "be the same" in{
    val courseGraph = CourseGraph(courses)
    
  }
}
