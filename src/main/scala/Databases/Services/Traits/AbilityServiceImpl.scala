package Databases.Services.Traits

import Databases.Dao.Implementations.AbilityDaoImpl
import Databases.Dao.Traits.AbilityDao
import Databases.Models.Domain.Ability
import Databases.Services.Implementations.AbilityService

import java.util.UUID

case class AbilityServiceImpl(dbname: String) extends AbilityService {
  private val abilityDao: AbilityDao = AbilityDaoImpl(dbname)

  /**
   * Получение всех Ability
   *
   * @param limit   - кол-во записей которые необходимо получить
   * @param offset  - отсутуп от начала полученных записей
   * @param orderBy - поле по которому необходимо отсортировать записи
   * @param sort    - порядок сортировки
   * @return последовательность всех Ability
   */
  override def findAll(limit: Int, offset: Int, orderBy: String, sort: String): Seq[Ability] = ???

  /**
   * Получение Ability по id
   *
   * @param id Ability которую необходимо получить
   * @return Optional с Ability если такая есть в БД, иначе Option.empty
   */
  override def findById(id: UUID): Option[Ability] = ???

  /**
   * Вставка новой Ability
   *
   * @param ability entity которую необходимо вставить
   */
override def insert(ability: Ability): Unit = ???

  /**
   * Удаление Ability по id
   *
   * @param id Ability которую необходимо удалить
   */
  override def deleteById(id: UUID): Unit = ???

  /**
   * Обновление Ability
   *
   * @param ability которое будет обновлено
   */
  override def update(ability: Ability): Unit = ???
}
