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
  /**
   * Перевод Entity в Model
   * @param entity которая будет переведена
   * @return полученная Model
   */
  def entity2Model(entity: EntityType): ModelType

  /**
   * Перевод Model в Enitity
   * @param model которая будет передевена
   * @return полученна Entity
   */
  def model2Entity(model: ModelType): EntityType
}

/**
 * Трейт ЗУН'ов, который добалвяет перевод из общего вида ЗУН'ов
 * в частный
 * @tparam KSAEntityType частный вид ЗУН'ов, в который будет выполняться перевод
 * @tparam ModelType  тип Model
 */
sealed trait IKSAMapper[KSAEntityType <: IKSAEntity, ModelType <: IKSA]
  extends IEntityMapper[KSAEntityType, ModelType] {
  def iKSAEntity2Entity(entity: IKSAEntity): KSAEntityType
}

/**
 * Частные, параметризированные трейты мапперов.
 * */
trait ISkillMapper extends IKSAMapper[SkillEntity, Skill]

trait IAbilityMapper extends IKSAMapper[AbilityEntity, Ability]

trait IKnowledgeMapper extends IKSAMapper[KnowledgeEntity, Knowledge]

trait ICourseMapper extends IEntityMapper[CourseEntity, Course]
