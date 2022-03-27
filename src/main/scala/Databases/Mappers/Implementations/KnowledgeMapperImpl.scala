package Databases.Mappers.Implementations

import Databases.Mappers.Traits.KnowledgeMapper
import Databases.Models.Dao.KnowledgeEntity
import Databases.Models.Domain.Knowledge

case class KnowledgeMapperImpl() extends KnowledgeMapper {
  /**
   * Перевод из KnowledgeEntity в Knowledge
   *
   * @param knowledgeEntity entity для перевода
   * @return полученная бизнес модель
   */
  override def knowledgeEntity2Knowledge(knowledgeEntity: KnowledgeEntity): Knowledge =
    Knowledge(knowledgeEntity.id, knowledgeEntity.name)

  /**
   * Перевод из Knowledge в KnowledgeEntity
   *
   * @param knowledge бизнес модель для перевода
   * @return полученная entity
   */
  override def knowledge2KnowledgeEntity(knowledge: Knowledge): KnowledgeEntity =
    KnowledgeEntity(knowledge.id, knowledge.name)
}
