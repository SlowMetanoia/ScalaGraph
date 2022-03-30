package Databases.Models.Dao

import java.util.UUID

/**
 * Класс сущностей Ability соответсвующий таблице ABILITY в БД
 * Слой для работы с БД
 *
 * @see IKSAEntity
 * */
case class AbilityEntity(id: UUID, name: String) extends IKSAEntity