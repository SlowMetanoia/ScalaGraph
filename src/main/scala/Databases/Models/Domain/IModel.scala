package Databases.Models.Domain

import java.util.UUID

/**
 * Трейт для моделей
 * */
trait IModel {
  val id: UUID
  val name: String
}


/**
 * Маркерный трейт для ЗУН'ов
 * */
trait IKSA extends IModel