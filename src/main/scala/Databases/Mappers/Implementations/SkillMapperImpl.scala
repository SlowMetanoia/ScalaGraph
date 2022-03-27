package Databases.Mappers.Implementations

import Databases.Mappers.Traits.SkillMapper
import Databases.Models.Dao.SkillEntity
import Databases.Models.Domain.Skill

case class SkillMapperImpl() extends SkillMapper {
  /**
   * Перевод из SkillEntity в Skill
   *
   * @param skillEntity entity для перевода
   * @return полученная бизнес модель
   */
  override def skillEntity2Skill(skillEntity: SkillEntity): Skill =
    Skill(skillEntity.id, skillEntity.name)

  /**
   * Перевод из Skill в SkillEntity
   *
   * @param skill бизнес модель для перевода
   * @return полученная entity
   */
  override def skill2SkillEntity(skill: Skill): SkillEntity =
    SkillEntity(skill.id, skill.name)
}
