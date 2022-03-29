package Databases.Services

import Databases.Configurations.{ASC, Id}
import Databases.Dao.{IKnowledgeDao, KnowledgeDao}
import Databases.Mappers.{IKnowledgeMapper, KnowledgeMapper}
import Databases.Models.Domain.Knowledge
import scalikejdbc.interpolation.SQLSyntax

import java.util.UUID

/**
 * Сервис для работы с Knowledge Entity
 *
 * @param dbname имя БД с которой будет просиходит работа
 */
case class KnowledgeService(dbname: String) extends IKnowledgeService {
  private val knowledgeMapper: IKnowledgeMapper = KnowledgeMapper()
  private val knowledgeDao: IKnowledgeDao = KnowledgeDao(dbname)

  /**
   * @param limit   кол-во записей которые необходимо получить
   * @param offset  отсутуп от начала полученных записей
   * @param orderBy поле по которому необходимо отсортировать записи
   * @param sort    порядок сортировки
   * @return последовательность всех Knowledge
   */
  override def findAll(limit: Int = 100,
                       offset: Int = 0,
                       orderBy: SQLSyntax = Id.value,
                       sort: SQLSyntax = ASC.value): Seq[Knowledge] =
    knowledgeDao.findAll(limit, offset, orderBy, sort)
      .map(knowledge => knowledgeMapper.entity2Model(knowledge))

  /**
   * Получение Knowledge по id
   *
   * @param id Knowledge которую необходимо получить
   * @return Optional с Knowledge если такая есть в БД, иначе Option.empty
   */
  override def findById(id: UUID): Option[Knowledge] =
    knowledgeDao.findById(id).map(knowledgeMapper.entity2Model)

  /**
   * Вставка новой Knowledge
   *
   * @param knowledge entity которую необходимо вставить
   */
  override def insert(knowledge: Knowledge): Unit =
    knowledgeDao.insert(knowledgeMapper.model2Entity(knowledge))

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
    knowledgeDao.update(knowledgeMapper.model2Entity(knowledge))
}
