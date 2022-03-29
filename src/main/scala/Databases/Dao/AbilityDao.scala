package Databases.Dao

import Databases.Configurations.{ASC, Id}
import Databases.Models.Dao.AbilityEntity
import scalikejdbc.interpolation.SQLSyntax
import scalikejdbc.{NamedDB, scalikejdbcSQLInterpolationImplicitDef}

import java.util.UUID
import scala.language.implicitConversions

/**
 * Реализация AbilityDao
 * Выполнение SQL скриптов при работе с таблицой Ability и
 * маппинг полученных данных в объекты класса AbilityEntity
 *
 * @see AbilityDao
 * @see AbilityEntity
 * */
case class AbilityDao(dbName: String) extends IAbilityDao {
  /**
   * Выполнение SQL запроса на получение всех записей из таблицы Ability
   *
   * @param limit кол-во записей которые необходимо получить
   * @param offset  отсутуп от начала полученных записей
   * @param orderBy поле по которому необходимо отсортировать записи
   * @param sort - порядок сортировки
   * @return последовательность всех Ability из таблицы
   */
  override def findAll(limit: Int = 100,
                       offset: Int = 0,
                       orderBy: SQLSyntax = Id.value,
                       sort: SQLSyntax = ASC.value): Seq[AbilityEntity] = {
    NamedDB(s"$dbName") readOnly { implicit session =>
      sql"""
        SELECT * FROM ability
        ORDER BY $orderBy $sort
        LIMIT $limit
        OFFSET $offset
      """.map(ability => AbilityEntity(
        UUID.fromString(ability.string("id")),
        ability.string("name"))
      ).collection.apply()
    }
  }

  /**
   * Выполенение SQL запроса на получение конретной записи из таблицы Ability
   * по id
   *
   * @param id Ability которую необходимо получить
   * @return Optional с Ability если такая есть в БД, иначе Option.empty
   */
  override def findById(id: UUID): Option[AbilityEntity] =
    NamedDB(s"$dbName") readOnly { implicit session =>
      sql"""
        SELECT * FROM ABILITY
        WHERE id = $id
      """.map(ability => AbilityEntity(
        UUID.fromString(ability.string("id")),
        ability.string("name"))
      ).single.apply()
    }

  /**
   * Выполнение SQL запроса на вставку новой записи в таблицу Ability
   *
   * @param ability entity которую необходимо вставить в таблицу
   */
  override def insert(ability: AbilityEntity): Unit =
    NamedDB(s"$dbName") localTx { implicit session =>
      sql"""
        INSERT INTO ABILITY (id, name)
        VALUES (${ability.id}, ${ability.name})
      """.update.apply()
    }

  /**
   * Выполенение SQL запроса на удаление записи из таблицы Ability
   *
   * @param id Ability которую необходимо удалить
   */
  override def deleteById(id: UUID): Unit =
    NamedDB(s"$dbName") localTx { implicit session =>
      sql"""
        DELETE FROM ABILITY
        WHERE id = $id
      """.update.apply()
    }

  /**
   * Выполнение SQL запроса на обновление записи в таблице Ability
   *
   * @param ability entity которое будет обновлено
   */
  override def update(ability: AbilityEntity): Unit =
    NamedDB(s"$dbName") localTx { implicit session =>
      sql"""
        UPDATE ABILITY
        SET id = ${ability.id}, name = ${ability.name}
        WHERE id = ${ability.id}
      """.update.apply()
    }
}
