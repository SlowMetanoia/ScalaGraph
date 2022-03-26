package Databases.Models.Domain

import java.util.UUID

/**
 * Класс Skill
 * В отлчии от SkillEntity может содержать логику и дополнительную логику
 * Слой для осуществелния логики
 * */
case class Skill(id: UUID, name: String)
