package Databases.Services

import Databases.Configurations.{ASC, Id}
import Databases.Dao.{ISkillDao, SkillDao}
import Databases.Mappers.{ISkillMapper, SkillMapper}
import Databases.Models.Domain.Skill
import scalikejdbc.interpolation.SQLSyntax

import java.util.UUID

/**
 * Сервис для работы с Skill Entity
 *
 * @param dbname имя БД с которой будет просиходит работа
 */
case class SkillService(dbname: String) extends ISkillService {
  private val skillMapper: ISkillMapper = SkillMapper()
  private val skillDao: ISkillDao = SkillDao(dbname)

  /**
   * Получение всех Skill
   *
   * @param limit   кол-во записей которые необходимо получить
   * @param offset  отсутуп от начала полученных записей
   * @param orderBy поле по которому необходимо отсортировать записи
   * @param sort    порядок сортировки
   * @return последовательность всех Skill
   */
  override def findAll(limit: Int = 100,
                       offset: Int = 0,
                       orderBy: SQLSyntax = Id.value,
                       sort: SQLSyntax = ASC.value): Seq[Skill] =
    skillDao.findAll(limit, offset, orderBy, sort)
      .map(skill => skillMapper.entity2Model(skill))

  /**
   * Получение Skill по id
   *
   * @param id Skill которую необходимо получить
   * @return Optional с Skill если такая есть в БД, иначе Option.empty
   */
  override def findById(id: UUID): Option[Skill] =
    skillDao.findById(id).map(skillMapper.entity2Model)

  /**
   * Вставка новой Skill
   *
   * @param skill entity которуб необходимо вставить в таблицу
   */
  override def insert(skill: Skill): Unit =
    skillDao.insert(skillMapper.model2Entity(skill))

  /**
   * Удаление Skill по id
   *
   * @param id Skill которую необходимо удалить
   */
  override def deleteById(id: UUID): Unit =
    skillDao.deleteById(id)

  /**
   * Обновление Skill
   *
   * @param skill которое будет обновлено
   */
  override def update(skill: Skill): Unit =
    skillDao.insert(skillMapper.model2Entity(skill))
}
