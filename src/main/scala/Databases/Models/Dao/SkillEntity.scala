package Databases.Models.Dao

import java.util.UUID

/**
 * Класс сущностей Skill соответсвующий таблице SKILL в БД
 * Может содержать только поля
 * Слой для работы с БД
 * */
case class SkillEntity(id: UUID, name: String)
