package Databases.Mappers

import Databases.Models.Dao._
import Databases.Models.Domain._

/**
 * Общий трейт для мапперов entity и model классов
 *
 * @tparam EntityType тип Entity
 * @tparam ModelType  тип Model
 * @see IEntity
 * @see IModel
 */
sealed trait IEntityMapper[EntityType <: IEntity, ModelType <: IModel] {
  def entity2Model(entity: EntityType): ModelType

  def model2Entity(model: ModelType): EntityType
}

/**
 * Частные, параметризированные трейты мапперов.
 * */
trait ISkillMapper extends IEntityMapper[SkillEntity, Skill]

trait IAbilityMapper extends IEntityMapper[AbilityEntity, Ability]

trait IKnowledgeMapper extends IEntityMapper[KnowledgeEntity, Knowledge]

trait ICourseMapper extends IEntityMapper[CourseEntity, Course]
