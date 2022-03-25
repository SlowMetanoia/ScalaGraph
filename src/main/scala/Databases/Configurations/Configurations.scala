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

  val course = courseDao.findById(UUID.fromString("1fba5a15-c61a-45a0-87ba-98290338968a"))



  val courseSeq = courseDao.findAll()

//  println(course)
//  println(course.get.inputSkills)
//  println(course.get.outputSkills)
//  println(course.get.inputAbility)
//  println(course.get.outputAbility)
//  println(course.get.inputKnowledge)
//  println(course.get.outputKnowledge)

  courseSeq.foreach(c => {
    println()
    println()
    println(c)
    println(c.inputSkills)
    println(c.outputSkills)
    println(c.inputAbility)
    println(c.outputAbility)
    println(c.inputKnowledge)
    println(c.outputKnowledge)
  })
//  println(courseSeq)
//  println(courseSeq.head.inputSkills)
//  println(courseSeq.head.outputSkills)
//  println(courseSeq.head.inputAbility)
//  println(courseSeq.head.outputAbility)
//  println(courseSeq.head.inputKnowledge)
//  println(courseSeq.head.outputKnowledge)
}
