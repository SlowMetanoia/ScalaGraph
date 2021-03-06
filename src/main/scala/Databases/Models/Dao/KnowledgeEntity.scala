package Databases.Models.Dao

import java.util.UUID

/**
 * Класс сущностей Knowledge соответсвующий таблице KNOWLEDGE в БД
 * Слой для работы с БД
 *
 * @see IKSAEntity
 * */
case class KnowledgeEntity(id: UUID, name: String) extends IKSAEntity
