package Databases.Services.Implementations

import Databases.Dao.Implementations.AbilityDaoImpl
import Databases.Dao.Traits.AbilityDao
import Databases.Mappers.Implementations.AbilityMapperImpl
import Databases.Mappers.Traits.AbilityMapper
import Databases.Models.Domain.Ability
import Databases.Services.Traits.AbilityService

import java.util.UUID

case class AbilityServiceImpl(dbname: String) extends AbilityService {
  private val abilityMapper: AbilityMapper = AbilityMapperImpl()
  private val abilityDao: AbilityDao = AbilityDaoImpl(dbname)

  /**
   * Получение всех Ability
   *
   * @param limit кол-во записей которые необходимо получить
   * @param offset отсутуп от начала полученных записей
   * @param orderBy поле по которому необходимо отсортировать записи
   * @param sort порядок сортировки
   * @return последовательность всех Ability
   */
  override def findAll(limit: Int = 100,
                       offset: Int = 0,
                       orderBy: String = "id",
                       sort: String = "ASC"): Seq[Ability] =
    abilityDao.findAll(limit, offset, orderBy, sort)
      .map(ability => abilityMapper.abilityEntity2Ability(ability))

  /**
   * Получение Ability по id
   *
   * @param id Ability которую необходимо получить
   * @return Optional с Ability если такая есть в БД, иначе Option.empty
   */
  override def findById(id: UUID): Option[Ability] =
      abilityDao.findById(id).map(abilityMapper.abilityEntity2Ability)
  /**
   * Вставка новой Ability
   *
   * @param ability entity которую необходимо вставить
   */
  override def insert(ability: Ability): Unit =
    abilityDao.insert(abilityMapper.ability2AbilityEntity(ability))

  /**
   * Удаление Ability по id
   *
   * @param id Ability которую необходимо удалить
   */
  override def deleteById(id: UUID): Unit =
    abilityDao.deleteById(id)

  /**
   * Обновление Ability
   *
   * @param ability которое будет обновлено
   */
  override def update(ability: Ability): Unit =
    abilityDao.update(abilityMapper.ability2AbilityEntity(ability))
}
