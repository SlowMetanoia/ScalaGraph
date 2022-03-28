package Databases.Dao.Implementations

import Databases.Dao.Traits.SkillDao
import Databases.Models.Dao.SkillEntity
import scalikejdbc.{NamedDB, SQLSyntax, scalikejdbcSQLInterpolationImplicitDef}

import java.util.UUID

/**
 * Реализация SkillDao
 * Выполнение SQL скриптов при работе с таблицой Skill и
 * маппинг полученных данных в объекты класса SkillEntity
 *
 * @see SkillDao
 * @see SkillEntity
 * */
case class SkillDaoImpl(dbName: String) extends SkillDao {

  /**
   * Выполнение SQL запроса на получение всех записей из таблицы Skill
   *
   * @param limit кол-во записей которые необходимо получить
   * @param offset отсутуп от начала полученных записей
   * @param orderBy поле по которому необходимо отсортировать записи
   * @param sort порядок сортировки
   * @return последовательность всех Skill из таблицы
   */
  override def findAll(limit: Int = 100,
                       offset: Int = 0,
                       orderBy: String = "id",
                       sort: String = "ASC"): Seq[SkillEntity] = {

    NamedDB(s"$dbName") readOnly { implicit session =>
      sql"""
        SELECT * FROM SKILL
        ORDER BY ${SQLSyntax.createUnsafely(orderBy)} ${SQLSyntax.createUnsafely(sort)}
        LIMIT $limit
        OFFSET $offset
      """.map(skill => SkillEntity(
        UUID.fromString(skill.string("id")),
        skill.string("name"))
      ).collection.apply()
    }
  }

  /**
   * Выполенение SQL запроса на получение конретной записи из таблицы Skill
   * по id
   *
   * @param id Skill которую необходимо получить
   * @return Optional с Skill если такая есть в БД, иначе Option.empty
   */
  override def findById(id: UUID): Option[SkillEntity] =
    NamedDB(s"$dbName") readOnly { implicit session =>
      sql"""
        SELECT * FROM SKILL
        WHERE id = $id
      """.map(skill => SkillEntity(
        UUID.fromString(skill.string("id")),
        skill.string("name"))
      ).single.apply()
    }

  /**
   * Выполнение SQL запроса на вставку новой записи в таблицу Skill
   *
   * @param skill entity которую необходимо вставить в таблицу
   */
  override def insert(skill: SkillEntity): Unit =
    NamedDB(s"$dbName") localTx { implicit session =>
      sql"""
        INSERT INTO SKILL (id, name)
        VALUES (${skill.id}, ${skill.name})
      """.update.apply()
    }

  /**
   * Выполенение SQL запроса на удаление записи из таблицы Skill
   *
   * @param id Skill которую необходимо удалить
   */
  override def deleteById(id: UUID): Unit =
    NamedDB(s"$dbName") localTx { implicit session =>
      sql"""
        DELETE FROM SKILL
        WHERE id = $id
      """.update.apply()
    }

  /**
   * Выполнение SQL запроса на обновление записи в таблице Skill
   *
   * @param skill entity которое будет обновлено
   */
  override def update(skill: SkillEntity): Unit =
    NamedDB(s"$dbName") localTx { implicit session =>
      sql"""
        UPDATE SKILL
        SET id = ${skill.id}, name = ${skill.name}
        WHERE id = ${skill.id}
      """.update.apply()
    }
}
