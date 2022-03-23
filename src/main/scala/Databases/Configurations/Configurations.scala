package Databases.Configurations

import Databases.Dao.{KnowledgeDao, KnowledgeDaoIml}
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

  val knowledgeDao: KnowledgeDao = KnowledgeDaoIml("horizontal")

  println(knowledgeDao.findAll())

  val knowledge = KnowledgeEntity(UUID.randomUUID(), "ЗНАНИЕ1")
  knowledgeDao.insert(knowledge)
  println(knowledgeDao.findById(knowledge.id))
  val update = KnowledgeEntity(knowledge.id, "ЗНАНИЕ!!!!")

}
