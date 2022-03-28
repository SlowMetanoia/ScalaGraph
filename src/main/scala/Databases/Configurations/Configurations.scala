package Databases.Configurations

import Databases.Dao.Implementations.{AbilityDaoImpl, CourseDaoImpl, KnowledgeDaoImpl, SkillDaoImpl}
import Databases.Dao.Traits.{AbilityDao, CourseDao, KnowledgeDao, SkillDao}
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
  private val tetrahedral = "tetrahedral"
  private val reticulated = "reticulated"

//  val dbManager = new DatabaseManager(horizontal)
//
//  val skills = DataGenerator.generateSkill(350, 400)
//  val abilities= DataGenerator.generateAbility(350, 400)
//  val knowledge = DataGenerator.generateKnowledge(350, 400)
//
//  val courses = DataGenerator.generateCourse((270, 320), (4, 7), skills, abilities, knowledge)
//
//  dbManager.dropAllTables()
//  dbManager.createAllTables()
//  dbManager.fillSkillTable(skills)
//  dbManager.fillAbilityTable(abilities)
//  dbManager.fillKnowledgeTable(knowledge)
//  dbManager.fillCourseTable(courses)

  val courseDao = CourseDaoImpl(horizontal)
  val courses = courseDao.findAll(500)

  println(courses)
  println(courses.length)
}
