package Databases.Mappers.Traits

import Databases.Models.Dao.AbilityEntity
import Databases.Models.Domain.Ability

/**
 * Mapper для перевода Ability из entity в бизнес модель и обратно
 *
 * @see Ability
 * */
trait AbilityMapper {
  /**
   * Перевод из AbilityEntity в Ability
   *
   * @param abilityEntity - entity для перевода
   * @return полученная бизнес модель
   */
  def mapToAbility(abilityEntity: AbilityEntity): Ability

  /**
   * Перевод из Ability в AbilityEntity
   *
   * @param ability - бизнес модель для перевода
   * @return полученная entity
   */
  def mapToAbilityEntity(ability: Ability): AbilityEntity
}
