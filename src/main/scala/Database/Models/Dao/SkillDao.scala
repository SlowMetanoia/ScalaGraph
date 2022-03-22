package Database.Models.Dao

import java.util.UUID

trait SkillDao {
  def findAll(): Seq[SkillEntity]
  def findById(id: UUID): Option[SkillEntity]
  def insert(skill: SkillEntity): Unit
  def deleteById(id: UUID): Unit
  def update(skill: SkillEntity): Unit
}
