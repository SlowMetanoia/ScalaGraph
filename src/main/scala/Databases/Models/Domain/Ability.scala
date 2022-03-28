package Databases.Models.Domain

import java.util.UUID

/**
 * Класс Ability
 * В отлчии от AbilityEntity может содержать логику и дополнительную логику
 * Слой для осуществелния логики
 * */
case class Ability(id: UUID, name: String) extends IKSA
