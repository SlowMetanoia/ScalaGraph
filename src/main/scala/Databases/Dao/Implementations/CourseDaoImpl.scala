package Databases.Dao.Implementations

import Databases.Dao.Traits.CourseDao
import Databases.Models.Dao.{CourseEntity, SkillEntity}
import scalikejdbc.{NamedDB, scalikejdbcSQLInterpolationImplicitDef}

import java.util.UUID

case class CourseDaoImpl(dbName: String) extends CourseDao {
  override def findAll(): Seq[CourseEntity] = ???

  override def findById(id: UUID): Option[CourseEntity] = {
//    val skillInOpt = NamedDB(s"$dbName") readOnly { implicit session =>
//        sql"""
//            SELECT skill.id, skill.name
//            FROM skill
//            LEFT JOIN course_input_skill
//                ON skill.id = course_input_skill.skill_id
//            WHERE course_input_skill.course_id = $id
//         """.map(skill => SkillEntity(UUID.nameUUIDFromBytes(skill.bytes("id")), skill.string("name"))).list.apply()
//      }
//    println(skillInOpt.get)
//
    val skillOutOpt: Seq[SkillEntity] = NamedDB(s"$dbName") readOnly { implicit session =>
      sql"""
            SELECT skill.id, skill.name
            FROM skill
            LEFT JOIN course_output_skill
                ON skill.id = course_output_skill.skill_id
            WHERE course_output_skill.course_id = $id
         """.map(skill => SkillEntity(UUID.nameUUIDFromBytes(skill.bytes("id")), skill.string("name"))).collection.apply()
    }
    println(skillOutOpt)

    NamedDB(s"$dbName") readOnly { implicit session =>
      sql"""
           SELECT *
           FROM course
           WHERE id = $id
         """.map(course => CourseEntity(
        UUID.nameUUIDFromBytes(course.bytes("id")),
        course.string("name"),
        Seq.empty,
        skillOutOpt,
        Seq.empty,
        Seq.empty,
        Seq.empty,
        Seq.empty)).single.apply()
    }
  }

  override def insert(course: CourseEntity): Unit = ???

  override def deleteById(id: UUID): Unit = ???

  override def update(course: CourseEntity): Unit = ???
}
