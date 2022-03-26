package Databases.Models.Dao

import java.util.UUID

/**
 * Класс сущностей Knowledge соответсвующий таблице KNOWLEDGE в БД
 * Может содержать только поля
 * */
case class KnowledgeEntity(id: UUID, name: String)
