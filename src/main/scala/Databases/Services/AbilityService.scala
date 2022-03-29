package Databases.Services

import Databases.Configurations.{ASC, Id}
import Databases.Dao.{AbilityDao, IAbilityDao}
import Databases.Mappers.{AbilityMapper, IAbilityMapper}
import Databases.Models.Domain.Ability
import scalikejdbc.interpolation.SQLSyntax

import java.util.UUID

/**
 * Сервис для работы с Ability Entity
 *
 * @param dbname имя БД с которой будет просиходит работа
 */
case class AbilityService(dbname: String) extends IAbilityService {
  private val abilityMapper: IAbilityMapper = AbilityMapper()
  private val abilityDao: IAbilityDao = AbilityDao(dbname)

  /**
   * Получение всех Ability
   *
   * @param limit   кол-во записей которые необходимо получить
   * @param offset  отсутуп от начала полученных записей
   * @param orderBy поле по которому необходимо отсортировать записи
   * @param sort    порядок сортировки
   * @return последовательность всех Ability
   */
  override def findAll(limit: Int = 100,
                       offset: Int = 0,
                       orderBy: SQLSyntax = Id.value,
                       sort: SQLSyntax = ASC.value): Seq[Ability] =
    abilityDao.findAll(limit, offset, orderBy, sort)
      .map(ability => abilityMapper.entity2Model(ability))

  /**
   * Получение Ability по id
   *
   * @param id Ability которую необходимо получить
   * @return Optional с Ability если такая есть в БД, иначе Option.empty
   */
  override def findById(id: UUID): Option[Ability] =
    abilityDao.findById(id).map(abilityMapper.entity2Model)

  /**
   * Вставка новой Ability
   *
   * @param ability entity которую необходимо вставить
   */
  override def insert(ability: Ability): Unit =
    abilityDao.insert(abilityMapper.model2Entity(ability))

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
    abilityDao.update(abilityMapper.model2Entity(ability))
}
