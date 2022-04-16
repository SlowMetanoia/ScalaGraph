package Databases.Models.Dao

import Databases.Configurations.{ASC, Id}
import Databases.Dao.Factory
import scalikejdbc._

import java.util.UUID

/**
 * Класс сущностей Skill соответсвующий таблице SKILL в БД
 * Слой для работы с БД
 *
 * @see IKSAEntity
 * */
case class SkillEntity(id: UUID, name: String) extends IKSAEntity

object SkillEntity extends SQLSyntaxSupport[SkillEntity] with Factory {
  override val tableName = "SKILL"

  val s: QuerySQLSyntaxProvider[SQLSyntaxSupport[SkillEntity], SkillEntity] = SkillEntity.syntax("s")
  val sc: ColumnName[SkillEntity] = SkillEntity.column

  def apply(s: ResultName[SkillEntity])(rs: WrappedResultSet): SkillEntity =
    new SkillEntity(
      id = UUID.fromString(rs.get(s.id)),
      name = rs.string(s.name)
    )

  def findById(id: UUID): Option[SkillEntity] =
    DB readOnly { implicit session =>
      withSQL {
        select.from(SkillEntity as s)
          .where.eq(s.id, id)
      }.map(SkillEntity(s.resultName)).single.apply()
    }

  def findAll(limit: Int = 100,
              offset: Int = 0,
              orderBy: SQLSyntax = Id.value,
              sort: SQLSyntax = ASC.value): Seq[SkillEntity] = {
    DB readOnly { implicit session =>
      withSQL {
        select.all(s).from(SkillEntity as s)
          .orderBy(orderBy).append(sort)
          .limit(limit)
          .offset(offset)
      }.map(SkillEntity(s.resultName)).list.apply()
    }
  }

  def insert(skill: SkillEntity): Unit =
    DB autoCommit { implicit session =>
      withSQL {
        insertInto(SkillEntity)
          .namedValues(
            sc.id -> skill.id,
            sc.name -> skill.name
          )
      }.update.apply()
    }

  def deleteById(id: UUID): Unit =
    DB autoCommit {implicit session =>
      withSQL {
        deleteFrom(SkillEntity)
          .where.eq(sc.id, id)
      }.update.apply()
    }

  def update(skill: SkillEntity): Unit =
    DB localTx {implicit session =>
      withSQL {
        QueryDSL.update(SkillEntity)
          .set(
            sc.name -> skill.name
          )
      }.update.apply()
    }


  //TODO: подружить n БД  и работать через NamedDB

}

object Main extends App {
  Class.forName("org.postgresql.Driver")
  ConnectionPool.singleton(
    url = "jdbc:postgresql://ec2-52-208-185-143.eu-west-1.compute.amazonaws.com/d7ib2dfkt0eiju",
    user = "tmdbifljbdvcwh",
    password = "2cd7bf97ba510c170da544e0ed89289ac3d011dc1220ffe4c53ce83f11fbec3e")

//  println(SkillEntity.findById(UUID.fromString("79819c4b-1cbc-4096-bd36-907f3efa3758")))
//  println(SkillEntity.findAll())


//  val uuid = UUID.randomUUID()
//  SkillEntity.insert(SkillEntity(uuid, "НАВЫК3000"))

  //1500ac90-4c94-4dd5-94ca-cb0308ed1e2b
//  println(SkillEntity.findById(uuid))

//  println(SkillEntity.findById(UUID.fromString("1500ac90-4c94-4dd5-94ca-cb0308ed1e2b")))
//  SkillEntity.deleteById(UUID.fromString("1500ac90-4c94-4dd5-94ca-cb0308ed1e2b"))
//  println(SkillEntity.findById(UUID.fromString("1500ac90-4c94-4dd5-94ca-cb0308ed1e2b")))

  val skill = SkillEntity.findById(UUID.fromString("79819c4b-1cbc-4096-bd36-907f3efa3758")).get
  println(skill)

  val newSkill = SkillEntity(skill.id, name = "НАВЫК2500")
  SkillEntity.update(newSkill)
  println(SkillEntity.findById(skill.id).get)








}