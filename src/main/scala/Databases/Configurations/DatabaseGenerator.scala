package Databases.Configurations

import Databases.Dao.Implementations.{AbilityDaoImpl, CourseDaoImpl, KnowledgeDaoImpl, SkillDaoImpl}
import Databases.Dao.Traits.{AbilityDao, CourseDao, KnowledgeDao, SkillDao}
import Databases.Models.Dao.{AbilityEntity, CourseEntity, KnowledgeEntity, SkillEntity}
import Databases.Models.Domain.Skill
import scalikejdbc.{NamedDB, scalikejdbcSQLInterpolationImplicitDef}

import java.util.UUID
import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer
import scala.util
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

  def generateCourse(interval: (Int, Int )): Unit = {
    val skills = skillDao.findAll(1000)
    val ability = abilityDao.findAll(1000)
    val knowledge = knowledgeDao.findAll(1000)

    for (i <- 1 to Random.between(interval._1, interval._2))
      courseDao.insert(CourseEntity(
        id = UUID.randomUUID(),
        name = s"КУРС$i",

        inputSkills = a(skills),
        outputSkills = a(skills),

        inputAbility = r(ability),
        outputAbility = r(ability),

        inputKnowledge = b(knowledge),
        outputKnowledge = b (knowledge)
      ))
  }

  def a(skills: Seq[SkillEntity]): Seq[SkillEntity] = {
    val b = skills.to(ListBuffer)
    val res = new ListBuffer[SkillEntity]

    for (i <- 0 to Random.between(3, 7)) {
      val c = b(i)
      res += c
      b -= c
    }

    res.toSeq
  }

  def r(abilitys: Seq[AbilityEntity]): Seq[AbilityEntity] = {
    val b = abilitys.to(ListBuffer)
    val res = new ListBuffer[AbilityEntity]

    for (i <- 0 to Random.between(3, 7)) {
      val c = b(i)
      res += c
      b -= c
    }

    res.toSeq
  }

  def b(knowledges: Seq[KnowledgeEntity]): Seq[KnowledgeEntity] = {
    val b = knowledges.to(ListBuffer)
    val res = new ListBuffer[KnowledgeEntity]

    for (i <- 0 to Random.between(3, 7)) {
      val c = b(i)
      res += c
      b -= c
    }

    res.toSeq
  }
}

object DatabaseGenerator {
  Random.between(10, 20)
}
