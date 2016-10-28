package by.epam.cinemarating.dao;

import by.epam.cinemarating.database.WrapperConnection;
import by.epam.cinemarating.entity.Token;
import by.epam.cinemarating.exception.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class TokenDAO extends AbstractDAO<Token> {
	private static final String TOKEN = "token";
	private static final String FIND_TOKEN_BY_ID = "SELECT user_id,token " +
			" FROM token WHERE user_id=?";
	private static final String INSERT_TOKEN = "REPLACE INTO token(user_id,token) " +
			"VALUES(?,?)";
	private static final String FIND_TOKEN_BY_ID_AND_TOKEN = "SELECT user_id,token " +
			" FROM token WHERE user_id=? and token=?";

	public TokenDAO(WrapperConnection connection) {
		super(connection);
	}

	/**
	 * Retrieves all entities in the system.
	 *
	 * @return list of all entities
	 * @throws DAOException if any exceptions occurred on the SQL layer
	 */
	@Override
	public List<Token> findAll() throws DAOException {
		return null;
	}

	/**
	 * Retrieves an entity with a specific id.
	 *
	 * @param id id of the entity to find
	 * @return an entity with the given id
	 * @throws DAOException if any exceptions occurred on the SQL layer
	 */
	@Override
	public Optional<Token> findEntityById(long id) throws DAOException {
		Token token1 = null;
		try (
				PreparedStatement statement = connection.prepareStatement(FIND_TOKEN_BY_ID)
		) {
			statement.setLong(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				String token = resultSet.getString(TOKEN);
				token1 = new Token(id, token);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return Optional.ofNullable(token1);
	}

	/**
	 * Inserts the entity into the system.
	 *
	 * @param entity entity to insert into system
	 * @return true if the entity was inserted, false otherwise
	 * @throws DAOException if any exceptions occurred on the SQL layer
	 */
	@Override
	public boolean insert(Token entity) throws DAOException {
		int result;
		try(
				PreparedStatement statement = connection.prepareStatement(INSERT_TOKEN)
		) {
			statement.setLong(1, entity.getUserId());
			statement.setString(2, entity.getToken());
			result = statement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return result > 0;
	}

	/**
	 * Deletes the entity with a specific id from the system.
	 *
	 * @param id id of the entity to delete
	 * @return true if the entity was deleted, false otherwise
	 * @throws DAOException if any exceptions occurred on the SQL layer
	 */
	@Override
	public boolean delete(long id) throws DAOException {
		return false;
	}

	/**
	 * Updates the entity with a specific id.
	 *
	 * @param entity - new entity
	 * @return true if the entity was updated, false otherwise
	 * @throws DAOException if any exceptions occurred on the SQL layer
	 */
	@Override
	public boolean update(Token entity) throws DAOException {
		return false;
	}

	public boolean findEntity(long userId, String token) throws DAOException {
		Token token1 = null;
		try (
				PreparedStatement statement = connection.prepareStatement(FIND_TOKEN_BY_ID_AND_TOKEN)
		) {
			statement.setLong(1, userId);
			statement.setString(2, token);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				token1 = new Token(userId, token);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return Optional.ofNullable(token1).isPresent();
	}
}
