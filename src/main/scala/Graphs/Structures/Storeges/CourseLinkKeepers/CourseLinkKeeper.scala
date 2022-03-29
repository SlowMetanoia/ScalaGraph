package Graphs.Structures.Storeges.CourseLinkKeepers

import Databases.Models.Domain.Course
import Graphs.Structures.Storeges.MapLinkKeeper

case
class CourseLinkKeeper(
                        fromCourseMap: Map[ Course, Int ],
                        toCourseMap: Map[ Int, Course ]
                      )
  extends MapLinkKeeper(fromCourseMap, toCourseMap) {
  
  
  override def add( p1: Course, p2: Int ): CourseLinkKeeper = CourseLinkKeeper(fromCourseMap + (p1->p2))
  
  def apply( course: Course ): Int = fromCourseMap(course)
  
  def apply( n: Int ): Course = toCourseMap(n)
}

object CourseLinkKeeper {
  private def mapReverse[ T1, T2 ]: Map[ T1, T2 ] => Map[ T2, T1 ] = for ((k, v) <- _) yield (v, k)
  
  def apply( fromCourseMap: Map[ Course, Int ] ): CourseLinkKeeper =
    new CourseLinkKeeper(fromCourseMap, mapReverse(fromCourseMap))
}