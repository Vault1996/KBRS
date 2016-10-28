package by.epam.cinemarating.dao;

import by.epam.cinemarating.database.WrapperConnection;
import by.epam.cinemarating.entity.BanMessage;
import by.epam.cinemarating.exception.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BanMessageDAO extends AbstractDAO<BanMessage>{
	private static final String BAN_ID = "ban_id";
	private static final String MESSAGE = "message";

	private static final String FIND_ALL_BAN_MESSAGES = "SELECT ban_id,message FROM ban_message";
	private static final String FIND_BAN_MESSAGE_BY_ID = "SELECT ban_id,message FROM ban_message WHERE ban_id=?";

	private static final String INSERT_BAN_MESSAGE = "INSERT INTO ban_message(ban_id,message) VALUES(?,?)";
	private static final String UPDATE_BAN_MESSAGE_BY_ID = "UPDATE ban_message SET message=? WHERE ban_id=?";
	private static final String DELETE_BAN_MESSAGE_BY_ID = "DELETE FROM ban_message WHERE ban_id=?";
	private static final String DELETE_ALL_BAN_MESSAGES = "DELETE FROM ban_message";


	public BanMessageDAO(WrapperConnection connection) {
		super(connection);
	}

	@Override
	public List<BanMessage> findAll() throws DAOException {
		List<BanMessage> banMessages = new ArrayList<>();
		try (
				Statement statement = connection.createStatement()
		) {
			ResultSet resultSet = statement.executeQuery(FIND_ALL_BAN_MESSAGES);
			while (resultSet.next()) {
				long banId = resultSet.getLong(BAN_ID);
				String message = resultSet.getString(MESSAGE);
				banMessages.add(new BanMessage(banId, message));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return banMessages;
	}

	@Override
	public Optional<BanMessage> findEntityById(long id) throws DAOException {
		BanMessage banMessage = null;
		try(
				PreparedStatement statement = connection.prepareStatement(FIND_BAN_MESSAGE_BY_ID)
		) {
			statement.setLong(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				String message = resultSet.getString(MESSAGE);
				banMessage = new BanMessage(id, message);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return Optional.ofNullable(banMessage);
	}

	@Override
	public boolean insert(BanMessage entity) throws DAOException {
		int result;
		try(
				PreparedStatement statement = connection.prepareStatement(INSERT_BAN_MESSAGE)
		) {
			statement.setLong(1, entity.getBanId());
			statement.setString(2, entity.getMessage());
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
				PreparedStatement statement = connection.prepareStatement(DELETE_BAN_MESSAGE_BY_ID)
		) {
			statement.setLong(1, id);
			result = statement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return result > 0;
	}

	/**
	 * Deletes all entities from the system.
	 * @return true if the entity was deleted, false otherwise
	 * @throws DAOException if any exceptions occurred on the SQL layer
	 */
	public boolean deleteAllBanMessages() throws DAOException {
		int result;
		try(
				Statement statement = connection.createStatement()
		) {
			result = statement.executeUpdate(DELETE_ALL_BAN_MESSAGES);
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return result > 0;
	}

	@Override
	public boolean update(BanMessage entity) throws DAOException {
		int result;
		try(
				PreparedStatement statement = connection.prepareStatement(UPDATE_BAN_MESSAGE_BY_ID)
		) {
			statement.setString(1, entity.getMessage());
			statement.setLong(2, entity.getBanId());
			result = statement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return result > 0;
	}
}
