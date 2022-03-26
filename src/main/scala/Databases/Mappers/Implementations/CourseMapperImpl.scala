package Databases.Mappers.Implementations

import Databases.Mappers.Traits.{AbilityMapper, CourseMapper, KnowledgeMapper, SkillMapper}
import Databases.Models.Dao.CourseEntity
import Databases.Models.Domain.Course

case class CourseMapperImpl(
  abilityMapper: AbilityMapper,
  knowledgeMapper: KnowledgeMapper,
  skillMapper: SkillMapper
) extends CourseMapper {
  /**
   * Перевод из CourseEntity в Course
   *
   * @param courseEntity - entity для перевода
   * @return полученная бизнес модель
   */
  override def mapToCourse(courseEntity: CourseEntity): Course =
    Course(
      id = courseEntity.id,
      name = courseEntity.name,

      inputSkills = courseEntity.inputSkills.map(skill =>
        skillMapper.mapToSkill(skill)),

      outputSkills = courseEntity.outputSkills.map(skill =>
        skillMapper.mapToSkill(skill)),

      inputAbility = courseEntity.inputAbility.map(ability =>
        abilityMapper.mapToAbility(ability)),

      outputAbility = courseEntity.outputAbility.map(ability =>
        abilityMapper.mapToAbility(ability)),

      inputKnowledge = courseEntity.inputKnowledge.map(knowledge =>
        knowledgeMapper.mapToKnowledge(knowledge)),

      outputKnowledge = courseEntity.outputKnowledge.map(knowledge =>
        knowledgeMapper.mapToKnowledge(knowledge))
    )

  /**
   * Перевод из Course в CourseEntity
   *
   * @param course - бизнес модель для перевода
   * @return полученная entity
   */
  override def mapToCourseEntity(course: Course): CourseEntity =
    CourseEntity(
      id = course.id,
      name = course.name,

      inputSkills = course.inputSkills.map(skill =>
        skillMapper.mapToSkillEntity(skill)),

      outputSkills = course.outputSkills.map(skill =>
        skillMapper.mapToSkillEntity(skill)),

      inputAbility = course.inputAbility.map(ability =>
        abilityMapper.mapToAbilityEntity(ability)),

      outputAbility = course.outputAbility.map(ability =>
        abilityMapper.mapToAbilityEntity(ability)),

      inputKnowledge = course.inputKnowledge.map(knowledge =>
        knowledgeMapper.mapToKnowledgeEntity(knowledge)),

      outputKnowledge = course.outputKnowledge.map(knowledge =>
        knowledgeMapper.mapToKnowledgeEntity(knowledge))
    )
}
