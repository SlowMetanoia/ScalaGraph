package Databases.Models.Dao

import java.util.UUID

/**
 * Трейт для Entity классов
 * */
trait IEntity {
  val id: UUID
  val name: String
}

/**
 * Маркерный трейт для ЗУН'ов
 */
trait IKSAEntity extends IEntity