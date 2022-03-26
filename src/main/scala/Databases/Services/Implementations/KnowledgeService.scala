package Databases.Services.Implementations

import Databases.Models.Domain.Knowledge

import java.util.UUID

/**
 * Трейт сервиса для Knowledge
 * В методах класса сервиса можно добавить логику/валидацию
 * перед работай с entity и БД
 * @see Knowledge
 * */
trait KnowledgeService {
  /**
   * @param limit - кол-во записей которые необходимо получить
   * @param offset - отсутуп от начала полученных записей
   * @param orderBy - поле по которому необходимо отсортировать записи
   * @param sort - порядок сортировки
   * @return последовательность всех Knowledge
   */
  def findAll(limit: Int = 100,
              offset: Int = 0,
              orderBy: String = "id",
              sort: String = "ASC"): Seq[Knowledge]

  /**
   * Получение Knowledge по id
   * @param id Knowledge которую необходимо получить
   * @return Optional с Knowledge если такая есть в БД, иначе Option.empty
   */
  def findById(id: UUID): Option[Knowledge]

  /**
   * Вставка новой Knowledge
   * @param knowledge entity которую необходимо вставить
   */
  def insert(knowledge: Knowledge): Unit

  /**
   * Удаление Knowledge по id
   * @param id Knowledge которую необходимо удалить
   */
  def deleteById(id: UUID): Unit

  /**
   * Обновление Knowledge в таблице
   * @param knowledge entity которое будет обновлено
   */
  def update(knowledge: Knowledge): Unit
}
