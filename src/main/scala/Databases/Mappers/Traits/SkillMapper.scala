package Databases.Mappers.Traits

import Databases.Models.Dao.SkillEntity
import Databases.Models.Domain.Skill

/**
 * Mapper для перевода Skill из entity в бизнес модель и обратно
 *
 * @see Skill
 * */
trait SkillMapper {
  /**
   * Перевод из SkillEntity в Skill
   *
   * @param skillEntity - entity для перевода
   * @return полученная бизнес модель
   */
  def mapToSkill(skillEntity: SkillEntity): Skill

  /**
   * Перевод из Skill в SkillEntity
   *
   * @param skill - бизнес модель для перевода
   * @return полученная entity
   */
  def mapToSkillEntity(skill: Skill): SkillEntity
}
