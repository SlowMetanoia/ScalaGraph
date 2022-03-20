package Cousrses.Models.Dao

import Cousrses.Models.Domain.SkillEntity

import java.util.UUID

trait SkillDao {
  def findAll(): Seq[SkillEntity]
  def findById(id: UUID): Option[SkillEntity]

}
