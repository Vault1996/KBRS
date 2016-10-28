package by.epam.cinemarating.dao;

import by.epam.cinemarating.database.WrapperConnection;
import by.epam.cinemarating.entity.Access;
import by.epam.cinemarating.entity.AccessType;
import by.epam.cinemarating.exception.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccessDAO extends AbstractDAO<Access> {
	private static final String MOVIE_ID = "movie_id";
	private static final String USER_ID = "user_id";
	private static final String ACCESS = "access";

	private static final String FIND_ALL_ACCESSES = "SELECT movie_id,user_id,access " +
			"FROM access";
	private static final String FIND_ACCESS_BY_MOVIE_ID = "SELECT movie_id,user_id,access " +
			"FROM access WHERE movie_id=?";
	private static final String FIND_ACCESS_BY_USER_ID = "SELECT movie_id,user_id,access " +
			"FROM access WHERE user_id=?";
	private static final String FIND_ACCESS = "SELECT movie_id,user_id,access " +
			"FROM access WHERE movie_id=? AND user_id=?";

	private static final String INSERT_ACCESS = "INSERT INTO access(movie_id,user_id,access) " +
			"VALUES(?,?,?)";
	private static final String UPDATE_ACCESS = "UPDATE access SET access=? " +
			"WHERE movie_id=? AND user_id=?";
	private static final String DELETE_ACCESS_BY_MOVIE_ID = "DELETE FROM access WHERE movie_id=?";
	private static final String DELETE_ACCESS_BY_USER_ID = "DELETE FROM access WHERE user_id=?";
	private static final String DELETE_ACCESS = "DELETE FROM access WHERE movie_id=? AND user_id=?";
	private static final String DELETE_ACCESSES = "DELETE FROM access where movie_id=? and user_id!=?";


	public AccessDAO(WrapperConnection connection) {
		super(connection);
	}

	@Override
	public List<Access> findAll() throws DAOException {
		List<Access> accesses = new ArrayList<>();
		try (
				Statement statement = connection.createStatement()
		) {
			ResultSet resultSet = statement.executeQuery(FIND_ALL_ACCESSES);
			while (resultSet.next()) {
				long userId = resultSet.getLong(USER_ID);
				String access = resultSet.getString(ACCESS);
				long movieId = resultSet.getLong(MOVIE_ID);
				accesses.add(new Access(userId, movieId, AccessType.valueOf(access.toUpperCase())));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return accesses;
	}

	@Override
	public Optional<Access> findEntityById(long id) throws DAOException {
		return Optional.empty();
	}

	/**
	 * Retrieves all ratings to the specific movie by movie id
	 * @param movieId id of the movie to find
	 * @return all ratings to the specific movie
	 * @throws DAOException if any exceptions occurred on the SQL layer
	 */
	public List<Access> findRatingsByMovieId(long movieId) throws DAOException {
		List<Access> accessList = new ArrayList<>();
		try (
				PreparedStatement statement = connection.prepareStatement(FIND_ACCESS_BY_MOVIE_ID)
		) {
			statement.setLong(1, movieId);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				String accessField = resultSet.getString(ACCESS);
				long userId = resultSet.getLong(USER_ID);
				accessList.add(new Access(userId, movieId, AccessType.valueOf(accessField.toUpperCase())));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return accessList;
	}

	/**
	 * Retrieves all ratings of the specific user by user id
	 * @param userId id of the user to find
	 * @return all ratings of the specific user
	 * @throws DAOException if any exceptions occurred on the SQL layer
	 */
	public List<Access> findAccessesByUserId(long userId) throws DAOException {
		List<Access> accessList = new ArrayList<>();
		try (
				PreparedStatement statement = connection.prepareStatement(FIND_ACCESS_BY_USER_ID)
		) {
			statement.setLong(1, userId);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				String accessField = resultSet.getString(ACCESS);
				long userIdField = resultSet.getLong(USER_ID);
				long movieId = resultSet.getLong(MOVIE_ID);
				accessList.add(new Access(userIdField, movieId, AccessType.valueOf(accessField.toUpperCase())));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return accessList;
	}

	/**
	 * Retrieves rating to the specific movie rated by specific user
	 * @param movieId id of the movie to find
	 * @param userId id of the user to find
	 * @return rating to the specific movie rated by specific user
	 * @throws DAOException if any exceptions occurred on the SQL layer
	 */
	public Optional<Access> findAccess(long movieId, long userId) throws DAOException {
		Access access = null;
		try (
				PreparedStatement statement = connection.prepareStatement(FIND_ACCESS)
		) {
			statement.setLong(1, movieId);
			statement.setLong(2, userId);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				long userIdField = resultSet.getLong(USER_ID);
				long movieIdField = resultSet.getLong(MOVIE_ID);
				String accessField = resultSet.getString(ACCESS);
				access = new Access(userIdField, movieIdField, AccessType.valueOf(accessField.toUpperCase()));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return Optional.ofNullable(access);
	}

	@Override
	public boolean insert(Access entity) throws DAOException {
		int result;
		try (
				PreparedStatement statement = connection.prepareStatement(INSERT_ACCESS)
		) {
			statement.setLong(1, entity.getMovieId());
			statement.setLong(2, entity.getUserId());
			statement.setString(3, entity.getAccessType().toString());
			result = statement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return result > 0;
	}

	@Override
	public boolean delete(long id) throws DAOException {
		return false;
	}

	/**
	 * Deletes all ratings to movie.
	 * @param movieId id of the movie to delete
	 * @return true if the entity was deleted, false otherwise
	 * @throws DAOException if any exceptions occurred on the SQL layer
	 */
	public boolean deleteRatingsByMovieId(long movieId) throws DAOException {
		int result;
		try(
				PreparedStatement statement = connection.prepareStatement(DELETE_ACCESS_BY_MOVIE_ID)
		) {
			statement.setLong(1, movieId);
			result = statement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return result > 0;
	}

	/**
	 * Deletes all ratings of user.
	 * @param userId id of the user to delete
	 * @return true if the entity was deleted, false otherwise
	 * @throws DAOException if any exceptions occurred on the SQL layer
	 */
	public boolean deleteRatingsByUserId(long userId) throws DAOException {
		int result;
		try(
				PreparedStatement statement = connection.prepareStatement(DELETE_ACCESS_BY_USER_ID)
		) {
			statement.setLong(1, userId);
			result = statement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return result > 0;
	}

	/**
	 * Deletes rating to movie rated by user.
	 * @param movieId id of the movie to delete
	 * @param userId id of the user to delete
	 * @return true if the entity was deleted, false otherwise
	 * @throws DAOException if any exceptions occurred on the SQL layer
	 */
	public boolean deleteAccess(long movieId, long userId) throws DAOException {
		int result;
		try(
				PreparedStatement statement = connection.prepareStatement(DELETE_ACCESS)
		) {
			statement.setLong(1, movieId);
			statement.setLong(2, userId);
			result = statement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return result > 0;
	}

	@Override
	public boolean update(Access entity) throws DAOException {
		int result;
		try(
				PreparedStatement statement = connection.prepareStatement(UPDATE_ACCESS)
		) {
			statement.setString(1, entity.getAccessType().toString());
			statement.setLong(2, entity.getMovieId());
			statement.setLong(3, entity.getUserId());
			result = statement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return result > 0;
	}

	public boolean deleteAccesses(long movieId, long exceptUserId) throws DAOException {
		int result;
		try(
				PreparedStatement statement = connection.prepareStatement(DELETE_ACCESSES)
		) {
			statement.setLong(1, movieId);
			statement.setLong(2, exceptUserId);
			result = statement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return result > 0;
	}
}
