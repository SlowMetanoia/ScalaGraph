package Databases.Configurations

import scalikejdbc.interpolation.SQLSyntax

import scala.language.implicitConversions

/**
 * "Импровизированное перечисление"
 * Его наличие обусловленно ограничениями работы ScalikeJDBC
 * и небезопаснотью механизма SQLSyntax.createUnsafely,
 * который подвержен SQL инъекциям
 * */
sealed class SQLSyntaxProvider(val value: SQLSyntax)

object SQLSyntaxProvider {
  def apply(str: String): SQLSyntax = SQLSyntax.createUnsafely(str)
}

/**
 * Названия колонок таблиц из БД
 * */
object Id extends SQLSyntaxProvider(SQLSyntaxProvider("id"))

object Name extends SQLSyntaxProvider(SQLSyntaxProvider("name"))


/**
 * Операторы SQL
 * */
object ASC extends SQLSyntaxProvider(SQLSyntaxProvider("ASC"))

object DESC extends SQLSyntaxProvider(SQLSyntaxProvider("DESC"))

/**
 * Основные таблицы
 * */
object SKILL extends SQLSyntaxProvider(SQLSyntaxProvider("SKILL"))

object ABILITY extends SQLSyntaxProvider(SQLSyntaxProvider("ABILITY"))

object KNOWLEDGE extends SQLSyntaxProvider(SQLSyntaxProvider("KNOWLEDGE"))

object COURSE extends SQLSyntaxProvider(SQLSyntaxProvider("COURSE"))