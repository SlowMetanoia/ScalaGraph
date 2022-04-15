package Databases.Dao

import scalikejdbc.ParameterBinderFactory

import java.util.UUID

trait Factory {
  implicit val uuidFactory: ParameterBinderFactory[UUID] = ParameterBinderFactory[UUID] {
    value => (stmt, idx) => stmt.setObject(idx, value)
  }
}
