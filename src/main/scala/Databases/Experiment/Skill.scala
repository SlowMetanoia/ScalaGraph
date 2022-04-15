package Databases.Experiment

import scalikejdbc._

import java.util.UUID

case class Skill(id: UUID, name: String)

object Skill extends SQLSyntaxSupport[Skill] {


  def apply(s: ResultName[Skill])(rs: WrappedResultSet): Skill =
    new Skill(
      id = UUID.fromString(rs.string(s.id)),
      name = rs.string(s.name))
}

object Main extends App {
  Class.forName("org.postgresql.Driver")
  ConnectionPool.singleton(
    url = "jdbc:postgresql://ec2-52-208-185-143.eu-west-1.compute.amazonaws.com/d7ib2dfkt0eiju",
    user = "tmdbifljbdvcwh",
    password = "2cd7bf97ba510c170da544e0ed89289ac3d011dc1220ffe4c53ce83f11fbec3e")

  val s = Skill.syntax("s")

  def find() = {
    DB localTx { implicit session =>
      withSQL{
        select.from(Skill as s)
          .where.eq(s.name, "Навык23")
      }

    }
  }

  val skill = DB localTx { implicit session =>
    withSQL{
      select.from(Skill as s)
        .where.eq(s.name, "Навык23")
    }.map(Skill(s.resultName)).single.apply()
  }

  println(skill.get)


}
