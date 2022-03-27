package Databases.Services.Implementations

import Databases.Models.Domain.Skill

import java.util.UUID

/**
 * Трейт сервиса для Skill
 * В методах класса сервиса можно добавить логику/валидацию
 * перед работай с entity и БД
 *
 * @see Skill
 * */
trait SkillService {
  /**
   * Получение всех Skill
   *
   * @param limit кол-во записей которые необходимо получить
   * @param offset отсутуп от начала полученных записей
   * @param orderBy поле по которому необходимо отсортировать записи
   * @param sort порядок сортировки
   * @return последовательность всех Skill
   */
  def findAll(limit: Int = 100,
              offset: Int = 0,
              orderBy: String = "id",
              sort: String = "ASC"): Seq[Skill]

  /**
   * Получение Skill по id
   *
   * @param id Skill которую необходимо получить
   * @return Optional с Skill если такая есть в БД, иначе Option.empty
   */
  def findById(id: UUID): Option[Skill]

  /**
   * Вставка новой Skill
   *
   * @param skill entity которуб необходимо вставить в таблицу
   */
  def insert(skill: Skill): Unit

  /**
   * Удаление Skill по id
   *
   * @param id Skill которую необходимо удалить
   */
  def deleteById(id: UUID): Unit

  /**
   * Обновление Skill
   *
   * @param skill которое будет обновлено
   */
  def update(skill: Skill): Unit
}
