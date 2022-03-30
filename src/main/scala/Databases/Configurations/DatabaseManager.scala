package Databases.Configurations

import Databases.Dao._
import Databases.Mappers._
import Databases.Models.Dao.{CourseEntity, IKSAEntity}
import scalikejdbc.{NamedDB, SQLSyntax, scalikejdbcSQLInterpolationImplicitDef}

/**
 * Класс управления базой данных (Создание/Удаление/Заполнение таблиц)
 * @param dbname имя БД для работы
 */
class DatabaseManager(val dbname: String) {

  /**
   * Будем считать, что генератор работает всегда вне основной логики программы
   * поэтому мы в праве создать здесь локальные ДАО и Мапперы
   */
  private lazy val skillDao: ISkillDao = SkillDao(dbname)
  private lazy val abilityDao: IAbilityDao = AbilityDao(dbname)
  private lazy val knowledgeDao: IKnowledgeDao = KnowledgeDao(dbname)
  private lazy val courseDao: ICourseDao = CourseDao(dbname)

  private lazy val skillMapper: ISkillMapper = SkillMapper()
  private lazy val abilityMapper: IAbilityMapper = AbilityMapper()
  private lazy val knowledgeMapper: IKnowledgeMapper = KnowledgeMapper()

  /**
   * Заполнение таблиц ЗУН'ов.
   * Поскольку таблицы по сути своей одинаковы - объединяем локигу
   * @param table таблица для заполнения
   * @param entities последователньость ЗУН'ов, которые мы хотим вставить
   */
  def fillKSATable(table: SQLSyntax, entities: Seq[IKSAEntity]): Unit = {
    table match {
      case SKILL.value => entities.foreach(entity => skillDao.insert(skillMapper.iKSAEntity2Entity(entity)))
      case ABILITY.value => entities.foreach(entity => abilityDao.insert(abilityMapper.iKSAEntity2Entity(entity)))
      case KNOWLEDGE.value => entities.foreach(entity => knowledgeDao.insert(knowledgeMapper.iKSAEntity2Entity(entity)))
      case _ => throw new IllegalArgumentException
    }
  }

  /**
   * Заполнение таблицы Курсов.
   * Поскольку курс более сложная сущность, чем ЗУН'ы выделим заполнение курсами БД
   * в отделный метод
   * @param courses последовательность курсов, которые мы хотим вставить
   */
  def fillCourseTable(courses: Seq[CourseEntity]): Unit =
    courses.foreach(course => courseDao.insert(course))

  /**
   * Удаление отдельной таблицы.
   * Почему бы и нет, пускай будет
   * @param tableName таблица которую мы хотим удалить
   */
  def dropTable(tableName: SQLSyntax): Unit = {
    NamedDB(s"$dbname") localTx { implicit session =>
      sql"""
        DROP TABLE IF EXISTS $tableName;
      """.execute.apply()
    }
  }

  /**
   * Удаление всех таблиц из БД.
   * Поскольку при работе с Heroku мы не имеем возможности дропать
   * БД целиком - дропаем все таблицы
   */
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

  /**
   * Создание всех таблиц из БД.
   * Поскольку при работе с Heroku мы не имеем возможности создавать
   * БД целиком - дропаем все таблицы
   */
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
}

