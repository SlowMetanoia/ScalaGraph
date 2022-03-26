package Databases.Services.Traits

import Databases.Dao.Implementations.SkillDaoImpl
import Databases.Dao.Traits.SkillDao
import Databases.Models.Domain.Skill
import Databases.Services.Implementations.SkillService

import java.util.UUID

case class SkillServiceImpl(dbname: String) extends SkillService {
  private val skillDao: SkillDao = SkillDaoImpl(dbname)
  /**
   * Получение всех Skill
   *
   * @param limit   - кол-во записей которые необходимо получить
   * @param offset  - отсутуп от начала полученных записей
   * @param orderBy - поле по которому необходимо отсортировать записи
   * @param sort    - порядок сортировки
   * @return последовательность всех Skill
   */
  override def findAll(limit: Int, offset: Int, orderBy: String, sort: String): Seq[Skill] = ???

  /**
   * Получение Skill по id
   *
   * @param id Skill которую необходимо получить
   * @return Optional с Skill если такая есть в БД, иначе Option.empty
   */
  override def findById(id: UUID): Option[Skill] = ???

  /**
   * Вставка новой Skill
   *
   * @param skill entity которуб необходимо вставить в таблицу
   */
override def insert(skill: Skill): Unit = ???

  /**
   * Удаление Skill по id
   *
   * @param id Skill которую необходимо удалить
   */
  override def deleteById(id: UUID): Unit = ???

  /**
   * Обновление Skill
   *
   * @param skill которое будет обновлено
   */
  override def update(skill: Skill): Unit = ???
}
