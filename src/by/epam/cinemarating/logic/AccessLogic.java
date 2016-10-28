package by.epam.cinemarating.logic;

import by.epam.cinemarating.dao.AccessDAO;
import by.epam.cinemarating.database.ConnectionPool;
import by.epam.cinemarating.database.WrapperConnection;
import by.epam.cinemarating.entity.Access;
import by.epam.cinemarating.entity.AccessType;
import by.epam.cinemarating.exception.DAOException;
import by.epam.cinemarating.exception.LogicException;

public class AccessLogic {
	private static final String ERROR_MESSAGE = "Error Message";

	public AccessType findAccessType(long userId, long movieId) throws LogicException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		WrapperConnection connection = connectionPool.takeConnection().orElseThrow(LogicException::new);
		AccessDAO accessDAO = new AccessDAO(connection);
		Access access;
		try {
			access = accessDAO.findAccess(movieId, userId).orElse(new Access(userId, movieId, AccessType.R));
			return access.getAccessType();
		} catch (DAOException e) {
			throw new LogicException(ERROR_MESSAGE, e);
		} finally {
			if (connection != null) {
				connectionPool.returnConnection(connection);
			}
		}
	}

	public void deleteAccesses(long movieId, long exceptUserId) throws LogicException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		WrapperConnection connection = connectionPool.takeConnection().orElseThrow(LogicException::new);
		AccessDAO accessDAO = new AccessDAO(connection);
		try {
			accessDAO.deleteAccesses(movieId, exceptUserId);
		} catch (DAOException e) {
			throw new LogicException(ERROR_MESSAGE, e);
		} finally {
			if (connection != null) {
				connectionPool.returnConnection(connection);
			}
		}
	}

	public void updateAccess(long userId, long movieId) throws LogicException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		WrapperConnection connection = connectionPool.takeConnection().orElseThrow(LogicException::new);
		AccessDAO accessDAO = new AccessDAO(connection);
		try {
			accessDAO.insert(new Access(userId, movieId, AccessType.RW));
		} catch (DAOException e) {
			throw new LogicException(ERROR_MESSAGE, e);
		} finally {
			if (connection != null) {
				connectionPool.returnConnection(connection);
			}
		}
	}
}
