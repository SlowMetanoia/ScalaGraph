package Databases.Models.Dao

import java.util.UUID

/**
 * Класс сущностей Skill соответсвующий таблице SKILL в БД
 * Может содержать только поля
 * */
case class SkillEntity(id: UUID, name: String)
