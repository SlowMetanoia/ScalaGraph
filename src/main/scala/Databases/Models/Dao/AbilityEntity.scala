package Databases.Models.Dao

import java.util.UUID

/**
 * Класс сущностей Ability соответсвующий таблице ABILITY в БД
 * Может содержать только поля
 * Слой для работы с БД
 * */
case class AbilityEntity(id: UUID, name: String)
