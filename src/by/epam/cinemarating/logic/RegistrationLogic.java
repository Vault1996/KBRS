package by.epam.cinemarating.logic;

import by.epam.cinemarating.dao.UserDAO;
import by.epam.cinemarating.database.ConnectionPool;
import by.epam.cinemarating.database.WrapperConnection;
import by.epam.cinemarating.entity.Role;
import by.epam.cinemarating.entity.User;
import by.epam.cinemarating.exception.DAOException;
import by.epam.cinemarating.exception.LogicException;
import by.epam.cinemarating.function.PinGenerator;

import java.util.Optional;

public class RegistrationLogic {
	private static final int INITIAL_USER_STATUS = 100;
	private static final String STANDARD_PHOTO = "images/user/no-user-image.png";

	private static final String ERROR_MESSAGE = "Problem in Registration Logic";

	/**
	 * Register user into system
	 * @param login login of user
	 * @param name name of user
	 * @param surname surname of user
	 * @param email email of user
	 * @param password password of user
	 * @return true if user registered and false otherwise
	 * @throws LogicException
	 */
	public boolean registerUser(String login, String name, String surname, String email, String password, StringBuilder pinAnswer) throws LogicException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		WrapperConnection connection = connectionPool.takeConnection().orElseThrow(LogicException::new);
		UserDAO userDAO = new UserDAO(connection);
		try {
			Optional<User> optionalByLogin = userDAO.findUserByLogin(login);
			Optional<User> optionalByEmail = userDAO.findUserByEmail(email);
			boolean isOk = !optionalByLogin.isPresent() && !optionalByEmail.isPresent();
			if (isOk) {
//				Hasher hasher = new MD5Hash();
				PinGenerator pinGenerator = new PinGenerator();
				String pin = pinGenerator.generatePin();
				pinAnswer.insert(0, pin);
				User user = new User(0, Role.USER, login, password, pin, email, null, name, surname, INITIAL_USER_STATUS, STANDARD_PHOTO);
				userDAO.insert(user);
			}
			return isOk;
		} catch (DAOException e) {
			throw new LogicException(ERROR_MESSAGE, e);
		} finally {
			if (connection != null) {
				connectionPool.returnConnection(connection);
			}
		}
	}
}
