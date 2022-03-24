package Databases.Configurations

import Databases.Dao.Implementations.{CourseDaoImpl, KnowledgeDaoImpl}
import Databases.Dao.Traits.{CourseDao, KnowledgeDao}
import Databases.Models.Dao.KnowledgeEntity
import scalikejdbc.config.DBs

import java.util.UUID

/**
 * Конфигурация подключений к БД.
 * Данные для подключения берутся из application.conf
 * DBs.setupAll() позволяет установить конфигурации сразу ко всем
 * БД из application.conf
 * */
//App просто проверять запуск - потом уберу
object Configurations extends App {
  DBs.setupAll()

  val courseDao: CourseDao = CourseDaoImpl("horizontal")

  val course = courseDao.findById(UUID.fromString("32a2faef-8e58-427b-96cc-ae6cb299f824"))
  println(course)
  println(course.get.outputSkills)

}
