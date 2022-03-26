package Databases.Models.Dao

import java.util.UUID

/**
 * Класс сущностей Course соответсвующий таблице COURSE в БД
 * Может содержать только поля
 * Слой для работы с БД
 * */
case class CourseEntity(
  id: UUID,
  name: String,
  inputSkills: Seq[SkillEntity],
  outputSkills: Seq[SkillEntity],
  inputAbility: Seq[AbilityEntity],
  outputAbility: Seq[AbilityEntity],
  inputKnowledge: Seq[KnowledgeEntity],
  outputKnowledge: Seq[KnowledgeEntity]
)
