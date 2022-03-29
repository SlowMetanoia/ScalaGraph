package Databases.Configurations

import Databases.Services.{AbilityService, CourseService, IAbilityService, ICourseService, IKnowledgeService, ISkillService, KnowledgeService, SkillService}

import scala.language.implicitConversions
//import Databases.Configurations.SQLUnsafeSugar.sqlUnsafeSugar2SQLSyntax
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

  val skillService: ISkillService = SkillService(reticulated)
  val abilityService: IAbilityService = AbilityService(reticulated)
  val knowledgeService: IKnowledgeService = KnowledgeService(reticulated)
  val courseService: ICourseService = CourseService(reticulated)

  println(skillService.findAll(10, 2, Name.value, DESC.value))
//  println(abilityService.findAll(10))
//  println(knowledgeService.findAll(10))
//  println(courseService.findAll(10))
}
