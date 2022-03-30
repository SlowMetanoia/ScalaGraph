package Databases.Models.Dao

import java.util.UUID

/**
 * Класс сущностей Skill соответсвующий таблице SKILL в БД
 * Слой для работы с БД
 *
 * @see IKSAEntity
 * */
case class SkillEntity(id: UUID, name: String) extends IKSAEntity
