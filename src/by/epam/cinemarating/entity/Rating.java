package by.epam.cinemarating.entity;

import java.sql.Timestamp;

public class Rating extends Entity{
	private long movieId;
	private long userId;
	private int rating;
	private Timestamp time;

	public Rating() {
	}

	public Rating(long movieId, long userId, int rating, Timestamp time) {
		this.movieId = movieId;
		this.userId = userId;
		this.rating = rating;
		this.time = time;
	}

	public long getMovieId() {
		return movieId;
	}

	public void setMovieId(long movieId) {
		this.movieId = movieId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "Rating{" +
				"movieId=" + movieId +
				", userId=" + userId +
				", rating=" + rating +
				", time=" + time +
				'}';
	}
}
