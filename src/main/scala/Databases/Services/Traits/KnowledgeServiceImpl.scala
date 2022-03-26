package Databases.Services.Traits

import Databases.Dao.Implementations.KnowledgeDaoImpl
import Databases.Dao.Traits.KnowledgeDao
import Databases.Models.Domain.Knowledge
import Databases.Services.Implementations.KnowledgeService

import java.util.UUID

case class KnowledgeServiceImpl(dbname: String) extends KnowledgeService {
  private val knowledgeDao: KnowledgeDao = KnowledgeDaoImpl(dbname)
  /**
   * @param limit   - кол-во записей которые необходимо получить
   * @param offset  - отсутуп от начала полученных записей
   * @param orderBy - поле по которому необходимо отсортировать записи
   * @param sort    - порядок сортировки
   * @return последовательность всех Knowledge
   */
  override def findAll(limit: Int, offset: Int, orderBy: String, sort: String): Seq[Knowledge] = ???

  /**
   * Получение Knowledge по id
   *
   * @param id Knowledge которую необходимо получить
   * @return Optional с Knowledge если такая есть в БД, иначе Option.empty
   */
  override def findById(id: UUID): Option[Knowledge] = ???

  /**
   * Вставка новой Knowledge
   *
   * @param knowledge entity которую необходимо вставить
   */
override def insert(knowledge: Knowledge): Unit = ???

  /**
   * Удаление Knowledge по id
   *
   * @param id Knowledge которую необходимо удалить
   */
  override def deleteById(id: UUID): Unit = ???

  /**
   * Обновление Knowledge в таблице
   *
   * @param knowledge entity которое будет обновлено
   */
  override def update(knowledge: Knowledge): Unit = ???
}
