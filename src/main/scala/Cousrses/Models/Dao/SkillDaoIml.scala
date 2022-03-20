package Cousrses.Models.Dao
import Cousrses.Models.Domain.SkillEntity
import scalikejdbc.{DB, scalikejdbcSQLInterpolationImplicitDef}

import java.util.UUID

class SkillDaoIml extends SkillDao {
  override def findAll(): Seq[SkillEntity] = ???

  override def findById(id: UUID): Option[SkillEntity] =
    DB readOnly { implicit session =>
      sql"""
            SELECT * FROM SKILL
            WHERE id = ${id}
         """.map(skill => SkillEntity(skill.in("id"), skill.string("name"))).single.apply()
    }
}
