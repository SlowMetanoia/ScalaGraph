package Databases.Dao.Implementations

import Databases.Dao.Traits.CourseDao
import Databases.Models.Dao.{AbilityEntity, CourseEntity, KnowledgeEntity, SkillEntity}
import scalikejdbc.{NamedDB, scalikejdbcSQLInterpolationImplicitDef}

import java.util.UUID

/**
 * Реализация CourseDao
 * Выполнение SQL скриптов при работе с таблицами:
 * COURSE, COURSE_INPUT_SKILL, COURSE_OUTPUT_SKILL,
 * COURSE_INPUT_ABILITY, COURSE_OUTPUT_ABILITY,
 * COURSE_INPUT_KNOWLEDGE, COURSE_OUTPUT_KNOWLEDGE
 * и маппинг полученных результатов в объекты класса CourseEntity
 *
 * @see CourseDao
 * @see CourseEntity
 * */
case class CourseDaoImpl(dbName: String) extends CourseDao {

  /**
   * Класс "заглушка" для промежуточного хранения id и имени курса
   */

  import CourseDaoImpl.CoursePlug

  /**
   * Выполнение SQL запроса на получение всех записей из таблицы Course
   *
   * @param limit   - кол-во записей которые необходимо получить
   * @param offset  - отсутуп от начала полученных записей
   * @param orderBy - поле по которому необходимо отсортировать записи
   * @param sort    - порядок сортировки
   * @return последовательность всех Course из таблицы
   */
  override def findAll(limit: Int = 100,
                       offset: Int = 0,
                       orderBy: String = "id",
                       sort: String = "ASC"): Seq[CourseEntity] = {

    val order = orderBy match {
      case "id" => sqls"id"
      case "name" => sqls"name"
    }

    val sortType = sort match {
      case "ASC" => sqls"asc"
      case "DESC" => sqls"desc"
    }

    //Из таблицы Course получаем id и имя
    val coursePlugs: Seq[CoursePlug] = {
      NamedDB(s"$dbName") readOnly { implicit session =>
        sql"""
          SELECT * FROM course
          ORDER BY $order $sortType
          LIMIT $limit
          OFFSET $offset
        """.map(course => CoursePlug(
          UUID.fromString(course.string("id")),
          course.string("name"))
        ).collection.apply()
      }
    }

    //Поскольку объект типа Course явлется сложной сущностью
    //необходимо получить из базы остальные составляющие:
    //входные и выходные знания, умения, навыки
    coursePlugs.map(coursePlug => CourseEntity(
      id = coursePlug.id,
      name = coursePlug.name,

      //получение входных навыков
      inputSkills = {
        NamedDB(s"$dbName") readOnly { implicit session =>
          sql"""
            SELECT skill.id, skill.name
            FROM skill
            LEFT JOIN course_input_skill
              ON skill.id = course_input_skill.skill_id
            WHERE course_input_skill.course_id = ${coursePlug.id}
          """.map(skill => SkillEntity(
            UUID.fromString(skill.string("id")),
            skill.string("name"))
          ).collection.apply()
        }
      },

      //получение выходных навыков
      outputSkills = {
        NamedDB(s"$dbName") readOnly { implicit session =>
          sql"""
            SELECT skill.id, skill.name
            FROM skill
            LEFT JOIN course_output_skill
              ON skill.id = course_output_skill.skill_id
            WHERE course_output_skill.course_id = ${coursePlug.id}
          """.map(skill => SkillEntity(
            UUID.fromString(skill.string("id")),
            skill.string("name"))
          ).collection.apply()
        }
      },

      //получение входных умений
      inputAbility = {
        NamedDB(s"$dbName") readOnly { implicit session =>
          sql"""
            SELECT ability.id, ability.name
            FROM ability
            LEFT JOIN course_input_ability
              ON ability.id = course_input_ability.ability_id
            WHERE course_input_ability.course_id = ${coursePlug.id}
          """.map(ability => AbilityEntity(
            UUID.fromString(ability.string("id")),
            ability.string("name"))
          ).collection.apply()
        }
      },

      //получение выходных умений
      outputAbility = {
        NamedDB(s"$dbName") readOnly { implicit session =>
          sql"""
            SELECT ability.id, ability.name
            FROM ability
            LEFT JOIN course_output_ability
              ON ability.id = course_output_ability.ability_id
            WHERE course_output_ability.course_id = ${coursePlug.id}
          """.map(ability => AbilityEntity(
            UUID.fromString(ability.string("id")),
            ability.string("name"))
          ).collection.apply()
        }
      },

      //получение входных знаний
      inputKnowledge = {
        NamedDB(s"$dbName") readOnly { implicit session =>
          sql"""
            SELECT knowledge.id, knowledge.name
            FROM knowledge
            LEFT JOIN course_input_knowledge
              ON knowledge.id = course_input_knowledge.knowledge_id
            WHERE course_input_knowledge.course_id = ${coursePlug.id}
          """.map(knowledge => KnowledgeEntity(
            UUID.fromString(knowledge.string("id")),
            knowledge.string("name"))
          ).collection.apply()
        }
      },

      //получение выходных знаний
      outputKnowledge = {
        NamedDB(s"$dbName") readOnly { implicit session =>
          sql"""
            SELECT knowledge.id, knowledge.name
            FROM knowledge
            LEFT JOIN course_output_knowledge
              ON knowledge.id = course_output_knowledge.knowledge_id
            WHERE course_output_knowledge.course_id = ${coursePlug.id}
          """.map(knowledge => KnowledgeEntity(
            UUID.fromString(knowledge.string("id")),
            knowledge.string("name"))
          ).collection.apply()
        }
      },
    ))
  }

  /**
   * Выполенение SQL запроса на получение конретной записи из таблицы Course
   * по id
   *
   * @param id Course которую необходимо получить
   * @return Optional с Course если такая есть в БД, иначе Option.empty
   */
  override def findById(id: UUID): Option[CourseEntity] = {
    //Получение id и имени из таблицы Course
    val coursePlugOpt: Option[CoursePlug] = {
      NamedDB(s"$dbName") readOnly { implicit session =>
        sql"""
          SELECT * FROM course
          WHERE id = $id
        """.map(course => CoursePlug(
          UUID.fromString(course.string("id")),
          course.string("name"))
        ).single.apply()
      }
    }

    //Если такой курс найден, то получаем его
    //входные-выходные знания, умения, навыки
    if (coursePlugOpt.isDefined) {
      Option(
        CourseEntity(
          id = coursePlugOpt.get.id,
          name = coursePlugOpt.get.name,

          //получение входных навыков
          inputSkills = {
            NamedDB(s"$dbName") readOnly { implicit session =>
              sql"""
              SELECT skill.id, skill.name
              FROM skill
              LEFT JOIN course_input_skill
                ON skill.id = course_input_skill.skill_id
              WHERE course_input_skill.course_id = $id
            """.map(skill => SkillEntity(
                UUID.fromString(skill.string("id")),
                skill.string("name"))
              ).collection.apply()
            }
          },

          //получение выходных навыков
          outputSkills = {
            NamedDB(s"$dbName") readOnly { implicit session =>
              sql"""
              SELECT skill.id, skill.name
              FROM skill
              LEFT JOIN course_output_skill
                ON skill.id = course_output_skill.skill_id
              WHERE course_output_skill.course_id = $id
            """.map(skill => SkillEntity(
                UUID.fromString(skill.string("id")),
                skill.string("name"))
              ).collection.apply()
            }
          },

          //получение входных умений
          inputAbility = {
            NamedDB(s"$dbName") readOnly { implicit session =>
              sql"""
              SELECT ability.id, ability.name
              FROM ability
              LEFT JOIN course_input_ability
                ON ability.id = course_input_ability.ability_id
              WHERE course_input_ability.course_id = $id
            """.map(ability => AbilityEntity(
                UUID.fromString(ability.string("id")),
                ability.string("name"))
              ).collection.apply()
            }
          },

          //получение выходных умений
          outputAbility = {
            NamedDB(s"$dbName") readOnly { implicit session =>
              sql"""
              SELECT ability.id, ability.name
              FROM ability
              LEFT JOIN course_output_ability
                ON ability.id = course_output_ability.ability_id
              WHERE course_output_ability.course_id = $id
            """.map(ability => AbilityEntity(
                UUID.fromString(ability.string("id")),
                ability.string("name"))
              ).collection.apply()
            }
          },

          //получение входных знаний
          inputKnowledge = {
            NamedDB(s"$dbName") readOnly { implicit session =>
              sql"""
              SELECT knowledge.id, knowledge.name
              FROM knowledge
              LEFT JOIN course_input_knowledge
                ON knowledge.id = course_input_knowledge.knowledge_id
              WHERE course_input_knowledge.course_id = $id
            """.map(knowledge => KnowledgeEntity(
                UUID.fromString(knowledge.string("id")),
                knowledge.string("name"))
              ).collection.apply()
            }
          },

          //получение выходных знаний
          outputKnowledge = {
            NamedDB(s"$dbName") readOnly { implicit session =>
              sql"""
              SELECT knowledge.id, knowledge.name
              FROM knowledge
              LEFT JOIN course_output_knowledge
                ON knowledge.id = course_output_knowledge.knowledge_id
              WHERE course_output_knowledge.course_id = $id
            """.map(knowledge => KnowledgeEntity(
                UUID.fromString(knowledge.string("id")),
                knowledge.string("name"))
              ).collection.apply()
            }
          },
        )
      )
    }
    //Если курс не был найден, то возвращаем
    else Option.empty
  }

  /**
   * Выполнение SQL запроса на вставку новой записи в таблицу Course
   * и остальных составляющих CourseEntity
   *
   * @param course entity которую необходимо вставить в таблицу
   */
  override def insert(course: CourseEntity): Unit = {
    //вставка записи в таблицу COURSE
    NamedDB(s"$dbName") localTx { implicit session =>
      sql"""
        INSERT INTO course (id, name)
        VALUES (${course.id}, ${course.name})
      """.update.apply()

      //Вставка связей курса и его входных-выходных знаний, умений, навыков
      //вставка записей в таблицу COURSE_INPUT_SKILL
      course.inputSkills.map(skill =>
        sql"""
          INSERT INTO course_input_skill (course_id, skill_id)
          VALUES (${course.id}, ${skill.id})
        """.update.apply()
      )

      //вставка записей в таблицу COURSE_OUTPUT_SKILL
      course.outputSkills.map(skill =>
        sql"""
          INSERT INTO course_output_skill (course_id, skill_id)
          VALUES (${course.id}, ${skill.id})
        """.update.apply()
      )

      //вставка записей в таблицу COURSE_INPUT_ABILITY
      course.inputAbility.map(ability =>
        sql"""
          INSERT INTO course_input_ability (course_id, ability_id)
          VALUES (${course.id}, ${ability.id})
        """.update.apply()
      )

      //вставка записей в таблицу COURSE_OUTPUT_ABILITY
      course.outputAbility.map(ability =>
        sql"""
          INSERT INTO course_output_ability (course_id, ability_id)
          VALUES (${course.id}, ${ability.id})
        """.update.apply()
      )

      //вставка записей в таблицу COURSE_INPUT_KNOWLEDGE
      course.inputKnowledge.map(knowledge =>
        sql"""
          INSERT INTO course_input_knowledge (course_id, knowledge_id)
          VALUES (${course.id}, ${knowledge.id})
        """.update.apply()
      )

      //вставка записей в таблицу COURSE_OUTPUT_KNOWLEDGE
      course.outputKnowledge.map(knowledge =>
        sql"""
          INSERT INTO course_output_knowledge (course_id, knowledge_id)
          VALUES (${course.id}, ${knowledge.id})
        """.update.apply()
      )
    }
  }

  /**
   * Выполенение SQL запроса на удаление записи из таблицы Course
   * Удаление из таблиц связей просиходит автоматически благодаря настройке
   * ON DELETE CASCADE
   *
   * @param id Course которую необходимо удалить
   */
  override def deleteById(id: UUID): Unit = {
    NamedDB(s"$dbName") localTx { implicit session =>
      sql"""
        DELETE FROM course
        WHERE id = $id
      """.update.apply()
    }
  }

  /**
   * Выполнение SQL запроса на обновление записи в таблице Course
   * и обновление связей с входными-выходными знаниями, умения, навыками
   *
   * @param course entity которое будет обновлено
   */
  override def update(course: CourseEntity): Unit = {
    //Обновление записи в таблицу COURSE
    NamedDB(s"$dbName") localTx { implicit session =>
      sql"""
        UPDATE course
        SET id = ${course.id}, name = ${course.id}
        WHERE id = ${course.id}
      """.update.apply()

      //Считаем, что при изменения курса могут быть полностью переписаны его
      //входные-выходные знания, умения, навыки
      //Поэтому из таблиц связей удаляем все прошлые записи связанные с этим курсом
      //и вставляем новые.
      //В противном случае велик риск получить нарушение уникальности ключей в таблицах связи
      //или оставить в них не нужные записи.
      //Например:
      //  У курса было 3 выходных умения, а мы хотим чтобы их было 2
      //  у entity мы изменили seq входных умений, сделали обновление, но
      //  в таблице связи обновились 2 связи и осталась третья ???

      //обновление связей с входными навыками
      course.inputSkills.map(skill => {
        sql"""
            DELETE FROM course_input_skill
            WHERE course_id = ${course.id}
          """.update.apply()

        sql"""
            INSERT INTO course_input_skill (course_id, skill_id)
            VALUES (${course.id}, ${skill.id})
          """.update.apply()
      })

      //обновление связей с выходными навыками
      course.outputSkills.map(skill => {
        sql"""
            DELETE FROM course_output_skill
            WHERE course_id = ${course.id}
          """.update.apply()

        sql"""
            INSERT INTO course_output_skill (course_id, skill_id)
            VALUES (${course.id}, ${skill.id})
          """.update.apply()
      })

      //обновление связей с входными умениями
      course.inputAbility.map(ability => {
        sql"""
            DELETE FROM course_input_ability
            WHERE course_id = ${course.id}
          """.update.apply()

        sql"""
            INSERT INTO course_input_ability (course_id, ability_id)
            VALUES (${course.id}, ${ability.id})
          """.update.apply()
      })

      //обновление связей с выходными умениями
      course.outputAbility.map(ability => {
        sql"""
            DELETE FROM course_output_ability
            WHERE course_id = ${course.id}
          """.update.apply()

        sql"""
            INSERT INTO course_output_ability (course_id, ability_id)
            VALUES (${course.id}, ${ability.id})
          """.update.apply()
      })

      //обновление связей с входными знаниями
      course.inputKnowledge.map(knowledge => {
        sql"""
            DELETE FROM course_input_knowledge
            WHERE course_id = ${course.id}
          """.update.apply()

        sql"""
            INSERT INTO course_input_knowledge (course_id, knowledge_id)
            VALUES (${course.id}, ${knowledge.id})
          """.update.apply()
      })

      //обновление связей с выходными знаниями
      course.outputKnowledge.map(knowledge => {
        sql"""
            DELETE FROM course_output_knowledge
            WHERE course_id = ${course.id}
          """.update.apply()

        sql"""
            INSERT INTO course_output_knowledge (course_id, knowledge_id)
            VALUES (${course.id}, ${knowledge.id})
          """.update.apply()
      })
    }
  }
}


object CourseDaoImpl {
  private case class CoursePlug(id: UUID, name: String)
}