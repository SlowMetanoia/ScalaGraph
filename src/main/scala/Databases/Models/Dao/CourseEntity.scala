package Databases.Models.Dao

import java.util.UUID

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
