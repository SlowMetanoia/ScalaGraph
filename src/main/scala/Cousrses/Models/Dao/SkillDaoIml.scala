package Cousrses.Models.Dao
import scalikejdbc.{DB, NamedDB, scalikejdbcSQLInterpolationImplicitDef}

import java.util.UUID

case class SkillDaoIml(dbName: String) extends SkillDao {
  override def findAll(): Seq[SkillEntity] =
    NamedDB(s"$dbName") readOnly { implicit session =>
      sql"""
           SELECT * FROM SKILL
         """.map(skill => SkillEntity(UUID.nameUUIDFromBytes(skill.bytes("id")), skill.string("name"))).collection.apply()

    }

  override def findById(id: UUID): Option[SkillEntity] =
    NamedDB(s"$dbName") readOnly { implicit session =>
        sql"""
            SELECT * FROM SKILL
            WHERE id = ${id}
         """.map(skill => SkillEntity(UUID.nameUUIDFromBytes(skill.bytes("id")), skill.string("name"))).single.apply()

    }

  override def insert(skill: SkillEntity): Unit =
    NamedDB(s"$dbName") localTx { implicit session =>
      sql"""
           INSERT INTO SKILL (id, name)
           VALUES (${skill.id}, ${skill.name})
         """
    }

  override def deleteById(id: UUID): Unit = ???

  override def update(skill: SkillEntity): Unit = ???
}
