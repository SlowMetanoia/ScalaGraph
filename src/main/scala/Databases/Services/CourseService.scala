package Databases.Services

import Databases.Configurations.{ASC, Id}
import Databases.Dao.{CourseDao, ICourseDao}
import Databases.Mappers.{AbilityMapper, CourseMapper, ICourseMapper, KnowledgeMapper, SkillMapper}
import Databases.Models.Domain.Course
import scalikejdbc.interpolation.SQLSyntax

import java.util.UUID

/**
 * Сервис для работы с Course Entity
 *
 * @param dbname имя БД с которой будет просиходит работа
 */
case class CourseService(dbname: String) extends ICourseService {
  private val courseMapper: ICourseMapper = CourseMapper(
    SkillMapper(),
    AbilityMapper(),
    KnowledgeMapper()
  )
  private val courseDao: ICourseDao = CourseDao(dbname)

  /**
   * Получение всех Course
   *
   * @param limit   кол-во записей которые необходимо получить
   * @param offset  отсутуп от начала полученных записей
   * @param orderBy поле по которому необходимо отсортировать записи
   * @param sort    порядок сортировки
   * @return последовательность всех Course
   */
  override def findAll(limit: Int = 100,
                       offset: Int = 0,
                       orderBy: SQLSyntax = Id.value,
                       sort: SQLSyntax = ASC.value): Seq[Course] =
    courseDao.findAll(limit, offset, orderBy, sort)
      .map(course => courseMapper.entity2Model(course))

  /**
   * Получение Course по id
   *
   * @param id Course которую необходимо получить
   * @return Optional с Course если такая есть в БД, иначе Option.empty
   */
  override def findById(id: UUID): Option[Course] =
    courseDao.findById(id).map(courseMapper.entity2Model)

  /**
   * Вставка новой Course
   *
   * @param course entity которуб необходимо вставить в таблицу
   */
  override def insert(course: Course): Unit =
    courseDao.insert(courseMapper.model2Entity(course))

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
    courseDao.update(courseMapper.model2Entity(course))
}
