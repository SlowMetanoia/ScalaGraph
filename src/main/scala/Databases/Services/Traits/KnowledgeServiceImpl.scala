package Databases.Services.Traits

import Databases.Dao.Implementations.KnowledgeDaoImpl
import Databases.Dao.Traits.KnowledgeDao
import Databases.Mappers.Implementations.KnowledgeMapperImpl
import Databases.Mappers.Traits.KnowledgeMapper
import Databases.Models.Domain.Knowledge
import Databases.Services.Implementations.KnowledgeService

import java.util.UUID

case class KnowledgeServiceImpl(dbname: String) extends KnowledgeService {
  private val knowledgeMapper: KnowledgeMapper = KnowledgeMapperImpl()
  private val knowledgeDao: KnowledgeDao = KnowledgeDaoImpl(dbname)

  /**
   * @param limit   - кол-во записей которые необходимо получить
   * @param offset  - отсутуп от начала полученных записей
   * @param orderBy - поле по которому необходимо отсортировать записи
   * @param sort    - порядок сортировки
   * @return последовательность всех Knowledge
   */
  override def findAll(limit: Int = 100,
                       offset: Int = 0,
                       orderBy: String = "id",
                       sort: String = "ASC"): Seq[Knowledge] =
    knowledgeDao.findAll(limit, offset, orderBy, sort)
      .map(knowledge => knowledgeMapper.mapToKnowledge(knowledge))

  /**
   * Получение Knowledge по id
   *
   * @param id Knowledge которую необходимо получить
   * @return Optional с Knowledge если такая есть в БД, иначе Option.empty
   */
  override def findById(id: UUID): Knowledge =
    knowledgeMapper.mapToKnowledge(knowledgeDao.findById(id).get)

  /**
   * Вставка новой Knowledge
   *
   * @param knowledge entity которую необходимо вставить
   */
  override def insert(knowledge: Knowledge): Unit =
    knowledgeDao.insert(knowledgeMapper.mapToKnowledgeEntity(knowledge))

  /**
   * Удаление Knowledge по id
   *
   * @param id Knowledge которую необходимо удалить
   */
  override def deleteById(id: UUID): Unit =
    knowledgeDao.deleteById(id)

  /**
   * Обновление Knowledge в таблице
   *
   * @param knowledge entity которое будет обновлено
   */
  override def update(knowledge: Knowledge): Unit =
    knowledgeDao.update(knowledgeMapper.mapToKnowledgeEntity(knowledge))
}
