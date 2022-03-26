package Databases.Dao.Traits

import Databases.Models.Dao.KnowledgeEntity

import java.util.UUID

/**
 * Трейт DAO для entity Knowledge
 * */
trait KnowledgeDao {
  /**
   * Получение всех Knowledge из таблицы
   * @return последовательность всех Knowledge из таблицы
   */
  def findAll(limit: Int = 100,
              offset: Int = 0,
              orderBy: String = "id",
              sort: String = "ASC"): Seq[KnowledgeEntity]

  /**
   * Получение Knowledge из таблицы по id
   * @param id Knowledge которую необходимо получить
   * @return Optional с Knowledge если такая есть в БД, иначе Option.empty
   */
  def findById(id: UUID): Option[KnowledgeEntity]

  /**
   * Вставка новой Knowledge в таблицу
   * @param knowledge entity которуб необходимо вставить в таблицу
   */
  def insert(knowledge: KnowledgeEntity): Unit

  /**
   * Удаление Knowledge из таблицы по id
   * @param id Knowledge которую необходимо удалить
   */
  def deleteById(id: UUID): Unit

  /**
   * Обновление Knowledge в таблице
   * @param knowledge entity которое будет обновлено
   */
  def update(knowledge: KnowledgeEntity): Unit
}
