package Databases.Configurations

import Databases.Models.Dao.{AbilityEntity, CourseEntity, KnowledgeEntity, SkillEntity}

import java.util.UUID
import scala.annotation.tailrec
import scala.util.Random

object DataGenerator {
  private def uniqueSkills(skills: Seq[SkillEntity], quantity: Int): Seq[SkillEntity] = {
    val res = Seq.empty[SkillEntity]

    @tailrec
    def step(skills: Seq[SkillEntity], res: Seq[SkillEntity], quantity: Int): Seq[SkillEntity] = {
      if (quantity == 0) return res
      val skill = skills(Random.between(0, skills.length))
      step(skills.filter(x => x != skill), res :+ skill, quantity - 1)
    }

    step(skills, res, quantity)
  }

  private def uniqueAbilities(abilities: Seq[AbilityEntity], quantity: Int): Seq[AbilityEntity] = {
    val res = Seq.empty[AbilityEntity]

    @tailrec
    def step(abilities: Seq[AbilityEntity], res: Seq[AbilityEntity], quantity: Int): Seq[AbilityEntity] = {
      if (quantity == 0) return res
      val ability = abilities(Random.between(0, abilities.length))
      step(abilities.filter(x => x != ability), res :+ ability, quantity - 1)
    }

    step(abilities, res, quantity)
  }

  private def uniqueKnowledge(knowledge: Seq[KnowledgeEntity], quantity: Int): Seq[KnowledgeEntity] = {
    val res = Seq.empty[KnowledgeEntity]

    @tailrec
    def step(knowledge: Seq[KnowledgeEntity], res: Seq[KnowledgeEntity], quantity: Int): Seq[KnowledgeEntity] = {
      if (quantity == 0) return res
      val know = knowledge(Random.between(0, knowledge.length))
      step(knowledge.filter(x => x != know), res :+ know, quantity - 1)
    }

    step(knowledge, res, quantity)
  }

  def generateSkill(interval: (Int, Int)): Seq[SkillEntity] = {
    val count = Random.between(interval._1, interval._2)
    val res = Seq.empty[SkillEntity]

    @tailrec
    def step(skills: Seq[SkillEntity], count: Int): Seq[SkillEntity] = {
      if (count == 0) return skills
      step(skills :+ SkillEntity(UUID.randomUUID(), s"Навык$count"), count - 1)
    }

    step(res, count)
  }

  def generateAbility(interval: (Int, Int)): Seq[AbilityEntity] = {
    val count = Random.between(interval._1, interval._2)
    val res = Seq.empty[AbilityEntity]

    @tailrec
    def step(abilities: Seq[AbilityEntity], count: Int): Seq[AbilityEntity] = {
      if (count == 0) return abilities
      step(abilities :+ AbilityEntity(UUID.randomUUID(), s"Умение$count"), count - 1)
    }

    step(res, count)
  }

  def generateKnowledge(interval: (Int, Int)): Seq[KnowledgeEntity] = {
    val count = Random.between(interval._1, interval._2)
    val res = Seq.empty[KnowledgeEntity]

    @tailrec
    def step(knowledge: Seq[KnowledgeEntity], count: Int): Seq[KnowledgeEntity] = {
      if (count == 0) return knowledge
      step(knowledge :+ KnowledgeEntity(UUID.randomUUID(), s"Знание$count"), count - 1)
    }

    step(res, count)
  }

  def generateCourse(interval: (Int, Int),
                     sakInterval: (Int, Int),
                     skills: Seq[SkillEntity],
                     abilities: Seq[AbilityEntity],
                     knowledge: Seq[KnowledgeEntity]): Seq[CourseEntity] = {
    val count = Random.between(interval._1, interval._2)
    val res = Seq.empty[CourseEntity]

    @tailrec
    def step(courses: Seq[CourseEntity], count: Int): Seq[CourseEntity] = {
      if (count == 0) return courses

      step(courses :+ CourseEntity(
        id = UUID.randomUUID(),
        name = s"Курс$count",

        inputSkills = uniqueSkills(skills, Random.between(sakInterval._1, sakInterval._2)),
        outputSkills = uniqueSkills(skills, Random.between(sakInterval._1, sakInterval._2)),

        inputAbility = uniqueAbilities(abilities, Random.between(sakInterval._1, sakInterval._2)),
        outputAbility = uniqueAbilities(abilities, Random.between(sakInterval._1, sakInterval._2)),

        inputKnowledge = uniqueKnowledge(knowledge, Random.between(sakInterval._1, sakInterval._2)),
        outputKnowledge = uniqueKnowledge(knowledge, Random.between(sakInterval._1, sakInterval._2))
      ), count - 1)
    }

    step(res, count)
  }
}
