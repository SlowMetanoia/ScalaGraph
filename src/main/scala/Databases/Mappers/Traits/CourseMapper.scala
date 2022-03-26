package Databases.Mappers.Traits

import Databases.Models.Dao.CourseEntity
import Databases.Models.Domain.Course

/**
 * Mapper для перевода Course из entity в бизнес модель и обратно
 * @see Course
 * */
trait CourseMapper {
  /**
   * Перевод из CourseEntity в Course
   * @param courseEntity - entity для перевода
   * @return полученная бизнес модель
   */
  def mapToCourse(courseEntity: CourseEntity): Course

  /**
   * Перевод из Course в CourseEntity
   * @param course - бизнес модель для перевода
   * @return полученная entity
   */
  def mapToCourseEntity(course: Course): CourseEntity
}
