package Databases.Dao.Implementations

import Databases.Dao.Traits.CourseDao
import Databases.Models.Dao.{AbilityEntity, CourseEntity, KnowledgeEntity, SkillEntity}
import scalikejdbc.{NamedDB, scalikejdbcSQLInterpolationImplicitDef}

import java.util.UUID

case class CourseDaoImpl(dbName: String) extends CourseDao {
  override def findAll(): Seq[CourseEntity] = ???

  override def findById(id: UUID): Option[CourseEntity] = {
    val inputSkills: Seq[SkillEntity] = NamedDB(s"$dbName") readOnly { implicit session =>
       sql"""
            SELECT skill.id, skill.name
            FROM skill
            LEFT JOIN course_input_skill
               ON skill.id = course_input_skill.skill_id
            WHERE course_input_skill.course_id = $id
         """.map(skill => SkillEntity(UUID.nameUUIDFromBytes(skill.bytes("id")), skill.string("name"))).collection.apply()
      }

    val outputSkills: Seq[SkillEntity] = NamedDB(s"$dbName") readOnly { implicit session =>
      sql"""
            SELECT skill.id, skill.name
            FROM skill
            LEFT JOIN course_output_skill
                ON skill.id = course_output_skill.skill_id
            WHERE course_output_skill.course_id = $id
         """.map(skill => SkillEntity(UUID.nameUUIDFromBytes(skill.bytes("id")), skill.string("name"))).collection.apply()
    }

    val inputAbility: Seq[AbilityEntity] = NamedDB(s"$dbName") readOnly { implicit session =>
      sql"""
            SELECT ability.id, ability.name
            FROM ability
            LEFT JOIN course_input_ability
                ON ability.id = course_input_ability.ability_id
            WHERE course_input_ability.course_id = $id
         """.map(ability => AbilityEntity(UUID.nameUUIDFromBytes(ability.bytes("id")), ability.string("name"))).collection.apply()
    }

    val outputAbility: Seq[AbilityEntity] = NamedDB(s"$dbName") readOnly { implicit session =>
      sql"""
            SELECT ability.id, ability.name
            FROM ability
            LEFT JOIN course_output_ability
                ON ability.id = course_output_ability.ability_id
            WHERE course_output_ability.course_id = $id
         """.map(ability => AbilityEntity(UUID.nameUUIDFromBytes(ability.bytes("id")), ability.string("name"))).collection.apply()
    }

    val inputKnowledge: Seq[KnowledgeEntity] = NamedDB(s"$dbName") readOnly { implicit session =>
      sql"""
            SELECT knowledge.id, knowledge.name
            FROM knowledge
            LEFT JOIN course_input_knowledge
                ON knowledge.id = course_input_knowledge.knowledge_id
            WHERE course_input_knowledge.course_id = $id
         """.map(knowledge => KnowledgeEntity(UUID.nameUUIDFromBytes(knowledge.bytes("id")), knowledge.string("name"))).collection.apply()
    }

    val outputKnowledge: Seq[KnowledgeEntity] = NamedDB(s"$dbName") readOnly { implicit session =>
      sql"""
            SELECT knowledge.id, knowledge.name
            FROM knowledge
            LEFT JOIN course_output_knowledge
                ON knowledge.id = course_output_knowledge.knowledge_id
            WHERE course_output_knowledge.course_id = $id
         """.map(knowledge => KnowledgeEntity(UUID.nameUUIDFromBytes(knowledge.bytes("id")), knowledge.string("name"))).collection.apply()
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
        outputKnowledge)).single.apply()
    }
  }

  override def insert(course: CourseEntity): Unit = ???

  override def deleteById(id: UUID): Unit = ???

  override def update(course: CourseEntity): Unit = ???
}
