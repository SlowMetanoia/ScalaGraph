package Databases.Dao.Implementations

import Databases.Dao.Traits.SkillDao
import Databases.Models.Dao.SkillEntity
import scalikejdbc.{NamedDB, scalikejdbcSQLInterpolationImplicitDef}

import java.util.UUID

case class SkillDaoImpl(dbName: String) extends SkillDao {
  override def findAll(): Seq[SkillEntity] =
    NamedDB(s"$dbName") readOnly { implicit session =>
      sql"""
        SELECT * FROM SKILL
      """.map(skill => SkillEntity(UUID.fromString(skill.string("id")), skill.string("name"))).collection.apply()
    }

  override def findById(id: UUID): Option[SkillEntity] =
    NamedDB(s"$dbName") readOnly { implicit session =>
      sql"""
        SELECT * FROM SKILL
        WHERE id = $id
      """.map(skill => SkillEntity(UUID.fromString(skill.string("id")), skill.string("name"))).single.apply()
    }

  override def insert(skill: SkillEntity): Unit =
    NamedDB(s"$dbName") localTx { implicit session =>
      sql"""
        INSERT INTO SKILL (id, name)
        VALUES (${skill.id}, ${skill.name})
      """.update.apply()
    }

  override def deleteById(id: UUID): Unit =
    NamedDB(s"$dbName") localTx { implicit session =>
      sql"""
        DELETE FROM SKILL
        WHERE id = $id
      """.update.apply()
    }

  override def update(skill: SkillEntity): Unit =
    NamedDB(s"$dbName") localTx { implicit session =>
      sql"""
        UPDATE SKILL
        SET id = ${skill.id}, name = ${skill.name}
        WHERE id = ${skill.id}
      """.update.apply()
    }
}
