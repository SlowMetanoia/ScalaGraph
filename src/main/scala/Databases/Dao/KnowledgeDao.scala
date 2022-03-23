package Databases.Dao

import Databases.Models.Dao.KnowledgeEntity

import java.util.UUID

trait KnowledgeDao {
  def findAll(): Seq[KnowledgeEntity]
  def findById(id: UUID): Option[KnowledgeEntity]
  def insert(knowledge: KnowledgeEntity): Unit
  def deleteById(id: UUID): Unit
  def update(knowledge: KnowledgeEntity): Unit
}
