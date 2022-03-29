package Databases.Configurations

import scalikejdbc.interpolation.SQLSyntax

import scala.language.implicitConversions

sealed class SQLUnsafeSugar(val sqlSyntax:SQLSyntax)
object SQLUnsafeSugar {
  def apply(str:String): SQLSyntax = SQLSyntax.createUnsafely(str)
}

object Id extends SQLUnsafeSugar(SQLUnsafeSugar("id"))
object Name extends SQLUnsafeSugar(SQLUnsafeSugar("name"))