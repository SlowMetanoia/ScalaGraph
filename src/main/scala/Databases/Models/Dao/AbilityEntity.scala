package Databases.Models.Dao

import java.util.UUID

/**
 * Класс сущностей Ability соответсвующий таблице ABILITY в БД
 * Может содержать только поля
 * */
case class AbilityEntity(id: UUID, name: String)
