package by.epam.cinemarating.database;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
	private static final String DB_URL = "url";
	private static final String USER = "user";
	private static final String PASSWORD = "password";
	private static final String AUTO_RECONNECT = "autoReconnect";
	private static final String CHAR_ENCODING = "characterEncoding";
	private static final String USE_UNICODE = "useUnicode";
	private static final String USE_SSL = "useSSL";
	private static final String POOL_SIZE = "poolSize";
	private static final String DATABASE_CONFIG_FILE = "database";
	private static final int MAX_RETRIES = 3;

	private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);
	private static final String INTERRUPTED_ERROR = "Interrupted while waiting";
	private static final String CLOSE_CONNECTION_ERROR = "Can't close connection";

	private ArrayBlockingQueue<WrapperConnection> connectionQueue;

	private static ConnectionPool instance;
	private static ReentrantLock lock = new ReentrantLock();
	private static AtomicBoolean instanceCreated = new AtomicBoolean();

	private ConnectionPool() {
		// register driver
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		} catch (SQLException e) {
			LOGGER.fatal("Can't register driver", e);
			throw new RuntimeException(e);
		}
		//**************************
		ResourceBundle resource = ResourceBundle.getBundle(DATABASE_CONFIG_FILE);
		String url = resource.getString(DB_URL);
		String user = resource.getString(USER);
		String password = resource.getString(PASSWORD);
		String autoReconnect = resource.getString(AUTO_RECONNECT);
		String charEncoding = resource.getString(CHAR_ENCODING);
		String useUnicode = resource.getString(USE_UNICODE);
		String useSSL = resource.getString(USE_SSL);
		int poolSize = Integer.parseInt(resource.getString(POOL_SIZE));
		// get values from .properties file
		Properties properties = new Properties();
		properties.put(USER, user);
		properties.put(PASSWORD, password);
		properties.put(AUTO_RECONNECT, autoReconnect);
		properties.put(CHAR_ENCODING, charEncoding);
		properties.put(USE_UNICODE, useUnicode);
		properties.put(USE_SSL, useSSL);
		//***********
		initializeConnectionPool(poolSize, url, properties);
		// Retry initializing pool
		int numberOfTries = 0;
		while (poolSize != connectionQueue.size() && numberOfTries < MAX_RETRIES) {
			LOGGER.warn("Retry initializing pool.");
			initializeConnectionPool(poolSize, url, properties);
			numberOfTries++;
		}
		// End of program if pool didn't initialized
		if(poolSize != connectionQueue.size()){
			LOGGER.fatal("Can't connect to database");
			throw new RuntimeException();
		}
	}

	/**
	 * Gets the single object of this type
	 * @return new ConnectionPool if not exists and existing one otherwise
	 */
	public static ConnectionPool getInstance() {
		if (!instanceCreated.get()) {
			lock.lock(); // blocking
			try {
				if (instance == null) {
					instance = new ConnectionPool();
					instanceCreated.getAndSet(true);
				}
			} finally {
				lock.unlock(); // unblocking
			}
		}
		return instance;
	}

	/**
	 * Take connection from Connection Pool
	 * @return connection from connection pool if there is any connection or null empty optional otherwise
	 */
	public Optional<WrapperConnection> takeConnection() {
		WrapperConnection connection = null;
		try {
			connection = connectionQueue.poll(5, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			LOGGER.error("Error while waiting", e);
		}
		return Optional.ofNullable(connection);
	}

	/**
	 * Returns connection to Connection Pool
	 * @param connection connection to return to connection pool
	 */
	public void returnConnection(WrapperConnection connection) {
		if (connection != null) {
			connectionQueue.offer(connection);
		}
	}

	/**
	 * Close connection pool by closing all connections
	 */
	public void closePool() {
		try {
			while (!connectionQueue.isEmpty()) {
				connectionQueue.take().close();
			}
		} catch (InterruptedException e) {
			LOGGER.error(INTERRUPTED_ERROR, e);
		} catch (SQLException e) {
			LOGGER.error(CLOSE_CONNECTION_ERROR, e);
		}

	}

	/**
	 * Gets the size of connection pool
	 * @return size of the connection pool
	 */
	public int getSize(){
		if (connectionQueue != null) {
			return connectionQueue.size();
		} else {
			return 0;
		}
	}

	private void initializeConnectionPool(final int POOL_SIZE, String url, Properties properties) {
		connectionQueue = new ArrayBlockingQueue<>(POOL_SIZE);
		for (int i = 0; i < POOL_SIZE; i++) {
			try {
				Connection connection = DriverManager.getConnection(url, properties);
				WrapperConnection wrapperConnection = new WrapperConnection(connection);
				connectionQueue.offer(wrapperConnection);
			} catch (SQLException e) {
				LOGGER.warn(e);
			}
		}
	}
}
