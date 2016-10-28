package by.epam.cinemarating.logic;

import by.epam.cinemarating.exception.DAOException;
import by.epam.cinemarating.dao.UserDAO;
import by.epam.cinemarating.database.ConnectionPool;
import by.epam.cinemarating.database.WrapperConnection;
import by.epam.cinemarating.entity.User;
import by.epam.cinemarating.exception.LogicException;

public class EditProfileLogic {
	private static final String ERROR_MESSAGE = "Problem in Edit Profile Logic";

	/**
	 * Updates profile
	 * @param user user info to update profile
	 * @throws LogicException
	 */
	public void updateProfile(User user) throws LogicException{
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		WrapperConnection connection = connectionPool.takeConnection().orElseThrow(LogicException::new);
		UserDAO userDAO = new UserDAO(connection);
		try {
			userDAO.update(user);
		} catch (DAOException e) {
			throw new LogicException(ERROR_MESSAGE, e);
		} finally {
			if (connection != null) {
				connectionPool.returnConnection(connection);
			}
		}
	}
}
