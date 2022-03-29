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
  def isInput(ksa:IKSA): Boolean = ksa match {
    case k:Knowledge => inputKnowledge.contains(k)
    case s:Skill => inputSkills.contains(s)
    case a:Ability => inputAbility.contains(a)
    case _ => throw new IllegalArgumentException
  }
  def isOutput(ksa:IKSA): Boolean = ksa match {
    case k:Knowledge => outputKnowledge.contains(k)
    case s:Skill => outputSkills.contains(s)
    case a:Ability => outputAbility.contains(a)
    case _ => throw new IllegalArgumentException
  }
}