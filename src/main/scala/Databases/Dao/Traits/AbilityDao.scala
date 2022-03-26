package Databases.Dao.Traits

import Databases.Models.Dao.AbilityEntity

import java.util.UUID

/**
 * Трейт DAO для entity Ability
 * */
trait AbilityDao {
  /**
   * Получение всех Ability из таблицы
   *
   * @param limit   - кол-во записей которые необходимо получить
   * @param offset  - отсутуп от начала полученных записей
   * @param orderBy - поле по которому необходимо отсортировать записи
   * @param sort    - порядок сортировки
   * @return последовательность всех Ability из таблицы
   */
  def findAll(limit: Int = 100,
              offset: Int = 0,
              orderBy: String = "id",
              sort: String = "ASC"): Seq[AbilityEntity]

  /**
   * Получение Ability из таблицы по id
   *
   * @param id Ability которую необходимо получить
   * @return Optional с Ability если такая есть в БД, иначе Option.empty
   */
  def findById(id: UUID): Option[AbilityEntity]

  /**
   * Вставка новой Ability в таблицу
   *
   * @param ability entity которуб необходимо вставить в таблицу
   */
  def insert(ability: AbilityEntity): Unit

  /**
   * Удаление Ability из таблицы по id
   *
   * @param id Ability которую необходимо удалить
   */
  def deleteById(id: UUID): Unit

  /**
   * Обновление Ability в таблице
   *
   * @param ability entity которое будет обновлено
   */
  def update(ability: AbilityEntity): Unit
}
