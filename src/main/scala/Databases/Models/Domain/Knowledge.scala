package Databases.Models.Domain

import java.util.UUID

/**
 * Класс только
 * В отлчии от толькоEntity может содержать логику и дополнительную логику
 * Слой для осуществелния логики
 * */
case class Knowledge(id: UUID, name: String) extends IKSA
