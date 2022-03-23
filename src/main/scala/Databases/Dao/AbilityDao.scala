package Databases.Dao

import Databases.Models.Dao.AbilityEntity

import java.util.UUID

trait AbilityDao {
  def findAll(): Seq[AbilityEntity]
  def findById(id: UUID): Option[AbilityEntity]
  def insert(ability: AbilityEntity): Unit
  def deleteById(id: UUID): Unit
  def update(ability: AbilityEntity): Unit
}
