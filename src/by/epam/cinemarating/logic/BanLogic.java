package by.epam.cinemarating.logic;

import by.epam.cinemarating.dao.BanDAO;
import by.epam.cinemarating.dao.BanMessageDAO;
import by.epam.cinemarating.exception.DAOException;
import by.epam.cinemarating.dao.UserDAO;
import by.epam.cinemarating.database.ConnectionPool;
import by.epam.cinemarating.database.WrapperConnection;
import by.epam.cinemarating.entity.Ban;
import by.epam.cinemarating.entity.BanMessage;
import by.epam.cinemarating.entity.BanMessageInfo;
import by.epam.cinemarating.entity.User;
import by.epam.cinemarating.exception.LogicException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BanLogic {
	private static final String ERROR_MESSAGE = "Problem in Ban Logic";

	/**
	 * Finds Ban by user id
	 * @param userId id of user to watch bans
	 * @return Ban of user if exists and empty optional otherwise
	 * @throws LogicException
	 */
	public Optional<Ban> findBanByUserId(long userId) throws LogicException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		WrapperConnection connection = connectionPool.takeConnection().orElseThrow(LogicException::new);
		BanDAO banDAO = new BanDAO(connection);
		Optional<Ban> banOptional;
		try {
			banOptional = banDAO.findBanByUserId(userId);
			if (banOptional.isPresent()) {
				Ban ban = banOptional.get();
				// if ban passed
				if (ban.getTill().before(new Timestamp(System.currentTimeMillis()))) {
					banDAO.delete(ban.getBanId());
					return Optional.empty();
				}
			}
			return banOptional;
		} catch (DAOException e) {
			throw new LogicException(ERROR_MESSAGE, e);
		} finally {
			if (connection != null) {
				connectionPool.returnConnection(connection);
			}
		}
	}

	/**
	 * Leave message to administrator about your ban
	 * @param banId id of the ban to user
	 * @param banMessage message to admin
	 * @return true if message leaved and false otherwise
	 * @throws LogicException
	 */
	public boolean leaveMessage(long banId, String banMessage) throws LogicException{
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		WrapperConnection connection = connectionPool.takeConnection().orElseThrow(LogicException::new);
		BanMessageDAO banMessageDAO = new BanMessageDAO(connection);
		try {
			Optional<BanMessage> banMessageOptional = banMessageDAO.findEntityById(banId);
			if (banMessageOptional.isPresent()) {
				return false;
			} else {
				banMessageDAO.insert(new BanMessage(banId, banMessage));
				return true;
			}
		} catch (DAOException e) {
			throw new LogicException(ERROR_MESSAGE, e);
		} finally {
			if (connection != null) {
				connectionPool.returnConnection(connection);
			}
		}
	}

	/**
	 * Finds ban by ban id
	 * @param banId id of ban to find
	 * @return Ban if exists of empty optional otherwise
	 * @throws LogicException
	 */
	public Optional<Ban> findBanByBanId(long banId) throws LogicException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		WrapperConnection connection = connectionPool.takeConnection().orElseThrow(LogicException::new);
		BanDAO banDAO = new BanDAO(connection);
		Optional<Ban> banOptional;
		try {
			banOptional = banDAO.findEntityById(banId);
			if (banOptional.isPresent()) {
				Ban ban = banOptional.get();
				// if ban passed
				if (ban.getTill().before(new Timestamp(System.currentTimeMillis()))) {
					banDAO.delete(ban.getBanId());
					return Optional.empty();
				}
			}
			return banOptional;
		} catch (DAOException e) {
			throw new LogicException(ERROR_MESSAGE, e);
		} finally {
			if (connection != null) {
				connectionPool.returnConnection(connection);
			}
		}
	}

	/**
	 * Finds ban message by id
	 * @param banId id of ban
	 * @return Ban Message found
	 * @throws LogicException
	 */
	public BanMessage findBanMessageById(long banId) throws LogicException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		WrapperConnection connection = connectionPool.takeConnection().orElseThrow(LogicException::new);
		BanMessageDAO banMessageDAO = new BanMessageDAO(connection);
		Optional<BanMessage> banOptional;
		try {
			banOptional = banMessageDAO.findEntityById(banId);
			return banOptional.orElse(null);
		} catch (DAOException e) {
			throw new LogicException(ERROR_MESSAGE, e);
		} finally {
			if (connection != null) {
				connectionPool.returnConnection(connection);
			}
		}
	}

	/**
	 * Finds all Ban messages
	 * @return All ban messages
	 * @throws LogicException
	 */
	public List<BanMessageInfo> findAllBanMessages() throws LogicException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		WrapperConnection connection = connectionPool.takeConnection().orElseThrow(LogicException::new);
		BanMessageDAO banMessageDAO = new BanMessageDAO(connection);
		BanDAO banDAO = new BanDAO(connection);
		UserDAO userDAO = new UserDAO(connection);
		List<BanMessageInfo> banMessageInfoList = new ArrayList<>();
		try {
			List<BanMessage> banMessageList = banMessageDAO.findAll();
			for (BanMessage banMessage : banMessageList	) {
				Ban ban = banDAO.findEntityById(banMessage.getBanId()).orElse(new Ban());
				User user = userDAO.findEntityById(ban.getUserId()).orElse(new User());
				banMessageInfoList.add(new BanMessageInfo(user, ban, banMessage));
			}
			return banMessageInfoList;
		} catch (DAOException e) {
			throw new LogicException(ERROR_MESSAGE, e);
		} finally {
			if (connection != null) {
				connectionPool.returnConnection(connection);
			}
		}
	}

	/**
	 * Deletes all ban messages
	 * @throws LogicException
	 */
	public void deleteAllBanMessages() throws LogicException{
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		WrapperConnection connection = connectionPool.takeConnection().orElseThrow(LogicException::new);
		BanMessageDAO banMessageDAO = new BanMessageDAO(connection);
		try {
			banMessageDAO.deleteAllBanMessages();
		} catch (DAOException e) {
			throw new LogicException(ERROR_MESSAGE, e);
		} finally {
			if (connection != null) {
				connectionPool.returnConnection(connection);
			}
		}
	}

	/**
	 * Give a ban to user
	 * @param ban ban information
	 * @throws LogicException
	 */
	public void banUser(Ban ban) throws LogicException{
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		WrapperConnection connection = connectionPool.takeConnection().orElseThrow(LogicException::new);
		BanDAO banDAO = new BanDAO(connection);
		try {
			banDAO.insert(ban);
		} catch (DAOException e) {
			throw new LogicException(ERROR_MESSAGE, e);
		} finally {
			if (connection != null) {
				connectionPool.returnConnection(connection);
			}
		}
	}

	/**
	 * Unban user
	 * @param userId id of user to unban
	 * @throws LogicException
	 */
	public void unbanUser(long userId) throws LogicException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		WrapperConnection connection = connectionPool.takeConnection().orElseThrow(LogicException::new);
		BanDAO banDAO = new BanDAO(connection);
		try {
			banDAO.deleteByUserId(userId);
		} catch (DAOException e) {
			throw new LogicException(ERROR_MESSAGE, e);
		} finally {
			if (connection != null) {
				connectionPool.returnConnection(connection);
			}
		}
	}
}
