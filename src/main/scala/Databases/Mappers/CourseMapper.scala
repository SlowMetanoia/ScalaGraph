package Databases.Mappers

import Databases.Models.Dao.CourseEntity
import Databases.Models.Domain.Course

/**
 * Маппер CourseEntity/Course
 *
 * @see ICourseMapper
 * */
case class CourseMapper(
                         skillMapper: ISkillMapper,
                         abilityMapper: IAbilityMapper,
                         knowledgeMapper: IKnowledgeMapper,
                       ) extends ICourseMapper {
  /**
   * Перевод из CourseEntity в Course
   *
   * @param courseEntity Entity для перевода
   * @return полученная Model
   */
  override def entity2Model(courseEntity: CourseEntity): Course =
    Course(
      id = courseEntity.id,
      name = courseEntity.name,
      inputSkills = courseEntity.inputSkills.map(skillMapper.entity2Model),
      outputSkills = courseEntity.outputSkills.map(skillMapper.entity2Model),
      inputAbility = courseEntity.inputAbility.map(abilityMapper.entity2Model),
      outputAbility = courseEntity.outputAbility.map(abilityMapper.entity2Model),
      inputKnowledge = courseEntity.inputKnowledge.map(knowledgeMapper.entity2Model),
      outputKnowledge = courseEntity.outputKnowledge.map(knowledgeMapper.entity2Model)
    )

  /**
   * Перевод из Course в CourseEntity
   *
   * @param course Model для перевода
   * @return полученная Entity
   */
  override def model2Entity(course: Course): CourseEntity =
    CourseEntity(
      id = course.id,
      name = course.name,
      inputSkills = course.inputSkills.map(skillMapper.model2Entity),
      outputSkills = course.outputSkills.map(skillMapper.model2Entity),
      inputAbility = course.inputAbility.map(abilityMapper.model2Entity),
      outputAbility = course.outputAbility.map(abilityMapper.model2Entity),
      inputKnowledge = course.inputKnowledge.map(knowledgeMapper.model2Entity),
      outputKnowledge = course.outputKnowledge.map(knowledgeMapper.model2Entity)
    )
}
