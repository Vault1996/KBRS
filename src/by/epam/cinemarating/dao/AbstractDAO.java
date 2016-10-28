package by.epam.cinemarating.dao;

import by.epam.cinemarating.database.WrapperConnection;
import by.epam.cinemarating.entity.Entity;
import by.epam.cinemarating.exception.DAOException;

import java.util.List;
import java.util.Optional;

abstract class AbstractDAO<T extends Entity>{
	protected WrapperConnection connection;

	AbstractDAO(WrapperConnection connection) {
		this.connection = connection;
	}

	/**
	 * Retrieves all entities in the system.
	 * @return list of all entities
	 * @throws DAOException if any exceptions occurred on the SQL layer
	 */
	public abstract List<T> findAll() throws DAOException;

	/**
	 * Retrieves an entity with a specific id.
	 * @param id id of the entity to find
	 * @return an entity with the given id
	 * @throws DAOException if any exceptions occurred on the SQL layer
	 */
	public abstract Optional<T> findEntityById(long id) throws DAOException;

	/**
	 * Inserts the entity into the system.
	 * @param entity entity to insert into system
	 * @return true if the entity was inserted, false otherwise
	 * @throws DAOException if any exceptions occurred on the SQL layer
	 */
	public abstract boolean insert(T entity) throws DAOException;

	/**
	 * Deletes the entity with a specific id from the system.
	 * @param id id of the entity to delete
	 * @return true if the entity was deleted, false otherwise
	 * @throws DAOException if any exceptions occurred on the SQL layer
	 */
	public abstract boolean delete(long id) throws DAOException;

	/**
	 * Updates the entity with a specific id.
	 * @param entity - new entity
	 * @return true if the entity was updated, false otherwise
	 * @throws DAOException if any exceptions occurred on the SQL layer
	 */
	public abstract boolean update(T entity) throws DAOException;
}