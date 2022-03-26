package Databases.Mappers.Implementations

import Databases.Mappers.Traits.AbilityMapper
import Databases.Models.Dao.AbilityEntity
import Databases.Models.Domain.Ability

case class AbilityMapperImpl() extends AbilityMapper {
  /**
   * Перевод из AbilityEntity в Ability
   *
   * @param abilityEntity - entity для перевода
   * @return полученная бизнес модель
   */
  override def mapToAbility(abilityEntity: AbilityEntity): Ability =
    Ability(abilityEntity.id, abilityEntity.name)

  /**
   * Перевод из Ability в AbilityEntity
   *
   * @param ability - бизнес модель для перевода
   * @return полученная entity
   */
  override def mapToAbilityEntity(ability: Ability): AbilityEntity =
    AbilityEntity(ability.id, ability.name)
}
