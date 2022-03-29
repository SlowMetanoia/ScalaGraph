package Databases.Services

import Databases.Configurations.{ASC, Id}
import Databases.Models.Domain._
import scalikejdbc.interpolation.SQLSyntax

import java.util.UUID

/**
 * Общий Трейт всех сервисов
 *
 * @tparam ModelType тип Model с которым работает сервис
 */
sealed trait IService[ModelType <: IModel] {
  /**
   * Получение всех Model
   *
   * @param limit   кол-во записей которые необходимо получить
   * @param offset  отсутуп от начала полученных записей
   * @param orderBy поле по которому необходимо отсортировать записи
   * @param sort    порядок сортировки
   * @return последовательность всех Model
   */
  def findAll(limit: Int = 100,
              offset: Int = 0,
              orderBy: SQLSyntax = Id.value,
              sort: SQLSyntax = ASC.value): Seq[ModelType]

  /**
   * Получение Model по id
   *
   * @param id Model которую необходимо получить
   * @return Optional с Model если такая есть в БД, иначе Option.empty
   */
  def findById(id: UUID): Option[ModelType]

  /**
   * Вставка новой Model
   *
   * @param model которую необходимо вставить
   */
  def insert(model: ModelType): Unit

  /**
   * Удаление Model по id
   *
   * @param id Model которую необходимо удалить
   */
  def deleteById(id: UUID): Unit

  /**
   * Обновление Model
   *
   * @param model которое будет обновлено
   */
  def update(model: ModelType): Unit
}

/**
 * Частные, параметризированные трейты сервисов.
 * */
trait ISkillService extends IService[Skill]

trait IAbilityService extends IService[Ability]

trait IKnowledgeService extends IService[Knowledge]

trait ICourseService extends IService[Course]


