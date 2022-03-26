package Databases.Services.Implementations

import Databases.Models.Domain.Ability

import java.util.UUID

/**
 * Трейт сервиса для Ability
 * В методах класса сервиса можно добавить логику/валидацию
 * перед работай с entity и БД
 *
 * @see Ability
 * */
trait AbilityService {
  /**
   * Получение всех Ability
   *
   * @param limit   - кол-во записей которые необходимо получить
   * @param offset  - отсутуп от начала полученных записей
   * @param orderBy - поле по которому необходимо отсортировать записи
   * @param sort    - порядок сортировки
   * @return последовательность всех Ability
   */
  def findAll(limit: Int = 100,
              offset: Int = 0,
              orderBy: String = "id",
              sort: String = "ASC"): Seq[Ability]

  /**
   * Получение Ability по id
   *
   * @param id Ability которую необходимо получить
   * @return Optional с Ability если такая есть в БД, иначе Option.empty
   */
  def findById(id: UUID): Ability

  /**
   * Вставка новой Ability
   *
   * @param ability entity которую необходимо вставить
   */
  def insert(ability: Ability): Unit

  /**
   * Удаление Ability по id
   *
   * @param id Ability которую необходимо удалить
   */
  def deleteById(id: UUID): Unit

  /**
   * Обновление Ability
   *
   * @param ability которое будет обновлено
   */
  def update(ability: Ability): Unit
}
