package by.epam.cinemarating.dao;

import by.epam.cinemarating.database.WrapperConnection;
import by.epam.cinemarating.entity.Role;
import by.epam.cinemarating.entity.User;
import by.epam.cinemarating.exception.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAO extends AbstractDAO<User> {
	private static final String USER_ID = "user_id";
	private static final String ROLE = "role";
	private static final String LOGIN = "login";
	private static final String PASSWORD = "password";
	private static final String PIN = "pin";
	private static final String EMAIL = "email";
	private static final String CREATE_DATE = "create_date";
	private static final String NAME = "name";
	private static final String SURNAME = "surname";
	private static final String STATUS = "status";
	private static final String PHOTO = "photo";

	private static final String FIND_ALL_USERS = "SELECT user_id,role,login,`password`,pin,email,create_date,`name`," +
			"surname,`status`,photo FROM `user`";
	private static final String FIND_USER_BY_ID = "SELECT user_id,role,login,`password`,pin,email,create_date,`name`," +
			"surname,`status`,photo FROM `user` WHERE user_id=?";
	private static final String FIND_USER_BY_NAME_AND_SURNAME = "SELECT user_id,role,login,`password`,pin,email,create_date,`name`," +
			"surname,`status`,photo FROM `user` WHERE `name`=? AND surname=?";
	private static final String FIND_USER_BY_LOGIN = "SELECT user_id,role,login,`password`,pin,email,create_date,`name`," +
			"surname,`status`,photo FROM `user` WHERE login=?";
	private static final String FIND_USER_BY_EMAIL = "SELECT user_id,role,login,`password`,pin,email,create_date,`name`," +
			"surname,`status`,photo FROM `user` WHERE email=?";
	private static final String FIND_USER_BY_LOGIN_AND_PASSWORD = "SELECT user_id,role,login,`password`,pin,email,create_date,`name`," +
			"surname,`status`,photo FROM `user` WHERE login=? AND `password`=?";

	private static final String INSERT_USER = "INSERT INTO `user`(role,login,`password`,pin,email,create_date,`name`," +
			"surname,`status`,photo) VALUES(?,?,?,?,?,CURDATE(),?,?,?,?)";
	private static final String UPDATE_USER_BY_ID = "UPDATE `user` SET role=?,login=?,`password`=?,pin=?,email=?," +
			"create_date=?,`name`=?,surname=?,`status`=?,photo=? WHERE user_id=?";
	private static final String DELETE_USER_BY_ID = "DELETE FROM `user` WHERE user_id=?";
	private static final String GET_LAST_USER_ID = "SELECT LAST(user_id) FROM `user`";
	private static final String FIND_USER_BY_LOGIN_AND_PASSWORD_AND_PIN = "SELECT user_id,role,login,`password`,pin,email,create_date,`name`," +
			"surname,`status`,photo FROM `user` WHERE login=? AND `password`=? AND pin=?";
	private static final String FIND_USER_BY_USER_ID_AND_PIN = "SELECT user_id,role,login,`password`,pin,email,create_date,`name`," +
			"surname,`status`,photo FROM `user` WHERE user_id=? AND pin=?";


	public UserDAO(WrapperConnection connection) {
		super(connection);
	}

	@Override
	public List<User> findAll() throws DAOException {
		List<User> users = new ArrayList<>();
		try (
				Statement statement = connection.createStatement();
		) {
			ResultSet resultSet = statement.executeQuery(FIND_ALL_USERS);
			while (resultSet.next()) {
				long userId = resultSet.getLong(USER_ID);
				String role = resultSet.getString(ROLE);
				String login = resultSet.getString(LOGIN);
				String password = resultSet.getString(PASSWORD);
				String pin = resultSet.getString(PIN);
				String email = resultSet.getString(EMAIL);
				Date createDate = resultSet.getDate(CREATE_DATE);
				String name = resultSet.getString(NAME);
				String surname = resultSet.getString(SURNAME);
				int status = resultSet.getInt(STATUS);
				String photo = resultSet.getString(PHOTO);
				users.add(new User(userId, Role.valueOf(role.toUpperCase()), login, password, pin,
						email, createDate, name, surname, status, photo));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return users;
	}

	@Override
	public Optional<User> findEntityById(long id) throws DAOException {
		User user = null;
		try (
				PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_ID)
		) {
			statement.setLong(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				String  role = resultSet.getString(ROLE);
				String login = resultSet.getString(LOGIN);
				String password = resultSet.getString(PASSWORD);
				String pin = resultSet.getString(PIN);
				String email = resultSet.getString(EMAIL);
				Date createDate = resultSet.getDate(CREATE_DATE);
				String name = resultSet.getString(NAME);
				String surname = resultSet.getString(SURNAME);
				int status = resultSet.getInt(STATUS);
				String photo = resultSet.getString(PHOTO);
				user = new User(id, Role.valueOf(role.toUpperCase()), login, password, pin,
						email, createDate, name, surname, status, photo);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return Optional.ofNullable(user);
	}

	/**
	 * Retrieves user by name and surname.
	 * @param name name of the user
	 * @param surname surname of the user
	 * @return an entity with the given id
	 * @throws DAOException if any exceptions occurred on the SQL layer
	 */
	public Optional<User> findUserByNameAndSurname(String name, String surname) throws DAOException {
		User user = null;
		try (
				PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_NAME_AND_SURNAME)
		) {
			statement.setString(1, name);
			statement.setString(2, surname);

			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				long userId = resultSet.getLong(USER_ID);
				String role = resultSet.getString(ROLE);
				Date createDate = resultSet.getDate(CREATE_DATE);
				String nameField = resultSet.getString(NAME);
				String surnameField = resultSet.getString(SURNAME);
				int status = resultSet.getInt(STATUS);
				String login = resultSet.getString(LOGIN);
				String password = resultSet.getString(PASSWORD);
				String pin = resultSet.getString(PIN);
				String email = resultSet.getString(EMAIL);
				String photo = resultSet.getString(PHOTO);
				user = new User(userId, Role.valueOf(role.toUpperCase()), login, password, pin,
						email, createDate, nameField, surnameField, status, photo);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return Optional.ofNullable(user);
	}

	/**
	 * Retrieves user by login.
	 * @param login login of the user
	 * @return an entity with the given id
	 * @throws DAOException if any exceptions occurred on the SQL layer
	 */
	public Optional<User> findUserByLogin(String login) throws DAOException {
		User user = null;
		try (
				PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_LOGIN)
		) {
			statement.setString(1, login);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				long id = resultSet.getLong(USER_ID);
				String role = resultSet.getString(ROLE);
				Date createDate = resultSet.getDate(CREATE_DATE);
				String name = resultSet.getString(NAME);
				String surname = resultSet.getString(SURNAME);
				String loginField = resultSet.getString(LOGIN);
				String password = resultSet.getString(PASSWORD);
				String pin = resultSet.getString(PIN);
				String email = resultSet.getString(EMAIL);
				int status = resultSet.getInt(STATUS);
				String photo = resultSet.getString(PHOTO);
				user = new User(id, Role.valueOf(role.toUpperCase()), loginField, password, pin,
						email, createDate, name, surname, status, photo);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return Optional.ofNullable(user);
	}

	/**
	 * Retrieves user by email.
	 * @param email email of the user
	 * @return an entity with the given id
	 * @throws DAOException if any exceptions occurred on the SQL layer
	 */
	public Optional<User> findUserByEmail(String email) throws DAOException {
		User user = null;
		try (
				PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_EMAIL)
		) {
			statement.setString(1, email);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				long id = resultSet.getLong(USER_ID);
				Date createDate = resultSet.getDate(CREATE_DATE);
				String password = resultSet.getString(PASSWORD);
				String pin = resultSet.getString(PIN);
				String emailField = resultSet.getString(EMAIL);
				int status = resultSet.getInt(STATUS);
				String name = resultSet.getString(NAME);
				String surname = resultSet.getString(SURNAME);
				String loginField = resultSet.getString(LOGIN);
				String role = resultSet.getString(ROLE);
				String photo = resultSet.getString(PHOTO);
				user = new User(id, Role.valueOf(role.toUpperCase()), loginField, password, pin,
						emailField, createDate, name, surname, status, photo);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return Optional.ofNullable(user);
	}

	/**
	 * Retrieves user by login and password.
	 * @param login login of the user
	 * @param password password of the user
	 * @return an entity with the given id
	 * @throws DAOException if any exceptions occurred on the SQL layer
	 */
	public Optional<User> findUserByLoginAndPassword(String login, String password) throws DAOException {
		User user = null;
		try (
				PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_LOGIN_AND_PASSWORD)
		) {
			statement.setString(1, login);
			statement.setString(2, password);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				long id = resultSet.getLong(USER_ID);
				String role = resultSet.getString(ROLE);
				String loginField = resultSet.getString(LOGIN);
				String passwordField = resultSet.getString(PASSWORD);
				String pin = resultSet.getString(PIN);
				String email = resultSet.getString(EMAIL);
				Date createDate = resultSet.getDate(CREATE_DATE);
				String name = resultSet.getString(NAME);
				String surname = resultSet.getString(SURNAME);
				int status = resultSet.getInt(STATUS);
				String photo = resultSet.getString(PHOTO);
				user = new User(id, Role.valueOf(role.toUpperCase()), loginField, passwordField, pin,
						email, createDate, name, surname, status, photo);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return Optional.ofNullable(user);
	}

	@Override
	public boolean insert(User entity) throws DAOException {
		int result;
		try(
				PreparedStatement statement = connection.prepareStatement(INSERT_USER)
		) {
			statement.setString(1, entity.getRole().toString());
			statement.setString(2, entity.getLogin());
			statement.setString(3, entity.getPassword());
			statement.setString(4, entity.getPin());
			statement.setString(5, entity.getEmail());
			statement.setString(6, entity.getName());
			statement.setString(7, entity.getSurname());
			statement.setInt(8, entity.getStatus());
			statement.setString(9, entity.getPhoto());
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
				PreparedStatement statement = connection.prepareStatement(DELETE_USER_BY_ID)
		) {
			statement.setLong(1, id);
			result = statement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return result > 0;
	}

	@Override
	public boolean update(User entity) throws DAOException {
		int result;
		try(
				PreparedStatement statement = connection.prepareStatement(UPDATE_USER_BY_ID)
		) {
			statement.setString(1, entity.getRole().toString());
			statement.setString(2, entity.getLogin());
			statement.setString(3, entity.getPassword());
			statement.setString(4, entity.getPin());
			statement.setString(5, entity.getEmail());
			statement.setDate(6, entity.getCreateDate());
			statement.setString(7, entity.getName());
			statement.setString(8, entity.getSurname());
			statement.setInt(9, entity.getStatus());
			statement.setString(10, entity.getPhoto());
			statement.setLong(11, entity.getUserId());
			result = statement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return result > 0;
	}

	/**
	 * Gets the last user id in system.
	 * @return last user id if user exists or -1 otherwise
	 * @throws DAOException if any exceptions occurred on the SQL layer
	 */
	public long getLastUserId() throws DAOException {
		try(
				Statement statement = connection.createStatement()
		) {
			ResultSet resultSet = statement.executeQuery(GET_LAST_USER_ID);
			long result = -1;
			if (resultSet.next()) {
				result = resultSet.getLong(USER_ID);
			}
			return result;
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	public Optional<User> findUserByLoginAndPasswordAndPin(String login, String password, String pin) throws DAOException {
		User user = null;
		try (
				PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_LOGIN_AND_PASSWORD_AND_PIN)
		) {
			statement.setString(1, login);
			statement.setString(2, password);
			statement.setString(3, pin);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				long id = resultSet.getLong(USER_ID);
				String email = resultSet.getString(EMAIL);
				Date createDate = resultSet.getDate(CREATE_DATE);
				String name = resultSet.getString(NAME);
				String surname = resultSet.getString(SURNAME);
				String role = resultSet.getString(ROLE);
				String loginField = resultSet.getString(LOGIN);
				int status = resultSet.getInt(STATUS);
				String photo = resultSet.getString(PHOTO);
				String passwordField = resultSet.getString(PASSWORD);
				String pinField = resultSet.getString(PIN);
				user = new User(id, Role.valueOf(role.toUpperCase()), loginField, passwordField, pinField,
						email, createDate, name, surname, status, photo);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return Optional.ofNullable(user);
	}

	public Optional<User> findUserByUserIdAndPin(long userId, String pin) throws DAOException {
		User user = null;
		try (
				PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_USER_ID_AND_PIN)
		) {
			statement.setLong(1, userId);
			statement.setString(2, pin);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				long id = resultSet.getLong(USER_ID);
				String email = resultSet.getString(EMAIL);
				Date createDate = resultSet.getDate(CREATE_DATE);
				String name = resultSet.getString(NAME);
				String surname = resultSet.getString(SURNAME);
				String role = resultSet.getString(ROLE);
				String loginField = resultSet.getString(LOGIN);
				int status = resultSet.getInt(STATUS);
				String photo = resultSet.getString(PHOTO);
				String passwordField = resultSet.getString(PASSWORD);
				String pinField = resultSet.getString(PIN);
				user = new User(id, Role.valueOf(role.toUpperCase()), loginField, passwordField, pinField,
						email, createDate, name, surname, status, photo);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return Optional.ofNullable(user);
	}
}
