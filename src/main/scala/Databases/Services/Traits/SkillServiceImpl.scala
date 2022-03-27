package Databases.Services.Traits

import Databases.Dao.Implementations.SkillDaoImpl
import Databases.Dao.Traits.SkillDao
import Databases.Mappers.Implementations.SkillMapperImpl
import Databases.Mappers.Traits.SkillMapper
import Databases.Models.Domain.Skill
import Databases.Services.Implementations.SkillService

import java.util.UUID

case class SkillServiceImpl(dbname: String) extends SkillService {
  private val skillMapper: SkillMapper = SkillMapperImpl()
  private val skillDao: SkillDao = SkillDaoImpl(dbname)

  /**
   * Получение всех Skill
   *
   * @param limit кол-во записей которые необходимо получить
   * @param offset отсутуп от начала полученных записей
   * @param orderBy поле по которому необходимо отсортировать записи
   * @param sort порядок сортировки
   * @return последовательность всех Skill
   */
  override def findAll(limit: Int = 100,
                       offset: Int = 0,
                       orderBy: String = "id",
                       sort: String = "ASC"): Seq[Skill] =
    skillDao.findAll(limit, offset, orderBy, sort)
      .map(skill => skillMapper.skillEntity2Skill(skill))

  /**
   * Получение Skill по id
   *
   * @param id Skill которую необходимо получить
   * @return Optional с Skill если такая есть в БД, иначе Option.empty
   */
  override def findById(id: UUID): Option[Skill] =
    skillDao.findById(id).map(skillMapper.skillEntity2Skill)

  /**
   * Вставка новой Skill
   *
   * @param skill entity которуб необходимо вставить в таблицу
   */
  override def insert(skill: Skill): Unit =
    skillDao.insert(skillMapper.skill2SkillEntity(skill))

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
    skillDao.insert(skillMapper.skill2SkillEntity(skill))
}
