package by.epam.cinemarating.dao;

import by.epam.cinemarating.database.WrapperConnection;
import by.epam.cinemarating.entity.Movie;
import by.epam.cinemarating.exception.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MovieDAO extends AbstractDAO<Movie> {
	private static final String MOVIE_ID = "movie_id";
	private static final String NAME = "name";
	private static final String YEAR = "year";
	private static final String DESCRIPTION = "description";
	private static final String COUNTRY = "country";
	private static final String RATING = "rating";
	private static final String POSTER = "poster";

	private static final String FIND_ALL_MOVIES = "SELECT movie_id,`name`,`year`,description,country,rating,poster " +
			"FROM movie";
	private static final String FIND_MOVIE_BY_ID = "SELECT movie_id,`name`,`year`,description,country,rating,poster " +
			"FROM movie WHERE movie_id=?";

	private static final String INSERT_MOVIE = "INSERT INTO movie(`name`,`year`,description,country,rating,poster) " +
			"VALUES(?,?,?,?,?,?)";
	private static final String UPDATE_MOVIE_BY_ID = "UPDATE movie SET `name`=?,`year`=?,description=?,country=?," +
			"rating=?,poster=? WHERE movie_id=?";
	private static final String DELETE_MOVIE_BY_ID = "DELETE FROM movie WHERE movie_id=?";
	private static final String GET_LAST_MOVIE_ID = "SELECT LAST(movie_id) FROM movie";


	public MovieDAO(WrapperConnection connection) {
		super(connection);
	}

	@Override
	public List<Movie> findAll() throws DAOException {
		List<Movie> movies = new ArrayList<>();
		try (
				Statement statement = connection.createStatement()
		) {
			ResultSet resultSet = statement.executeQuery(FIND_ALL_MOVIES);
			while (resultSet.next()) {
				long movieId = resultSet.getLong(MOVIE_ID);
				String name = resultSet.getString(NAME);
				int year = resultSet.getInt(YEAR);
				String description = resultSet.getString(DESCRIPTION);
				String country = resultSet.getString(COUNTRY);
				double rating = resultSet.getDouble(RATING);
				String poster = resultSet.getString(POSTER);
				movies.add(new Movie(movieId, name, year, description,
						country, rating, poster));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return movies;
	}

	@Override
	public Optional<Movie> findEntityById(long id) throws DAOException {
		Movie movie = null;
		try (
				PreparedStatement statement = connection.prepareStatement(FIND_MOVIE_BY_ID)
		) {
			statement.setLong(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				String name = resultSet.getString(NAME);
				int year = resultSet.getInt(YEAR);
				String description = resultSet.getString(DESCRIPTION);
				String country = resultSet.getString(COUNTRY);
				double rating = resultSet.getDouble(RATING);
				String poster = resultSet.getString(POSTER);
				movie = new Movie(id, name, year, description,
						country, rating, poster);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return Optional.ofNullable(movie);
	}

	@Override
	public boolean insert(Movie entity) throws DAOException {
		int result;
		try(
				PreparedStatement statement = connection.prepareStatement(INSERT_MOVIE)
		) {
			statement.setString(1, entity.getName());
			statement.setInt(2, entity.getYear());
			statement.setString(3, entity.getDescription());
			statement.setString(4, entity.getCountry());
			statement.setDouble(5, entity.getRating());
			statement.setString(6, entity.getPoster());
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
				PreparedStatement statement = connection.prepareStatement(DELETE_MOVIE_BY_ID)
		) {
			statement.setLong(1, id);
			result = statement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return result > 0;
	}

	@Override
	public boolean update(Movie entity) throws DAOException {
		int result;
		try(
				PreparedStatement statement = connection.prepareStatement(UPDATE_MOVIE_BY_ID)
		) {
			statement.setString(1, entity.getName());
			statement.setInt(2, entity.getYear());
			statement.setString(3, entity.getDescription());
			statement.setString(4, entity.getCountry());
			statement.setDouble(5, entity.getRating());
			statement.setString(6, entity.getPoster());
			statement.setLong(7, entity.getMovieId());
			result = statement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return result > 0;
	}

	/**
	 * Method to define the last movie id in the system.
	 * @return movie id if last movie exists or -1 otherwise
	 * @throws DAOException if any exceptions occurred on the SQL layer
	 */
	public long getLastMovieId() throws DAOException {
		try(
				Statement statement = connection.createStatement()
		) {
			long result = -1;
			ResultSet resultSet = statement.executeQuery(GET_LAST_MOVIE_ID);
			if (resultSet.next()) {
				result = resultSet.getLong(MOVIE_ID);
			}
			return result;
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}
}
