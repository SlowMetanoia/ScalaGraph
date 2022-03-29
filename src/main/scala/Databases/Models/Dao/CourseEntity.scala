package Databases.Models.Dao

import java.util.UUID

/**
 * Класс сущностей Course соответсвующий таблице COURSE в БД
 * Слой для работы с БД
 *
 * @see IEntity
 * */
case class CourseEntity(
                         id: UUID,
                         name: String,
                         inputSkills: Seq[SkillEntity],
                         outputSkills: Seq[SkillEntity],
                         inputAbility: Seq[AbilityEntity],
                         outputAbility: Seq[AbilityEntity],
                         inputKnowledge: Seq[KnowledgeEntity],
                         outputKnowledge: Seq[KnowledgeEntity]
                       ) extends IEntity
