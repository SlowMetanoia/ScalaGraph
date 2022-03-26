package Databases.Models.Domain

import java.util.UUID

case class Course(
  id: UUID,
  name: String,
  inputSkills: Seq[Skill],
  outputSkills: Seq[Skill],
  inputAbility: Seq[Ability],
  outputAbility: Seq[Ability],
  inputKnowledge: Seq[Knowledge],
  outputKnowledge: Seq[Knowledge]
)