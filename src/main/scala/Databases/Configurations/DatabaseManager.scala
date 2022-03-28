package Databases.Configurations

import Databases.Dao.Implementations.{AbilityDaoImpl, CourseDaoImpl, KnowledgeDaoImpl, SkillDaoImpl}
import Databases.Dao.Traits.{AbilityDao, CourseDao, KnowledgeDao, SkillDao}
import Databases.Models.Dao.{AbilityEntity, CourseEntity, KnowledgeEntity, SkillEntity}
import scalikejdbc.{NamedDB, SQLSyntax, scalikejdbcSQLInterpolationImplicitDef}

class DatabaseManager(val dbname: String) {
  private lazy val skillDao: SkillDao = SkillDaoImpl(dbname)
  private lazy val abilityDao: AbilityDao = AbilityDaoImpl(dbname)
  private lazy val knowledgeDao: KnowledgeDao = KnowledgeDaoImpl(dbname)
  private lazy val courseDao: CourseDao = CourseDaoImpl(dbname)

  def dropAllTables(): Unit =
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
      """.execute.apply()
    }

  def dropTable(tableName: String): Unit = {
    NamedDB(s"$dbname") localTx { implicit session =>
      sql"""
        DROP TABLE IF EXISTS ${SQLSyntax.createUnsafely(tableName)};
      """.execute.apply()
    }
  }

  def createAllTables(): Unit = {
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
      """.execute.apply()
    }
  }

  def fillSkillTable(skills: Seq[SkillEntity]): Unit =
    skills.foreach(skill => skillDao.insert(skill))

  def fillAbilityTable(abilities: Seq[AbilityEntity]): Unit =
    abilities.foreach(ability => abilityDao.insert(ability))

  def fillKnowledgeTable(knowledge: Seq[KnowledgeEntity]): Unit =
    knowledge.foreach(know => knowledgeDao.insert(know))

  def fillCourseTable(courses: Seq[CourseEntity]): Unit =
    courses.foreach(course => courseDao.insert(course))

}

