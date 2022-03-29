package Databases.Dao

import Databases.Configurations.{ASC, Id}
import scalikejdbc.interpolation.SQLSyntax
import Databases.Models.Dao._

import java.util.UUID

/**
 * Общий Трейт всех DAO
 *
 * @tparam EntityType тип Entity с которым работает DAO
 */
sealed trait IDao[EntityType <: IEntity] {
  /**
   * Получение всех Entity из таблицы
   *
   * @param limit   кол-во записей которые необходимо получить
   * @param offset  отсутуп от начала полученных записей
   * @param orderBy поле по которому необходимо отсортировать записи
   * @param sort    порядок сортировки
   * @return последовательность всех Entity из таблицы
   */
  def findAll(limit: Int = 100,
              offset: Int = 0,
              orderBy: SQLSyntax = Id.value,
              sort: SQLSyntax = ASC.value): Seq[EntityType]

  /**
   * Получение Entity из таблицы по id
   *
   * @param id Entity которую необходимо получить
   * @return Optional с Entity если такая есть в БД, иначе Option.empty
   */
  def findById(id: UUID): Option[EntityType]

  /**
   * Вставка новой Entity в таблицу
   *
   * @param ability Entity которую необходимо вставить в таблицу
   */
  def insert(ability: EntityType): Unit

  /**
   * Удаление Entity из таблицы по id
   *
   * @param id Entity которую необходимо удалить
   */
  def deleteById(id: UUID): Unit

  /**
   * Обновление Entity в таблице
   *
   * @param ability Entity которое будет обновлено
   */
  def update(ability: EntityType): Unit
}

/**
 * Частные, параметризированные трейты DAO.
 * */
trait ISkillDao extends IDao[SkillEntity]

trait IAbilityDao extends IDao[AbilityEntity]

trait IKnowledgeDao extends IDao[KnowledgeEntity]

trait ICourseDao extends IDao[CourseEntity]
