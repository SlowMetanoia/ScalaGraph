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

  val courseDao: CourseDao = CourseDaoImpl("horizontal")
  val skillDao: SkillDao = SkillDaoImpl("horizontal")
  val abilityDao: AbilityDao = AbilityDaoImpl("horizontal")
  val knowledgeDao: KnowledgeDao = KnowledgeDaoImpl("horizontal")

  println(abilityDao.findAll())
  println(abilityDao.findAll(limit = 7))
  println(abilityDao.findAll(limit = 7, offset =2))
  println(abilityDao.findAll(limit = 7, offset =2, orderBy = "name"))
  println(abilityDao.findAll(limit = 7, offset =2, orderBy = "name", sort = "DESC"))

  println(courseDao.findAll())
  println(courseDao.findAll(limit = 1))
  println(courseDao.findAll(limit = 7, offset =2))
  println(courseDao.findAll(limit = 7, offset =2, orderBy = "name"))
  println(courseDao.findAll(limit = 7, offset =2, orderBy = "name", sort = "DESC"))

  println(abilityDao.findAll())
  println(abilityDao.findAll(limit = 1))
  println(abilityDao.findAll(limit = 7, offset =2))
  println(abilityDao.findAll(limit = 7, offset =2, orderBy = "name"))
  println(abilityDao.findAll(limit = 7, offset =2, orderBy = "name", sort = "DESC"))

  println(knowledgeDao.findAll())
  println(knowledgeDao.findAll(limit = 1))
  println(knowledgeDao.findAll(limit = 7, offset =2))
  println(knowledgeDao.findAll(limit = 7, offset =2, orderBy = "name"))
  println(knowledgeDao.findAll(limit = 7, offset =2, orderBy = "name", sort = "DESC"))

//  val course = courseDao.findById(UUID.fromString("1fba5a15-c61a-45a0-87ba-98290338968a"))
//
//
//
//  val courseSeq = courseDao.findAll()
//
//  println(course)
//  println(course.get.inputSkills)
//  println(course.get.outputSkills)
//  println(course.get.inputAbility)
//  println(course.get.outputAbility)
//  println(course.get.inputKnowledge)
//  println(course.get.outputKnowledge)

//  courseSeq.foreach(c => {
//    println()
//    println()
//    println(c)
//    println(c.inputSkills)
//    println(c.outputSkills)
//    println(c.inputAbility)
//    println(c.outputAbility)
//    println(c.inputKnowledge)
//    println(c.outputKnowledge)
//  })
//  println(courseSeq)
//  println(courseSeq.head.inputSkills)
//  println(courseSeq.head.outputSkills)
//  println(courseSeq.head.inputAbility)
//  println(courseSeq.head.outputAbility)
//  println(courseSeq.head.inputKnowledge)
//  println(courseSeq.head.outputKnowledge)

//  val course = CourseEntity(
//    id = UUID.randomUUID(),
//    name = "КУРСССС%",
//
//    inputSkills = Seq(
//      skillDao.findById(UUID.fromString("19a64472-44e2-48d7-8b05-4e42d970e2d9")).get,
//      skillDao.findById(UUID.fromString("be0ba051-1fad-4ed4-806a-3d46ad7c4c41")).get
//    ),
//
//    outputSkills = Seq(
//      skillDao.findById(UUID.fromString("7489945d-13bb-4afe-ba9b-0ee66868d93a")).get,
//      skillDao.findById(UUID.fromString("c243f321-09c6-4f4b-ba5f-8b03cc07bcf6")).get
//    ),
//
//    inputAbility = Seq(
//      abilityDao.findById(UUID.fromString("ef831823-12ef-46fe-a3b4-890f7f9cb261")).get,
//      abilityDao.findById(UUID.fromString("5926d26e-a632-4110-9b67-f6445e3d4d7e")).get
//    ),
//
//    outputAbility = Seq(
//      abilityDao.findById(UUID.fromString("682e6343-94da-47e4-bafa-005fd2cf5b9e")).get,
//      abilityDao.findById(UUID.fromString("640b656b-af89-4377-9939-2027cde72068")).get,
//    ),
//
//    inputKnowledge = Seq(
//      knowledgeDao.findById(UUID.fromString("f5ca8350-bad0-470b-927b-7e04908746cd")).get,
//      knowledgeDao.findById(UUID.fromString("469c260b-dbcf-4170-bf17-8d57613b1864")).get
//    ),
//
//    outputKnowledge = Seq(
//      knowledgeDao.findById(UUID.fromString("8f392d63-8121-4aac-9048-5ae460fee5f8")).get,
//      knowledgeDao.findById(UUID.fromString("278ecbfc-eb2d-457b-9f1d-f24d1e03472a")).get,
//    )
//  )
//
////  courseDao.insert(course)
//
////  println(courseDao.findById(UUID.fromString("ba765490-9ab7-42e6-8425-5ed447edd917")).get)
////  courseDao.deleteById(UUID.fromString("ba765490-9ab7-42e6-8425-5ed447edd917"))
////  println(courseDao.findById(UUID.fromString("ba765490-9ab7-42e6-8425-5ed447edd917")).get)
//
//  val foundCourse = courseDao.findById(UUID.fromString("276aa901-863e-40ef-a3fb-63e2a722090d")).get
//  println(foundCourse)
//
//  val updateCourse = CourseEntity(
//    foundCourse.id,
//    "КУРС_ТЕСТ",
//
//    inputSkills = Seq(
////      skillDao.findById(UUID.fromString("7b6f2b65-4dbe-4fcf-9f2b-c3e70cb83835")).get,
////      skillDao.findById(UUID.fromString("be0ba051-1fad-4ed4-806a-3d46ad7c4c41")).get
//      skillDao.findById(UUID.fromString("7489945d-13bb-4afe-ba9b-0ee66868d93a")).get
//    ),
//
//    outputSkills = Seq(
////      skillDao.findById(UUID.fromString("7489945d-13bb-4afe-ba9b-0ee66868d93a")).get,
////      skillDao.findById(UUID.fromString("c243f321-09c6-4f4b-ba5f-8b03cc07bcf6")).get
//      skillDao.findById(UUID.fromString("1f3c8836-fec5-40c2-a7b3-7eb44fc21f80")).get
//    ),
//
//    inputAbility = Seq(
//      abilityDao.findById(UUID.fromString("ef831823-12ef-46fe-a3b4-890f7f9cb261")).get,
//      abilityDao.findById(UUID.fromString("5926d26e-a632-4110-9b67-f6445e3d4d7e")).get
//    ),
//
//    outputAbility = Seq(
//      abilityDao.findById(UUID.fromString("682e6343-94da-47e4-bafa-005fd2cf5b9e")).get,
//      abilityDao.findById(UUID.fromString("640b656b-af89-4377-9939-2027cde72068")).get,
//    ),
//
//    inputKnowledge = Seq(
//      knowledgeDao.findById(UUID.fromString("f5ca8350-bad0-470b-927b-7e04908746cd")).get,
//      knowledgeDao.findById(UUID.fromString("469c260b-dbcf-4170-bf17-8d57613b1864")).get
//    ),
//
//    outputKnowledge = Seq(
//      knowledgeDao.findById(UUID.fromString("8f392d63-8121-4aac-9048-5ae460fee5f8")).get,
//      knowledgeDao.findById(UUID.fromString("278ecbfc-eb2d-457b-9f1d-f24d1e03472a")).get,
//    )
//  )
//
//  courseDao.update(updateCourse)

}
