package Databases.Configurations

import Databases.Services.Implementations.{AbilityService, CourseService, KnowledgeService, SkillService}
import Databases.Services.Traits.{AbilityServiceImpl, CourseServiceImpl, KnowledgeServiceImpl, SkillServiceImpl}
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

  val skillService: SkillService = SkillServiceImpl("horizontal")
  val abilityService: AbilityService = AbilityServiceImpl("horizontal")
  val knowledgeService: KnowledgeService = KnowledgeServiceImpl("horizontal")
  val courseService: CourseService = CourseServiceImpl("horizontal")

//  println(skillService.findAll())
//  println(abilityService.findAll())
//  println(knowledgeService.findAll())
  println(courseService.findAll())


}
