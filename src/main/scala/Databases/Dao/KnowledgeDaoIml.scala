package Databases.Dao
import Databases.Models.Dao.KnowledgeEntity
import scalikejdbc.{NamedDB, scalikejdbcSQLInterpolationImplicitDef}

import java.util.UUID

case class KnowledgeDaoIml(dbName: String) extends KnowledgeDao {
  override def findAll(): Seq[KnowledgeEntity] =
    NamedDB(s"$dbName") readOnly { implicit session =>
      sql"""
        SELECT * FROM KNOWLEDGE
      """.map(knowledge => KnowledgeEntity(UUID.nameUUIDFromBytes(knowledge.bytes("id")), knowledge.string("name"))).collection.apply()
    }

  override def findById(id: UUID): Option[KnowledgeEntity] =
    NamedDB(s"$dbName") readOnly { implicit session =>
      sql"""
        SELECT * FROM KNOWLEDGE
        WHERE id = $id
      """.map(knowledge => KnowledgeEntity(UUID.nameUUIDFromBytes(knowledge.bytes("id")), knowledge.string("name"))).single.apply()
    }

  override def insert(knowledge: KnowledgeEntity): Unit =
    NamedDB(s"$dbName") localTx { implicit session =>
      sql"""
        INSERT INTO KNOWLEDGE (id, name)
        VALUES (${knowledge.id}, ${knowledge.name})
      """.update.apply()
    }


  override def deleteById(id: UUID): Unit =
    NamedDB(s"$dbName") localTx { implicit session =>
      sql"""
        DELETE FROM KNOWLEDGE
        WHERE id = $id
      """.update.apply()
    }

  override def update(knowledge: KnowledgeEntity): Unit =
    NamedDB(s"$dbName") localTx { implicit session =>
      sql"""
        UPDATE KNOWLEDGE
        SET id = ${knowledge.id}, name = ${knowledge.name}
        WHERE id = ${knowledge.id}
      """.update.apply()
    }
}
