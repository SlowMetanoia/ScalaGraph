package Databases.Models.Dao

import java.util.UUID

/**
 * Класс сущностей Knowledge соответсвующий таблице KNOWLEDGE в БД
 * Может содержать только поля
 * Слой для работы с БД
 * */
case class KnowledgeEntity(id: UUID, name: String)
