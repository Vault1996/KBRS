package by.epam.cinemarating.logic;

import by.epam.cinemarating.dao.AccessDAO;
import by.epam.cinemarating.dao.MovieDAO;
import by.epam.cinemarating.database.ConnectionPool;
import by.epam.cinemarating.database.WrapperConnection;
import by.epam.cinemarating.entity.Access;
import by.epam.cinemarating.entity.AccessType;
import by.epam.cinemarating.entity.Movie;
import by.epam.cinemarating.exception.DAOException;
import by.epam.cinemarating.exception.LogicException;

import java.util.ArrayList;
import java.util.List;

public class MovieLogic {
	private static final String RATING = "rating";
	private static final String NAME = "name";
	private static final String ERROR_MESSAGE = "Problem in Movie Logic";

	/**
	 * Finds movie by id
	 * @param movieId id of the movie
	 * @return Movie if exists and null otherwise
	 * @throws LogicException
	 */
	public Movie findMovieById(long movieId) throws LogicException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		WrapperConnection connection = connectionPool.takeConnection().orElseThrow(LogicException::new);
		MovieDAO movieDAO = new MovieDAO(connection);
		try {
			Movie movie = movieDAO.findEntityById(movieId).orElse(null);
			return movie;
		} catch (DAOException e) {
			throw new LogicException(ERROR_MESSAGE, e);
		} finally {
			if (connection != null) {
				connectionPool.returnConnection(connection);
			}
		}
	}

	/**
	 * Finds top movies
	 * @param numberOfTopMovies number of movies in output list
	 * @return list of numberOfTopMovies top movies
	 * @throws LogicException
	 */
	public List<Movie> findTopMovies(int numberOfTopMovies) throws LogicException{
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		WrapperConnection connection = connectionPool.takeConnection().orElseThrow(LogicException::new);
		MovieDAO movieDAO = new MovieDAO(connection);
		try {
			List<Movie> movies = movieDAO.findAll();
			movies.sort((o1, o2)->(Double.valueOf(o2.getRating())).compareTo(o1.getRating()));
			if (movies.size() >= numberOfTopMovies) {
				movies = movies.subList(0, numberOfTopMovies);
			}
			return movies;
		} catch (DAOException e) {
			throw new LogicException(ERROR_MESSAGE, e);
		} finally {
			if (connection != null) {
				connectionPool.returnConnection(connection);
			}
		}
	}

	/**
	 *
	 * @param sortBy sort parameter
	 * <p>
	 * 		"rating" to sort by rating
	 * 	    "name" or else to sort by name
	 * </p>
	 * @return sorted list of all movies
	 * @throws LogicException
	 */
	public List<Movie> getAllMovies(String sortBy) throws LogicException{
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		WrapperConnection connection = connectionPool.takeConnection().orElseThrow(LogicException::new);
		MovieDAO movieDAO = new MovieDAO(connection);
		List<Movie> movies;
		try {
			movies = movieDAO.findAll();
			switch (sortBy) {
				case RATING:
					movies.sort((o1, o2)->(Double.valueOf(o2.getRating())).compareTo(o1.getRating()));
					break;
				case NAME: default:
					movies.sort((o1, o2)->o1.getName().compareTo(o2.getName()));
			}
			return movies;
		} catch (DAOException e) {
			throw new LogicException(ERROR_MESSAGE, e);
		} finally {
			if (connection != null) {
				connectionPool.returnConnection(connection);
			}
		}
	}

	/**
	 * Adds movie to system
	 * @param movie movie to add
	 * @throws LogicException
	 */
	public void addMovie(Movie movie) throws LogicException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		WrapperConnection connection = connectionPool.takeConnection().orElseThrow(LogicException::new);
		MovieDAO movieDAO = new MovieDAO(connection);
		try {
			movieDAO.insert(movie);
		} catch (DAOException e) {
			throw new LogicException(ERROR_MESSAGE, e);
		} finally {
			if (connection != null) {
				connectionPool.returnConnection(connection);
			}
		}
	}

	/**
	 * Delete movie by id from system
	 * @param movieId id of movie to delete
	 * @throws LogicException
	 */
	public void deleteMovie(long movieId) throws LogicException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		WrapperConnection connection = connectionPool.takeConnection().orElseThrow(LogicException::new);
		MovieDAO movieDAO = new MovieDAO(connection);
		try {
			movieDAO.delete(movieId);
		} catch (DAOException e) {
			throw new LogicException(ERROR_MESSAGE, e);
		} finally {
			if (connection != null) {
				connectionPool.returnConnection(connection);
			}
		}
	}

	/**
	 * Edit existing movie
	 * @param movie movie info to update
	 * @throws LogicException
	 */
	public void editMovie(Movie movie) throws LogicException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		WrapperConnection connection = connectionPool.takeConnection().orElseThrow(LogicException::new);
		MovieDAO movieDAO = new MovieDAO(connection);
		try {
			movieDAO.update(movie);
		} catch (DAOException e) {
			throw new LogicException(ERROR_MESSAGE, e);
		} finally {
			if (connection != null) {
				connectionPool.returnConnection(connection);
			}
		}
	}

	/**
	 * Get last movie id
	 * @return
	 * @throws LogicException
	 */
	public long getLastMovieId() throws LogicException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		WrapperConnection connection = connectionPool.takeConnection().orElseThrow(LogicException::new);
		MovieDAO movieDAO = new MovieDAO(connection);
		try {
			return movieDAO.getLastMovieId();
		} catch (DAOException e) {
			throw new LogicException(ERROR_MESSAGE, e);
		} finally {
			if (connection != null) {
				connectionPool.returnConnection(connection);
			}
		}
	}

	public List<Movie> getMyMovies(long userId) throws LogicException {
		ConnectionPool connectionPool = ConnectionPool.getInstance();
		WrapperConnection connection = connectionPool.takeConnection().orElseThrow(LogicException::new);
		MovieDAO movieDAO = new MovieDAO(connection);
		AccessDAO accessDAO = new AccessDAO(connection);
		List<Movie> movies = new ArrayList<>();
		List<Access> accesses;
		try {
			accesses = accessDAO.findAccessesByUserId(userId);
			for (Access access : accesses) {
				if (access.getAccessType() == AccessType.A) {
					movies.add(movieDAO.findEntityById(access.getMovieId()).orElse(new Movie()));
				}
			}
			return movies;
		} catch (DAOException e) {
			throw new LogicException(ERROR_MESSAGE, e);
		} finally {
			if (connection != null) {
				connectionPool.returnConnection(connection);
			}
		}
	}
}
