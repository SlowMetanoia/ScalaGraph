package Databases.Configurations

import scala.language.implicitConversions
//import Databases.Configurations.SQLUnsafeSugar.sqlUnsafeSugar2SQLSyntax
import Databases.Dao.Implementations.AbilityDaoImpl
import Databases.Dao.Traits.AbilityDao
import Databases.Services.Implementations.CourseServiceImpl
import scalikejdbc.config.DBs
import scalikejdbc.interpolation.SQLSyntax

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
  private val tetrahedral = "tetrahedral"
  private val reticulated = "reticulated"

  val dbManager = new DatabaseManager(reticulated)

  val skills = DataGenerator.generateSkill(5, 7)
  val abilities= DataGenerator.generateAbility(5, 7)
  val knowledge = DataGenerator.generateKnowledge(5, 7)

  val courses = DataGenerator.generateCourse((10, 15), (1, 2), skills, abilities, knowledge)

  dbManager.dropAllTables()
  dbManager.createAllTables()
  dbManager.fillSkillTable(skills)
  dbManager.fillAbilityTable(abilities)
  dbManager.fillKnowledgeTable(knowledge)
  dbManager.fillCourseTable(courses)

//  val courseDao = CourseDaoImpl(horizontal)
//  val courses = courseDao.findAll(500)
//
//  println(courses)
//  println(courses.length)
//  import Databases.Configurations.SQLUnsafeSugar.sqlUnsafeSugar2SQLSyntax

//    println(Id)
//    println(Id.sqlSyntax)
//    println(Id)
//
//  val abilityDao: AbilityDao = AbilityDaoImpl(tetrahedral)
//  println(abilityDao.findAll())
//  println(Id)
//  println(Id.sqlSyntax)
//
//  val courseService = CourseServiceImpl(horizontal)
//  courseService.findAll()
}
