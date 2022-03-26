package Databases.Dao.Implementations

import Databases.Dao.Traits.KnowledgeDao
import Databases.Models.Dao.KnowledgeEntity
import scalikejdbc.{NamedDB, scalikejdbcSQLInterpolationImplicitDef}

import java.util.UUID

/**
 * Реализация KnowledgeDao
 * Выполнение SQL скриптов при работе с таблицой Knowledge и
 * маппинг полученных данных в объекты класса KnowledgeEntity
 * @see KnowledgeDao
 * @see KnowledgeEntity
 * */
case class KnowledgeDaoImpl(dbName: String) extends KnowledgeDao {

  /**
   * Выполнение SQL запроса на получение всех записей из таблицы Knowledge
   * @return последовательность всех Knowledge из таблицы
   */
  override def findAll(): Seq[KnowledgeEntity] =
    NamedDB(s"$dbName") readOnly { implicit session =>
      sql"""
        SELECT * FROM knowledge
      """.map(knowledge => KnowledgeEntity(
        UUID.fromString(knowledge.string("id")),
        knowledge.string("name"))
      ).collection.apply()
    }

  /**
   * Выполенение SQL запроса на получение конретной записи из таблицы Knowledge
   * по id
   * @param id Knowledge которую необходимо получить
   * @return Optional с Knowledge если такая есть в БД, иначе Option.empty
   */
  override def findById(id: UUID): Option[KnowledgeEntity] =
    NamedDB(s"$dbName") readOnly { implicit session =>
      sql"""
        SELECT * FROM knowledge
        WHERE id = $id
      """.map(knowledge => KnowledgeEntity(
        UUID.fromString(knowledge.string("id")),
        knowledge.string("name"))
      ).single.apply()
    }

  /**
   * Выполнение SQL запроса на вставку новой записи в таблицу Knowledge
   * @param knowledge entity которую необходимо вставить в таблицу
   */
  override def insert(knowledge: KnowledgeEntity): Unit =
    NamedDB(s"$dbName") localTx { implicit session =>
      sql"""
        INSERT INTO knowledge (id, name)
        VALUES (${knowledge.id}, ${knowledge.name})
      """.update.apply()
    }

  /**
   * Выполенение SQL запроса на удаление записи из таблицы Knowledge
   * @param id Knowledge которую необходимо удалить
   */
  override def deleteById(id: UUID): Unit =
    NamedDB(s"$dbName") localTx { implicit session =>
      sql"""
        DELETE FROM knowledge
        WHERE id = $id
      """.update.apply()
    }

  /**
   * Выполнение SQL запроса на обновление записи в таблице Knowledge
   * @param knowledge entity которое будет обновлено
   */
  override def update(knowledge: KnowledgeEntity): Unit =
    NamedDB(s"$dbName") localTx { implicit session =>
      sql"""
        UPDATE knowledge
        SET id = ${knowledge.id}, name = ${knowledge.name}
        WHERE id = ${knowledge.id}
      """.update.apply()
    }
}
