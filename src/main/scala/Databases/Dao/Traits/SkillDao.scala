package Databases.Dao.Traits

import Databases.Models.Dao.SkillEntity

import java.util.UUID

/**
 * Трейт DAO для entity Ability
 * */
trait SkillDao {
  /**
   * Получение всех Skill из таблицы
   * @param limit - кол-во записей которые необходимо получить
   * @param offset - отсутуп от начала полученных записей
   * @param orderBy - поле по которому необходимо отсортировать записи
   * @param sort - порядок сортировки
   * @return последовательность всех Skill из таблицы
   */
  def findAll(limit: Int = 100,
              offset: Int = 0,
              orderBy: String = "id",
              sort: String = "ASC"): Seq[SkillEntity]

  /**
   * Получение Skill из таблицы по id
   * @param id Skill которую необходимо получить
   * @return Optional с Skill если такая есть в БД, иначе Option.empty
   */
  def findById(id: UUID): Option[SkillEntity]

  /**
   * Вставка новой Skill в таблицу
   * @param skill entity которуб необходимо вставить в таблицу
   */
  def insert(skill: SkillEntity): Unit

  /**
   * Удаление Skill из таблицы по id
   * @param id Skill которую необходимо удалить
   */
  def deleteById(id: UUID): Unit

  /**
   * Обновление Skill в таблице
   * @param skill entity которое будет обновлено
   */
  def update(skill: SkillEntity): Unit
}
