package Databases.Dao.Implementations

import Databases.Dao.Traits.KnowledgeDao
import Databases.Models.Dao.KnowledgeEntity
import scalikejdbc.{NamedDB, scalikejdbcSQLInterpolationImplicitDef}

import java.util.UUID

/**
 * Реализация KnowledgeDao
 * Выполнение SQL скриптов при работе с таблицой Knowledge и
 * маппинг полученных данных в объекты класса KnowledgeEntity
 *
 * @see KnowledgeDao
 * @see KnowledgeEntity
 * */
case class KnowledgeDaoImpl(dbName: String) extends KnowledgeDao {

  /**
   * Выполнение SQL запроса на получение всех записей из таблицы Knowledge
   *
   * @param limit   - кол-во записей которые необходимо получить
   * @param offset  - отсутуп от начала полученных записей
   * @param orderBy - поле по которому необходимо отсортировать записи
   * @param sort    - порядок сортировки
   * @return последовательность всех Knowledge из таблицы
   */
  override def findAll(limit: Int = 100,
                       offset: Int = 0,
                       orderBy: String = "id",
                       sort: String = "ASC"): Seq[KnowledgeEntity] = {

    val order = orderBy match {
      case "id" => sqls"id"
      case "name" => sqls"name"
    }

    val sortType = sort match {
      case "ASC" => sqls"asc"
      case "DESC" => sqls"desc"
    }

    NamedDB(s"$dbName") readOnly { implicit session =>
      sql"""
        SELECT * FROM knowledge
        ORDER BY $order $sortType
        LIMIT $limit
        OFFSET $offset
      """.map(knowledge => KnowledgeEntity(
        UUID.fromString(knowledge.string("id")),
        knowledge.string("name"))
      ).collection.apply()
    }
  }

  /**
   * Выполенение SQL запроса на получение конретной записи из таблицы Knowledge
   * по id
   *
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
   *
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
   *
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
   *
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
