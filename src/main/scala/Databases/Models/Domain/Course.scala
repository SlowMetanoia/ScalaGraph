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
){
  def isInput(ksa:IKSA): Boolean = {
    case k:Knowledge => inputKnowledge.contains(k)
    case s:Skill => inputSkills.contains(s)
    case a:Ability => inputAbility.contains(a)
  }
  def isOutput(ksa:IKSA): Boolean = {
    case k:Knowledge => outputKnowledge.contains(k)
    case s:Skill => outputSkills.contains(s)
    case a:Ability => outputAbility.contains(a)
  }
}