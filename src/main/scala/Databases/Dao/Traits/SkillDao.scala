package Databases.Dao.Traits

import Databases.Models.Dao.SkillEntity

import java.util.UUID

trait SkillDao {
  def findAll(): Seq[SkillEntity]
  def findById(id: UUID): Option[SkillEntity]
  def insert(skill: SkillEntity): Unit
  def deleteById(id: UUID): Unit
  def update(skill: SkillEntity): Unit
}
