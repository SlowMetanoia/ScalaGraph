package Databases.Configurations

import Databases.Dao.Implementations.SkillDaoImpl
import Databases.Services.Implementations.{AbilityService, CourseService, KnowledgeService, SkillService}
import Databases.Services.Traits.{AbilityServiceImpl, CourseServiceImpl, KnowledgeServiceImpl, SkillServiceImpl}
import scalikejdbc.config.DBs

import scala.util.Random

/**
 * Конфигурация подключений к БД.
 * Данные для подключения берутся из application.conf
 * DBs.setupAll() позволяет установить конфигурации сразу ко всем
 * БД из application.conf
 * */
//App просто проверять запуск - потом уберу
object Configurations extends App {
  DBs.setupAll()

  val skillDao = SkillDaoImpl("tetrahedral")
//  val skillService: SkillService = SkillServiceImpl("horizontal")
//  val abilityService: AbilityService = AbilityServiceImpl("horizontal")
//  val knowledgeService: KnowledgeService = KnowledgeServiceImpl("horizontal")
//  val courseService: CourseService = CourseServiceImpl("horizontal")
//
//  println(skillService.findAll())
//  println(abilityService.findAll())
//  println(knowledgeService.findAll())
//  println(courseService.findAll())

  val dbGen = new DatabaseGenerator("tetrahedral")
//  dbGen.dropDatabase()
//  dbGen.createDatabase()

//  val a = Random.between(10, 20)
//  dbGen.generateSkill(10, 20)
//  dbGen.generateAbility(10, 20)
//  dbGen.generateKnowledge(10, 20)
  dbGen.generateCourse(2, 7)
//
//  val skills = skillDao.findAll()
//  println(dbGen.getSeq(skills))
}
