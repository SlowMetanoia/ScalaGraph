package Databases.Configurations

import Databases.Services.Traits.CourseServiceImpl
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

  val dbGenerator = new DatabaseGenerator("tetrahedral")

//  dbGenerator.dropDatabase()
//  dbGenerator.createDatabase()

//  dbGenerator.generateSkill(100, 200)
//  dbGenerator.generateAbility(100, 200)
//  dbGenerator.generateKnowledge(100, 200)

//  dbGenerator.generateCourse(
//    (60, 90),
//    (3,5),
//    (2,6),
//    (4,7),
//    (2,5),
//    (4,6),
//    (3, 5)
//  )

  val courseDao = CourseServiceImpl("tetrahedral")
  println(courseDao.findAll())

}
