package Databases.Models.Dao

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


}

object Main extends App {
  Class.forName("org.postgresql.Driver")
  ConnectionPool.singleton(
    url = "jdbc:postgresql://ec2-52-208-185-143.eu-west-1.compute.amazonaws.com/d7ib2dfkt0eiju",
    user = "tmdbifljbdvcwh",
    password = "2cd7bf97ba510c170da544e0ed89289ac3d011dc1220ffe4c53ce83f11fbec3e")

  println(SkillEntity.findById(UUID.fromString("79819c4b-1cbc-4096-bd36-907f3efa3758")))




}