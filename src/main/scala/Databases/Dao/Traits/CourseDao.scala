package Databases.Dao.Traits

import Databases.Models.Dao.CourseEntity

import java.util.UUID

trait CourseDao {
  def findAll(): Seq[CourseEntity]
  def findById(id: UUID): Option[CourseEntity]
  def insert(course: CourseEntity): Unit
  def deleteById(id: UUID): Unit
  def update(course: CourseEntity): Unit
}
