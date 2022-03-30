package Databases.Configurations

import Databases.Mappers._
import Databases.Models.Dao._
import scalikejdbc.interpolation.SQLSyntax

import java.util.UUID
import scala.annotation.tailrec
import scala.util.Random

/**
 * Генератор данных
 * Может и не совсем красивый, но его как бы вообще быть не должно
 *
 * """Оставь надежды всяк сюда входящий"""
 */
object DataGenerator {
  /**
   * Без них никуда
   */
  private lazy val skillMapper: ISkillMapper = SkillMapper()
  private lazy val abilityMapper: IAbilityMapper = AbilityMapper()
  private lazy val knowledgeMapper: IKnowledgeMapper = KnowledgeMapper()

  /***
   * Функция получения случайных уникальных по
   * @param entities последовательность из которой бу
   * @param quantity кол-во элементов в уникальной последователбности
   * @return кортеж из двух последовательностей.
   *         1 - уникальная последоваталеьность заднного размера.
   *         2 - последовательность без ЗУН'ов выделенных в первую последовательность
   */
  def uniqueKSA(entities: Seq[IKSAEntity], quantity: Int): (Seq[IKSAEntity], Seq[IKSAEntity]) = {
    val res = Seq.empty[IKSAEntity]

    @tailrec
    def step(entities: Seq[IKSAEntity], res: Seq[IKSAEntity], quantity: Int): (Seq[IKSAEntity], Seq[IKSAEntity]) = {
      if (quantity == 0) return (res, entities)
      val entity = entities(Random.between(0, entities.length))
      step(entities.filter(x => x != entity), res :+ entity, quantity -1)
    }

    step(entities, res, quantity)
  }

  /**
   * Генератор ЗУН'ов.
   * В зависимости от преденной таблицы генерируем ЗУн определенного типа
   * @param tableName имя таблицы ЗУН'ы которой мы хотим генерировать
   * @param interval промежуток мин и макс кол-во ЗУН'ов, которые мы хотим сгененировать
   * @return последовательность ЗУН'ов определенного типа
   */
  def generateKSA(tableName: SQLSyntax, interval: (Int, Int)): Seq[IKSAEntity] = {
    val count = Random.between(interval._1, interval._2)
    val res = Seq.empty[IKSAEntity]

    @tailrec
    def step(entities: Seq[IKSAEntity], count: Int): Seq[IKSAEntity] = {
      if (count == 0) return entities

      tableName match {
        case SKILL.value => step(entities :+ SkillEntity(UUID.randomUUID(), s"Навык$count"), count - 1)
        case ABILITY.value => step(entities :+ AbilityEntity(UUID.randomUUID(), s"Умение$count"), count - 1)
        case KNOWLEDGE.value => step(entities :+ KnowledgeEntity(UUID.randomUUID(), s"Знание$count"), count - 1)
        case _ => throw new IllegalArgumentException
      }
    }

    step(res, count)
  }

  /**
   * Генератор курсов.
   * Т.к. курс более сложная сущность, то и логика его генерации сложнее
   * @param interval промежуток мин и макс кол-во Курсов, которые мы хотим сгененировать
   * @param ksaInterval промежуток мин и макс кол-во входных/выходных ЗУН'ов в каждой последовательности
   * @param skills последовательность навыков, используемых при генерации курса
   * @param abilities последовательность умений, используемых при генерации курса
   * @param knowledge последовательность знаний, используемых при генерации курса
   * @return
   */
  def generateCourse(interval: (Int, Int),
                     ksaInterval: (Int, Int),
                     skills: Seq[IKSAEntity],
                     abilities: Seq[IKSAEntity],
                     knowledge: Seq[IKSAEntity]): Seq[CourseEntity] = {
    val count = Random.between(interval._1, interval._2)
    val res = Seq.empty[CourseEntity]

    @tailrec
    def step(courses: Seq[CourseEntity], count: Int): Seq[CourseEntity] = {
      if (count == 0) return courses

      val (inputSkill, skillsWithOutInput) = uniqueKSA(skills, Random.between(ksaInterval._1, ksaInterval._2))
      val (inputAbilities, abilitiesWithOutInput) = uniqueKSA(abilities, Random.between(ksaInterval._1, ksaInterval._2))
      val (inputKnowledge, knowledgeWithOutInput) = uniqueKSA(knowledge, Random.between(ksaInterval._1, ksaInterval._2))

      step(courses :+ CourseEntity(
        id = UUID.randomUUID(),
        name = s"Курс$count",

        inputSkills = inputSkill.map(skillMapper.iKSAEntity2Entity),
        outputSkills = uniqueKSA(skillsWithOutInput, Random.between(ksaInterval._1, ksaInterval._2))._1.map(skillMapper.iKSAEntity2Entity),

        inputAbility = inputAbilities.map(abilityMapper.iKSAEntity2Entity),
        outputAbility = uniqueKSA(abilitiesWithOutInput, Random.between(ksaInterval._1, ksaInterval._2))._1.map(abilityMapper.iKSAEntity2Entity),

        inputKnowledge = inputKnowledge.map(knowledgeMapper.iKSAEntity2Entity),
        outputKnowledge = uniqueKSA(knowledgeWithOutInput, Random.between(ksaInterval._1, ksaInterval._2))._1.map(knowledgeMapper.iKSAEntity2Entity),
      ), count - 1)
    }
    step(res, count)
  }
}
