package by.epam.cinemarating.dao;

import by.epam.cinemarating.database.WrapperConnection;
import by.epam.cinemarating.entity.Ban;
import by.epam.cinemarating.exception.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BanDAO extends AbstractDAO<Ban> {
	private static final String BAN_ID = "ban_id";
	private static final String USER_ID = "user_id";
	private static final String TILL = "till";
	private static final String REASON = "reason";

	private static final String FIND_ALL_BANS = "SELECT ban_id,user_id,till,reason FROM ban";
	private static final String FIND_BAN_BY_ID = "SELECT ban_id,user_id,till,reason FROM ban WHERE ban_id=?";
	private static final String FIND_BAN_BY_USER_ID = "SELECT ban_id,user_id,till,reason FROM ban WHERE user_id=?";

	private static final String INSERT_BAN = "INSERT INTO ban(user_id,till,reason) VALUES(?,?,?)";
	private static final String UPDATE_BAN_BY_ID = "UPDATE ban SET user_id=?,till=?,reason=? WHERE ban_id=?";
	private static final String DELETE_BAN_BY_ID = "DELETE FROM ban WHERE ban_id=?";
	private static final String DELETE_BAN_BY_USER_ID = "DELETE FROM ban WHERE user_id=?";

	public BanDAO(WrapperConnection connection) {
		super(connection);
	}

	@Override
	public List<Ban> findAll() throws DAOException {
		List<Ban> bans = new ArrayList<>();
		try (
				Statement statement = connection.createStatement()
		) {
			ResultSet resultSet = statement.executeQuery(FIND_ALL_BANS);
			while (resultSet.next()) {
				long banId = resultSet.getLong(BAN_ID);
				long userId = resultSet.getLong(USER_ID);
				Timestamp till = resultSet.getTimestamp(TILL);
				String reason = resultSet.getString(REASON);
				bans.add(new Ban(banId, userId, till, reason));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return bans;
	}

	@Override
	public Optional<Ban> findEntityById(long id) throws DAOException {
		Ban ban = null;
		try(
				PreparedStatement statement = connection.prepareStatement(FIND_BAN_BY_ID)
		) {
			statement.setLong(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				long userId = resultSet.getLong(USER_ID);
				Timestamp till = resultSet.getTimestamp(TILL);
				String reason = resultSet.getString(REASON);
				ban = new Ban(id, userId, till, reason);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return Optional.ofNullable(ban);
	}

	/**
	 * Retrieves {@link by.epam.cinemarating.entity.User} with a specific userId.
	 * @param userId id of the User to find
	 * @return an entity with the given id
	 * @throws DAOException if any exceptions occurred on the SQL layer
	 */
	public Optional<Ban> findBanByUserId(long userId) throws DAOException {
		Ban ban = null;
		try(
				PreparedStatement statement = connection.prepareStatement(FIND_BAN_BY_USER_ID)
		) {
			statement.setLong(1, userId);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				Timestamp till = resultSet.getTimestamp(TILL);
				String reason = resultSet.getString(REASON);
				long banId = resultSet.getLong(BAN_ID);
				ban = new Ban(banId, userId, till, reason);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return Optional.ofNullable(ban);
	}

	@Override
	public boolean insert(Ban entity) throws DAOException {
		int result;
		try(
				PreparedStatement statement = connection.prepareStatement(INSERT_BAN)
		) {
			statement.setLong(1, entity.getUserId());
			statement.setTimestamp(2, entity.getTill());
			statement.setString(3, entity.getReason());
			result = statement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return result > 0;
	}

	@Override
	public boolean delete(long id) throws DAOException {
		int result;
		try(
				PreparedStatement statement = connection.prepareStatement(DELETE_BAN_BY_ID)
		) {
			statement.setLong(1, id);
			result = statement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return result > 0;
	}

	@Override
	public boolean update(Ban entity) throws DAOException {
		int result;
		try(
				PreparedStatement statement = connection.prepareStatement(UPDATE_BAN_BY_ID)
		) {
			statement.setLong(1, entity.getUserId());
			statement.setTimestamp(2, entity.getTill());
			statement.setString(3, entity.getReason());
			statement.setLong(4, entity.getBanId());
			result = statement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return result > 0;
	}

	/**
	 * Deletes {@link by.epam.cinemarating.entity.User} with a specific user id from the system.
	 * @param userId id of the User to delete
	 * @return true if the entity was deleted, false otherwise
	 * @throws DAOException if any exceptions occurred on the SQL layer
	 */
	public boolean deleteByUserId(long userId) throws DAOException {
		int result;
		try(
				PreparedStatement statement = connection.prepareStatement(DELETE_BAN_BY_USER_ID)
		) {
			statement.setLong(1, userId);
			result = statement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return result > 0;
	}
}
