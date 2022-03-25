package Databases.Dao.Implementations

import Databases.Dao.Traits.AbilityDao
import Databases.Models.Dao.AbilityEntity
import scalikejdbc.{NamedDB, scalikejdbcSQLInterpolationImplicitDef}

import java.util.UUID

case class AbilityDaoImpl(dbName: String) extends AbilityDao {
  override def findAll(): Seq[AbilityEntity] =
    NamedDB(s"$dbName") readOnly { implicit session =>
      sql"""
        SELECT * FROM ABILITY
      """.map(ability => AbilityEntity(UUID.fromString(ability.string("id")), ability.string("name"))).collection.apply()
    }

  override def findById(id: UUID): Option[AbilityEntity] =
    NamedDB(s"$dbName") readOnly { implicit session =>
      sql"""
        SELECT * FROM ABILITY
        WHERE id = $id
      """.map(ability => AbilityEntity(UUID.fromString(ability.string("id")), ability.string("name"))).single.apply()
    }

  override def insert(ability: AbilityEntity): Unit =
    NamedDB(s"$dbName") localTx { implicit session =>
      sql"""
        INSERT INTO ABILITY (id, name)
        VALUES (${ability.id}, ${ability.name})
      """.update.apply()
    }

  override def deleteById(id: UUID): Unit =
    NamedDB(s"$dbName") localTx { implicit session =>
      sql"""
        DELETE FROM ABILITY
        WHERE id = $id
      """.update.apply()
    }

  override def update(ability: AbilityEntity): Unit =
    NamedDB(s"$dbName") localTx { implicit session =>
      sql"""
        UPDATE ABILITY
        SET id = ${ability.id}, name = ${ability.name}
        WHERE id = ${ability.id}
      """.update.apply()
    }
}
