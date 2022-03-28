package Databases.Dao.Traits

import Databases.Models.Dao.CourseEntity

import java.util.UUID

/**
 * Трейт DAO для entity Course
 * */
trait CourseDao {
  /**
   * Получение всех Course из таблицы
   *
   * @param limit   - кол-во записей которые необходимо получить
   * @param offset  - отсутуп от начала полученных записей
   * @param orderBy - поле по которому необходимо отсортировать записи
   * @param sort    - порядок сортировки
   * @return последовательность всех Course из таблицы
   */
  def findAll(limit: Int = 100,
              offset: Int = 0,
              orderBy: String = "id",
              sort: String = "ASC"): Seq[CourseEntity]

  /**
   * Получение Course из таблицы по id
   *
   * @param id Course которую необходимо получить
   * @return Optional с Course если такая есть в БД, иначе Option.empty
   */
  def findById(id: UUID): Option[CourseEntity]

  /**
   * Вставка новой Course в таблицу
   *
   * @param course entity которуб необходимо вставить в таблицу
   */
  def insert(course: CourseEntity): Unit

  /**
   * Удаление Course из таблицы по id
   *
   * @param id Course которую необходимо удалить
   */
  def deleteById(id: UUID): Unit

  /**
   * Обновление Course в таблице
   *
   * @param course entity которое будет обновлено
   */
  def update(course: CourseEntity): Unit
}