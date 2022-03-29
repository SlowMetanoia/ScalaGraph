package Databases.Mappers

import Databases.Models.Dao.KnowledgeEntity
import Databases.Models.Domain.Knowledge

/**
 * Маппер KnowledgeEntity/Knowledge
 *
 * @see IKnowledgeMapper
 * */
case class KnowledgeMapper() extends IKnowledgeMapper {
  /**
   * Перевод из KnowledgeEntity в Knowledge
   *
   * @param knowledgeEntity Entity для перевода
   * @return полученная Model
   */
  override def entity2Model(knowledgeEntity: KnowledgeEntity): Knowledge =
    Knowledge(knowledgeEntity.id, knowledgeEntity.name)

  /**
   * Перевод из Knowledge в KnowledgeEntity
   *
   * @param knowledge Model для перевода
   * @return полученная Entity
   */
  override def model2Entity(knowledge: Knowledge): KnowledgeEntity =
    KnowledgeEntity(knowledge.id, knowledge.name)
}
