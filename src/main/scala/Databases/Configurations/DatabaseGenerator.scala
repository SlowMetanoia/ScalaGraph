package Databases.Configurations

import Databases.Dao.Implementations.{AbilityDaoImpl, CourseDaoImpl, KnowledgeDaoImpl, SkillDaoImpl}
import Databases.Dao.Traits.{AbilityDao, CourseDao, KnowledgeDao, SkillDao}
import Databases.Models.Dao.{AbilityEntity, CourseEntity, KnowledgeEntity, SkillEntity}
import scalikejdbc.{NamedDB, scalikejdbcSQLInterpolationImplicitDef}

import java.util.UUID
import scala.annotation.tailrec
import scala.util.Random

/**
 * Оставь надежды всяк сюда входящий
 * */
class DatabaseGenerator(val dbname: String) {
  private val skillDao: SkillDao = SkillDaoImpl(dbname)
  private val abilityDao: AbilityDao = AbilityDaoImpl(dbname)
  private val knowledgeDao: KnowledgeDao = KnowledgeDaoImpl(dbname)
  private val courseDao: CourseDao = CourseDaoImpl(dbname)

  def dropDatabase(): Unit =
    NamedDB(s"$dbname") localTx { implicit session =>
      sql"""
         DROP TABLE IF EXISTS course_input_skill;
         DROP TABLE IF EXISTS course_output_skill;

         DROP TABLE IF EXISTS course_input_ability;
         DROP TABLE IF EXISTS course_output_ability;

         DROP TABLE IF EXISTS course_input_knowledge;
         DROP TABLE IF EXISTS course_output_knowledge;

         DROP TABLE IF EXISTS skill;
         DROP TABLE IF EXISTS ability;
         DROP TABLE IF EXISTS knowledge;
         DROP TABLE IF EXISTS course;
      """.update.apply()
    }

  def createDatabase(): Unit =
    NamedDB(s"$dbname") localTx { implicit session =>
      sql"""
        CREATE TABLE IF NOT EXISTS COURSE (
          id UUID NOT NULL UNIQUE PRIMARY KEY,
          name VARCHAR(255)
        );

        CREATE TABLE IF NOT EXISTS SKILL (
          id UUID NOT NULL UNIQUE PRIMARY KEY,
          name VARCHAR(255)
        );

        CREATE TABLE IF NOT EXISTS ABILITY (
          id UUID NOT NULL UNIQUE PRIMARY KEY,
          name VARCHAR(255)
        );

        CREATE TABLE IF NOT EXISTS KNOWLEDGE (
          id UUID NOT NULL UNIQUE PRIMARY KEY,
          name VARCHAR(255)
        );



        CREATE TABLE IF NOT EXISTS COURSE_INPUT_SKILL (
          course_id UUID NOT NULL,
          skill_id UUID NOT NULL,

          CONSTRAINT course_input_skill_pk
            PRIMARY KEY (course_id, skill_id),

          CONSTRAINT course_input_skill_fk
            FOREIGN KEY (course_id)
            REFERENCES COURSE(id)
            ON DELETE CASCADE,
          CONSTRAINT input_skill_fk
            FOREIGN KEY (skill_id)
            REFERENCES SKILL(id)
            ON DELETE CASCADE
          );

        CREATE TABLE IF NOT EXISTS COURSE_OUTPUT_SKILL (
          course_id UUID NOT NULL,
          skill_id UUID NOT NULL,

          CONSTRAINT course_output_skill_pk
            PRIMARY KEY (course_id, skill_id),

          CONSTRAINT course_output_skill_fk
            FOREIGN KEY (course_id)
            REFERENCES COURSE(id)
            ON DELETE CASCADE,
          CONSTRAINT output_skill_fk
            FOREIGN KEY (skill_id)
            REFERENCES SKILL(id)
            ON DELETE CASCADE
        );



        CREATE TABLE IF NOT EXISTS COURSE_INPUT_ABILITY (
          course_id UUID NOT NULL,
          ability_id UUID NOT NULL,

          CONSTRAINT course_input_ability_pk
            PRIMARY KEY (course_id, ability_id),

          CONSTRAINT course_input_ability_fk
            FOREIGN KEY (course_id)
            REFERENCES COURSE(id)
            ON DELETE CASCADE,
          CONSTRAINT input_ability_fk
            FOREIGN KEY (ability_id)
            REFERENCES ABILITY(id)
            ON DELETE CASCADE
        );

        CREATE TABLE IF NOT EXISTS COURSE_OUTPUT_ABILITY (
          course_id UUID NOT NULL,
          ability_id UUID NOT NULL,

          CONSTRAINT course_output_ability_pk
            PRIMARY KEY (course_id, ability_id),

          CONSTRAINT course_output_ability_fk
            FOREIGN KEY (course_id)
            REFERENCES COURSE(id)
            ON DELETE CASCADE,
          CONSTRAINT output_ability_fk
            FOREIGN KEY (ability_id)
            REFERENCES ABILITY(id)
            ON DELETE CASCADE
        );



        CREATE TABLE IF NOT EXISTS COURSE_INPUT_KNOWLEDGE (
          course_id UUID NOT NULL,
          knowledge_id UUID NOT NULL,

          CONSTRAINT course_input_knowledge_pk
            PRIMARY KEY (course_id, knowledge_id),

          CONSTRAINT course_input_knowledge_fk
            FOREIGN KEY (course_id)
            REFERENCES COURSE(id)
            ON DELETE CASCADE,
          CONSTRAINT input_knowledge_fk
            FOREIGN KEY (knowledge_id)
            REFERENCES KNOWLEDGE(id)
            ON DELETE CASCADE
        );

        CREATE TABLE IF NOT EXISTS COURSE_OUTPUT_KNOWLEDGE (
          course_id UUID NOT NULL,
          knowledge_id UUID NOT NULL,

          CONSTRAINT course_output_knowledge_pk
            PRIMARY KEY (course_id, knowledge_id),

          CONSTRAINT course_output_knowledge_fk
            FOREIGN KEY (course_id)
            REFERENCES COURSE(id)
            ON DELETE CASCADE,
          CONSTRAINT output_knowledge_fk
            FOREIGN KEY (knowledge_id)
            REFERENCES KNOWLEDGE(id)
            ON DELETE CASCADE
        );
      """.update.apply()
    }

  def generateSkill(interval: (Int, Int)): Unit =
    for (i <- 1 to Random.between(interval._1, interval._2))
      skillDao.insert(SkillEntity(UUID.randomUUID(), s"Навык$i"))

  def generateAbility(interval: (Int, Int)): Unit =
    for (i <- 1 to Random.between(interval._1, interval._2))
      abilityDao.insert(AbilityEntity(UUID.randomUUID(), s"Умение$i"))

  def generateKnowledge(interval: (Int, Int)): Unit =
    for (i <- 1 to Random.between(interval._1, interval._2))
      knowledgeDao.insert(KnowledgeEntity(UUID.randomUUID(), s"Знание$i"))

  def generateCourse(courseInterval: (Int, Int ),
                     inputSkillInterval: (Int, Int),
                     outputSkillInterval: (Int, Int),
                     inputAbilityInterval: (Int, Int),
                     outputAbilityInterval: (Int, Int),
                     inputKnowledgeInterval: (Int, Int),
                     outputKnowledgeInterval: (Int, Int)): Unit = {
    val numberOfCourses = Random.between(courseInterval._1, courseInterval._2)

    val inputNumberOfSkills = Random.between(inputSkillInterval._1, inputSkillInterval._2)
    val outputNumberOfSkills = Random.between(outputSkillInterval._1, outputSkillInterval._2)

    val inputNumberOfAbility = Random.between(inputAbilityInterval._1, inputAbilityInterval._2)
    val outputNumberOfAbility = Random.between(outputAbilityInterval._1, outputAbilityInterval._2)

    val inputNumberOfKnowledge = Random.between(inputKnowledgeInterval._1, inputKnowledgeInterval._2)
    val outputNumberOfKnowledge = Random.between(outputKnowledgeInterval._1, outputKnowledgeInterval._2)

    val sakAVG = {
      Seq(inputNumberOfSkills, outputNumberOfSkills,
        inputNumberOfAbility, outputNumberOfAbility,
        inputNumberOfKnowledge, outputNumberOfKnowledge)
        .foldLeft((0, 1)) {
          case ((avg, idx), next) => (avg + (next - avg)/idx, idx + 1)
        }._1}

    val skills = skillDao.findAll(numberOfCourses * sakAVG)
    val ability = abilityDao.findAll(numberOfCourses * sakAVG)
    val knowledge = knowledgeDao.findAll(numberOfCourses * sakAVG)

    for (i <- 1 to Random.between(courseInterval._1, courseInterval._2))
      courseDao.insert(CourseEntity(
        id = UUID.randomUUID(),
        name = s"КУРС$i",

        inputSkills = uniqueSkills(skills, inputNumberOfSkills),
        outputSkills = uniqueSkills(skills, outputNumberOfSkills),

        inputAbility = uniqueAbilities(ability, inputNumberOfAbility),
        outputAbility = uniqueAbilities(ability, outputNumberOfAbility),

        inputKnowledge = uniqueKnowledge(knowledge, inputNumberOfKnowledge),
        outputKnowledge = uniqueKnowledge(knowledge, outputNumberOfKnowledge)
      ))
  }

  def uniqueSkills(skills: Seq[SkillEntity], quantity: Int): Seq[SkillEntity] = {
    val res = Seq.empty[SkillEntity]

    @tailrec
    def step(skills: Seq[SkillEntity], res: Seq[SkillEntity], quantity: Int): Seq[SkillEntity] = {
      if (quantity == 0) return res
      val skill = skills(Random.between(0, skills.length))
      step(skills.filter(x => x != skill), res :+ skill, quantity - 1)
    }

    step(skills, res, quantity)
  }

  def uniqueAbilities(abilities: Seq[AbilityEntity], quantity: Int): Seq[AbilityEntity] = {
    val res = Seq.empty[AbilityEntity]

    @tailrec
    def step(abilities: Seq[AbilityEntity], res: Seq[AbilityEntity], quantity: Int): Seq[AbilityEntity] = {
      if (quantity == 0) return res
      val ability = abilities(Random.between(0, abilities.length))
      step(abilities.filter(x => x != ability), res :+ ability, quantity - 1)
    }

    step(abilities, res, quantity)
  }

  def uniqueKnowledge(knowledge: Seq[KnowledgeEntity], quantity: Int): Seq[KnowledgeEntity] = {
    val res = Seq.empty[KnowledgeEntity]

    @tailrec
    def step(knowledge: Seq[KnowledgeEntity], res: Seq[KnowledgeEntity], quantity: Int): Seq[KnowledgeEntity] = {
      if (quantity == 0) return res
      val know = knowledge(Random.between(0, knowledge.length))
      step(knowledge.filter(x => x != know), res :+ know, quantity - 1)
    }

    step(knowledge, res, quantity)
  }
}


object A {
  var dbname: String = ""


}