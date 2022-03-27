package Databases.Mappers.Traits

import Databases.Models.Dao.KnowledgeEntity
import Databases.Models.Domain.Knowledge

/**
 * Mapper для перевода Knowledge из entity в бизнес модель и обратно
 *
 * @see Knowledge
 * */
trait KnowledgeMapper {
  /**
   * Перевод из KnowledgeEntity в Knowledge
   *
   * @param knowledgeEntity - entity для перевода
   * @return полученная бизнес модель
   */
  def knowledgeEntity2Knowledge(knowledgeEntity: KnowledgeEntity): Knowledge

  /**
   * Перевод из Knowledge в KnowledgeEntity
   *
   * @param knowledge - бизнес модель для перевода
   * @return полученная entity
   */
  def knowledge2KnowledgeEntity(knowledge: Knowledge): KnowledgeEntity
}
