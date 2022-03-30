package Databases.Mappers

import Databases.Models.Dao.{AbilityEntity, IKSAEntity}
import Databases.Models.Domain.Ability

/**
 * Маппер AbilityEntity/Ability
 *
 * @see IAbilityMapper
 * */
case class AbilityMapper() extends IAbilityMapper {
  /**
   * Перевод из AbilityEntity в Ability
   *
   * @param abilityEntity Entity для перевода
   * @return полученная Model
   */
  override def entity2Model(abilityEntity: AbilityEntity): Ability =
    Ability(abilityEntity.id, abilityEntity.name)

  /**
   * Перевод из Ability в AbilityEntity
   *
   * @param ability Model для перевода
   * @return полученная Entity
   */
  override def model2Entity(ability: Ability): AbilityEntity =
    AbilityEntity(ability.id, ability.name)

  /**
   * Перевод из общего вида ЗУН'ов - IKSAEntity в AbilityEntity
   * @param entity ЗУН общего вида для перевода
   * @return ЗУН вида AbilityEntity
   */
  override def iKSAEntity2Entity(entity: IKSAEntity): AbilityEntity =
    AbilityEntity(entity.id, entity.name)
}
