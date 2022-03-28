package Databases.Mappers.Implementations

import Databases.Mappers.Traits.{AbilityMapper, CourseMapper, KnowledgeMapper, SkillMapper}
import Databases.Models.Dao.CourseEntity
import Databases.Models.Domain.Course

case class CourseMapperImpl(
  skillMapper: SkillMapper,
  abilityMapper: AbilityMapper,
  knowledgeMapper: KnowledgeMapper,
) extends CourseMapper {
  /**
   * Перевод из CourseEntity в Course
   *
   * @param courseEntity entity для перевода
   * @return полученная бизнес модель
   */
  override def mapToCourse(courseEntity: CourseEntity): Course =
    Course(
      id = courseEntity.id,
      name = courseEntity.name,
      inputSkills = courseEntity.inputSkills.map(skillMapper.skillEntity2Skill),
      outputSkills = courseEntity.outputSkills.map(skillMapper.skillEntity2Skill),
      inputAbility = courseEntity.inputAbility.map(abilityMapper.abilityEntity2Ability),
      outputAbility = courseEntity.outputAbility.map(abilityMapper.abilityEntity2Ability),
      inputKnowledge = courseEntity.inputKnowledge.map(knowledgeMapper.knowledgeEntity2Knowledge),
      outputKnowledge = courseEntity.outputKnowledge.map(knowledgeMapper.knowledgeEntity2Knowledge)
    )

  /**
   * Перевод из Course в CourseEntity
   *
   * @param course бизнес модель для перевода
   * @return полученная entity
   */
  override def course2CourseEntity(course: Course): CourseEntity =
    CourseEntity(
      id = course.id,
      name = course.name,
      inputSkills = course.inputSkills.map(skillMapper.skill2SkillEntity),
      outputSkills = course.outputSkills.map(skillMapper.skill2SkillEntity),
      inputAbility = course.inputAbility.map(abilityMapper.ability2AbilityEntity),
      outputAbility = course.outputAbility.map(abilityMapper.ability2AbilityEntity),
      inputKnowledge = course.inputKnowledge.map(knowledgeMapper.knowledge2KnowledgeEntity),
      outputKnowledge = course.outputKnowledge.map(knowledgeMapper.knowledge2KnowledgeEntity)
    )
}
