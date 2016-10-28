package by.epam.cinemarating.dao;

import by.epam.cinemarating.database.WrapperConnection;
import by.epam.cinemarating.entity.Review;
import by.epam.cinemarating.exception.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReviewDAO extends AbstractDAO<Review> {
	private static final String MOVIE_ID = "movie_id";
	private static final String USER_ID = "user_id";
	private static final String REVIEW = "review";
	private static final String TIME = "time";

	private static final String FIND_ALL_REVIEWS = "SELECT movie_id,user_id,review,`time` " +
			"FROM review";
	private static final String FIND_REVIEW_BY_MOVIE_ID = "SELECT movie_id,user_id,review,`time` " +
			"FROM review WHERE movie_id=?";
	private static final String FIND_REVIEW_BY_USER_ID = "SELECT movie_id,user_id,review,`time` " +
			"FROM review WHERE user_id=?";
	private static final String FIND_REVIEW = "SELECT movie_id,user_id,review,`time` " +
			"FROM review WHERE movie_id=? AND user_id=?";

	private static final String INSERT_REVIEW = "INSERT INTO review(movie_id,user_id,review,`time`) " +
			"VALUES(?,?,?,NOW())";
	private static final String UPDATE_REVIEW = "UPDATE review SET review=? " +
			"WHERE movie_id=? AND user_id=?";
	private static final String DELETE_REVIEW_BY_MOVIE_ID = "DELETE FROM review WHERE movie_id=?";
	private static final String DELETE_REVIEW_BY_USER_ID = "DELETE FROM review WHERE user_id=?";
	private static final String DELETE_REVIEW = "DELETE FROM review WHERE movie_id=? AND user_id=?";


	public ReviewDAO(WrapperConnection connection) {
		super(connection);
	}

	@Override
	public List<Review> findAll() throws DAOException {
		List<Review> reviews = new ArrayList<>();
		try (
				Statement statement = connection.createStatement()
		) {
			ResultSet resultSet = statement.executeQuery(FIND_ALL_REVIEWS);
			while (resultSet.next()) {
				long movieId = resultSet.getLong(MOVIE_ID);
				Timestamp time = resultSet.getTimestamp(TIME);
				long userId = resultSet.getLong(USER_ID);
				String review = resultSet.getString(REVIEW);
				reviews.add(new Review(movieId, userId, review, time));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return reviews;
	}

	@Override
	public Optional<Review> findEntityById(long id) throws DAOException {
		return Optional.empty();
	}

	/**
	 * Retrieves all reviews to the specific movie by movie id
	 * @param movieId id of the movie to find
	 * @return all reviews to the specific movie
	 * @throws DAOException if any exceptions occurred on the SQL layer
	 */
	public List<Review> findReviewsByMovieId(long movieId) throws DAOException {
		List<Review> reviews = new ArrayList<>();
		try (
				PreparedStatement statement = connection.prepareStatement(FIND_REVIEW_BY_MOVIE_ID)
		) {
			statement.setLong(1, movieId);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Timestamp time = resultSet.getTimestamp(TIME);
				long movieIdField = resultSet.getLong(MOVIE_ID);
				long userId = resultSet.getLong(USER_ID);
				String reviewField = resultSet.getString(REVIEW);
				reviews.add(new Review(movieIdField, userId, reviewField, time));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return reviews;
	}

	/**
	 * Retrieves all reviews of the specific user by user id
	 * @param userId id of the user to find
	 * @return all reviews of the specific user
	 * @throws DAOException if any exceptions occurred on the SQL layer
	 */
	public List<Review> findReviewsByUserId(long userId) throws DAOException {
		List<Review> reviews = new ArrayList<>();
		try (
				PreparedStatement statement = connection.prepareStatement(FIND_REVIEW_BY_USER_ID)
		) {
			statement.setLong(1, userId);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				long userIdField = resultSet.getLong(USER_ID);
				long movieId = resultSet.getLong(MOVIE_ID);
				String reviewField = resultSet.getString(REVIEW);
				Timestamp time = resultSet.getTimestamp(TIME);
				reviews.add(new Review(movieId, userIdField, reviewField, time));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return reviews;
	}

	/**
	 * Retrieves review to the specific movie rated by specific user
	 * @param movieId id of the movie to find
	 * @param userId id of the user to find
	 * @return review to the specific movie rated by specific user
	 * @throws DAOException if any exceptions occurred on the SQL layer
	 */
	public Optional<Review> findReview(long movieId, long userId) throws DAOException {
		Review review = null;
		try (
				PreparedStatement statement = connection.prepareStatement(FIND_REVIEW)
		) {
			statement.setLong(1, movieId);
			statement.setLong(2, userId);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				long userIdField = resultSet.getLong(USER_ID);
				long movieIdField = resultSet.getLong(MOVIE_ID);
				String reviewField = resultSet.getString(REVIEW);
				Timestamp time = resultSet.getTimestamp(TIME);
				review = new Review(movieIdField, userIdField, reviewField, time);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return Optional.ofNullable(review);
	}

	@Override
	public boolean insert(Review entity) throws DAOException {
		int result;
		try (
				PreparedStatement statement = connection.prepareStatement(INSERT_REVIEW)
		) {
			statement.setLong(1, entity.getMovieId());
			statement.setLong(2, entity.getUserId());
			statement.setString(3, entity.getReview());
			result = statement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return result > 0;
	}

	@Override
	public boolean delete(long id) throws DAOException {
		return false;
	}

	/**
	 * Deletes all reviews to movie.
	 * @param movieId id of the movie to delete
	 * @return true if the entity was deleted, false otherwise
	 * @throws DAOException if any exceptions occurred on the SQL layer
	 */
	public boolean deleteReviewsByMovieId(long movieId) throws DAOException {
		int result;
		try(
				PreparedStatement statement = connection.prepareStatement(DELETE_REVIEW_BY_MOVIE_ID)
		) {
			statement.setLong(1, movieId);
			result = statement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return result > 0;
	}

	/**
	 * Deletes all reviews of user.
	 * @param userId id of the user to delete
	 * @return true if the entity was deleted, false otherwise
	 * @throws DAOException if any exceptions occurred on the SQL layer
	 */
	public boolean deleteReviewsByUserId(long userId) throws DAOException {
		int result;
		try(
				PreparedStatement statement = connection.prepareStatement(DELETE_REVIEW_BY_USER_ID)
		) {
			statement.setLong(1, userId);
			result = statement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return result > 0;
	}
	/**
	 * Deletes review to movie rated by user.
	 * @param movieId id of the movie to delete
	 * @param userId id of the user to delete
	 * @return true if the entity was deleted, false otherwise
	 * @throws DAOException if any exceptions occurred on the SQL layer
	 */
	public boolean deleteReview(long movieId, long userId) throws DAOException {
		int result;
		try(
				PreparedStatement statement = connection.prepareStatement(DELETE_REVIEW)
		) {
			statement.setLong(2, userId);
			statement.setLong(1, movieId);
			result = statement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return result > 0;
	}

	@Override
	public boolean update(Review entity) throws DAOException {
		int result;
		try(
				PreparedStatement statement = connection.prepareStatement(UPDATE_REVIEW)
		) {
			statement.setString(1, entity.getReview());
			statement.setLong(2, entity.getMovieId());
			statement.setLong(3, entity.getUserId());
			result = statement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		return result > 0;
	}
}