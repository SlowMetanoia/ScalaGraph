package Databases.Services.Implementations

import Databases.Dao.Implementations.CourseDaoImpl
import Databases.Dao.Traits.CourseDao
import Databases.Mappers.Implementations.{AbilityMapperImpl, CourseMapperImpl, KnowledgeMapperImpl, SkillMapperImpl}
import Databases.Mappers.Traits.CourseMapper
import Databases.Models.Domain.Course
import Databases.Services.Traits.CourseService

import java.util.UUID

case class CourseServiceImpl(dbname: String) extends CourseService {
  private val courseMapper: CourseMapper = CourseMapperImpl(
    SkillMapperImpl(),
    AbilityMapperImpl(),
    KnowledgeMapperImpl()
  )
  private val courseDao: CourseDao = CourseDaoImpl(dbname)

  /**
   * Получение всех Course
   *
   * @param limit кол-во записей которые необходимо получить
   * @param offset отсутуп от начала полученных записей
   * @param orderBy поле по которому необходимо отсортировать записи
   * @param sort порядок сортировки
   * @return последовательность всех Course
   */
  override def findAll(limit: Int = 100,
                       offset: Int = 0,
                       orderBy: String = "id",
                       sort: String = "ASC"): Seq[Course] =
    courseDao.findAll(limit, offset, orderBy, sort)
      .map(course => courseMapper.mapToCourse(course))

  /**
   * Получение Course по id
   *
   * @param id Course которую необходимо получить
   * @return Optional с Course если такая есть в БД, иначе Option.empty
   */
  override def findById(id: UUID): Course =
    courseMapper.mapToCourse(courseDao.findById(id).get)

  /**
   * Вставка новой Course
   *
   * @param course entity которуб необходимо вставить в таблицу
   */
  override def insert(course: Course): Unit =
    courseDao.insert(courseMapper.course2CourseEntity(course))

  /**
   * Удаление Courseпо id
   *
   * @param id Course которую необходимо удалить
   */
  override def deleteById(id: UUID): Unit =
    courseDao.deleteById(id)

  /**
   * Обновление Course
   *
   * @param course entity которое будет обновлено
   */
  override def update(course: Course): Unit =
    courseDao.update(courseMapper.course2CourseEntity(course))
}
