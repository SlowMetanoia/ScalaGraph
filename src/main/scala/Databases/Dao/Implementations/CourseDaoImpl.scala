package Databases.Dao.Implementations

import Databases.Dao.Traits.CourseDao
import Databases.Models.Dao.{AbilityEntity, CourseEntity, KnowledgeEntity, SkillEntity}
import scalikejdbc.{NamedDB, scalikejdbcSQLInterpolationImplicitDef}

import java.util.UUID

case class CourseDaoImpl(dbName: String) extends CourseDao {

  import CourseDaoImpl.CoursePlug

  override def findAll(): Seq[CourseEntity] = {
    val coursePlugs: Seq[CoursePlug] = NamedDB(s"$dbName") readOnly { implicit session =>
      sql"""
        SELECT * FROM course
      """.map(course => CoursePlug(UUID.fromString(
        course.string("id")),
        course.string("name"))).collection.apply()
    }

    coursePlugs.map(coursePlug => CourseEntity(
      id = coursePlug.id,
      name = coursePlug.name,

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

  override def findById(id: UUID): Option[CourseEntity] = {
    val inputSkills: Seq[SkillEntity] = NamedDB(s"$dbName") readOnly { implicit session =>
      sql"""
        SELECT skill.id, skill.name
        FROM skill
        LEFT JOIN course_input_skill
          ON skill.id = course_input_skill.skill_id
        WHERE course_input_skill.course_id = $id
      """.map(skill => SkillEntity(UUID.fromString(skill.string("id")), skill.string("name"))).collection.apply()
    }

    val outputSkills: Seq[SkillEntity] = NamedDB(s"$dbName") readOnly { implicit session =>
      sql"""
        SELECT skill.id, skill.name
        FROM skill
        LEFT JOIN course_output_skill
          ON skill.id = course_output_skill.skill_id
        WHERE course_output_skill.course_id = $id
      """.map(skill => SkillEntity(UUID.fromString(skill.string("id")), skill.string("name"))).collection.apply()
    }

    val inputAbility: Seq[AbilityEntity] = NamedDB(s"$dbName") readOnly { implicit session =>
      sql"""
        SELECT ability.id, ability.name
        FROM ability
        LEFT JOIN course_input_ability
          ON ability.id = course_input_ability.ability_id
        WHERE course_input_ability.course_id = $id
     """.map(ability => AbilityEntity(UUID.fromString(ability.string("id")), ability.string("name"))).collection.apply()
    }

    val outputAbility: Seq[AbilityEntity] = NamedDB(s"$dbName") readOnly { implicit session =>
      sql"""
        SELECT ability.id, ability.name
        FROM ability
        LEFT JOIN course_output_ability
          ON ability.id = course_output_ability.ability_id
        WHERE course_output_ability.course_id = $id
      """.map(ability => AbilityEntity(UUID.fromString(ability.string("id")), ability.string("name"))).collection.apply()
    }

    val inputKnowledge: Seq[KnowledgeEntity] = NamedDB(s"$dbName") readOnly { implicit session =>
      sql"""
        SELECT knowledge.id, knowledge.name
        FROM knowledge
        LEFT JOIN course_input_knowledge
          ON knowledge.id = course_input_knowledge.knowledge_id
        WHERE course_input_knowledge.course_id = $id
      """.map(knowledge => KnowledgeEntity(UUID.fromString(knowledge.string("id")), knowledge.string("name"))).collection.apply()
    }

    val outputKnowledge: Seq[KnowledgeEntity] = NamedDB(s"$dbName") readOnly { implicit session =>
      sql"""
        SELECT knowledge.id, knowledge.name
        FROM knowledge
        LEFT JOIN course_output_knowledge
          ON knowledge.id = course_output_knowledge.knowledge_id
        WHERE course_output_knowledge.course_id = $id
     """.map(knowledge => KnowledgeEntity(UUID.fromString(knowledge.string("id")), knowledge.string("name"))).collection.apply()
    }

    NamedDB(s"$dbName") readOnly { implicit session =>
      sql"""
        SELECT *
        FROM course
        WHERE id = $id
      """.map(course => CourseEntity(
        UUID.nameUUIDFromBytes(course.bytes("id")),
        course.string("name"),
        inputSkills,
        outputSkills,
        inputAbility,
        outputAbility,
        inputKnowledge,
        outputKnowledge
      )).single.apply()
    }
  }

  override def insert(course: CourseEntity): Unit = {

  }

  override def deleteById(id: UUID): Unit = ???

  override def update(course: CourseEntity): Unit = ???
}


object CourseDaoImpl {
  private case class CoursePlug(id: UUID, name: String)
}

