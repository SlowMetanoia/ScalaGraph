package Databases.Services.Implementations

import Databases.Models.Domain.Course

import java.util.UUID

/**
 * Трейт сервиса для Course
 * В методах класса сервиса можно добавить логику/валидацию
 * перед работай с entity и БД
 * @see Ability
 * */
trait CourseService {
  /**
   * Получение всех Course
   * @param limit - кол-во записей которые необходимо получить
   * @param offset - отсутуп от начала полученных записей
   * @param orderBy - поле по которому необходимо отсортировать записи
   * @param sort - порядок сортировки
   * @return последовательность всех Course
   */
  def findAll(limit: Int = 100,
              offset: Int = 0,
              orderBy: String = "id",
              sort: String = "ASC"): Seq[Course]

  /**
   * Получение Course по id
   * @param id Course которую необходимо получить
   * @return Optional с Course если такая есть в БД, иначе Option.empty
   */
  def findById(id: UUID): Option[Course]

  /**
   * Вставка новой Course
   * @param course entity которуб необходимо вставить в таблицу
   */
  def insert(course: Course): Unit

  /**
   * Удаление Courseпо id
   * @param id Course которую необходимо удалить
   */
  def deleteById(id: UUID): Unit

  /**
   * Обновление Course
   * @param course entity которое будет обновлено
   */
  def update(course: Course): Unit
}
