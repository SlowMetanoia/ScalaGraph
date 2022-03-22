package Cousrses.Configurations

import Cousrses.Models.Dao.{SkillDao, SkillDaoIml, SkillEntity}
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

  val skillDAO: SkillDao = SkillDaoIml("horizontal")

//  println(skillDAO.findById(UUID.fromString("5986dd97-f6b8-4642-b8e7-8c45589c3319")).get)
//  println(skillDAO.findAll())
  val skill = SkillEntity(
    UUID.randomUUID(),
    "навык1"
  )

  skillDAO.insert(skill)
  println(skillDAO.findById(skill.id))

}
