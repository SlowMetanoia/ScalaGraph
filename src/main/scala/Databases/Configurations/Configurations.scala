package Databases.Configurations

import scala.language.implicitConversions
import scalikejdbc.config.DBs

/**
 * Конфигурация подключений к БД.
 * Данные для подключения берутся из application.conf
 * DBs.setupAll() позволяет установить конфигурации сразу ко всем
 * БД из application.conf
 * */
//App просто проверять запуск - потом уберу
object Configurations extends App {
  DBs.setupAll()

  private val horizontal = "horizontal"
  private val reticulated = "reticulated"
  private val tetrahedral = "tetrahedral"

  val skills = DataGenerator.generateKSA(SKILL.value, (20, 30))
  val abilities = DataGenerator.generateKSA(ABILITY.value,(20, 30))
  val knowledge = DataGenerator.generateKSA(KNOWLEDGE.value, (20, 30))
  val course = DataGenerator.generateCourse((300, 400), (1,2), skills, abilities, knowledge)

  val dbManager = new DatabaseManager(tetrahedral)
  dbManager.dropAllTables()
  dbManager.createAllTables()
  dbManager.fillKSATable(SKILL.value, skills)
  dbManager.fillKSATable(ABILITY.value, abilities)
  dbManager.fillKSATable(KNOWLEDGE.value, knowledge)
  dbManager.fillCourseTable(course)
}
