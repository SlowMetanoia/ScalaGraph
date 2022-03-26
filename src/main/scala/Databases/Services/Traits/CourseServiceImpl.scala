package Databases.Services.Traits

import Databases.Dao.Implementations.CourseDaoImpl
import Databases.Dao.Traits.CourseDao
import Databases.Models.Domain.Course
import Databases.Services.Implementations.CourseService

import java.util.UUID

case class CourseServiceImpl(dbname: String) extends CourseService {
  private val courseDao: CourseDao = CourseDaoImpl(dbname)
  /**
   * Получение всех Course
   *
   * @param limit   - кол-во записей которые необходимо получить
   * @param offset  - отсутуп от начала полученных записей
   * @param orderBy - поле по которому необходимо отсортировать записи
   * @param sort    - порядок сортировки
   * @return последовательность всех Course
   */
  override def findAll(limit: Int, offset: Int, orderBy: String, sort: String): Seq[Course] = ???

  /**
   * Получение Course по id
   *
   * @param id Course которую необходимо получить
   * @return Optional с Course если такая есть в БД, иначе Option.empty
   */
  override def findById(id: UUID): Option[Course] = ???

  /**
   * Вставка новой Course
   *
   * @param course entity которуб необходимо вставить в таблицу
   */
override def insert(course: Course): Unit = ???

  /**
   * Удаление Courseпо id
   *
   * @param id Course которую необходимо удалить
   */
  override def deleteById(id: UUID): Unit = ???

  /**
   * Обновление Course
   *
   * @param course entity которое будет обновлено
   */
  override def update(course: Course): Unit = ???
}
