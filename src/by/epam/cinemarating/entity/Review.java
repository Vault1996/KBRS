package by.epam.cinemarating.entity;

import java.sql.Timestamp;

public class Review extends Entity{
	private long movieId;
	private long userId;
	private String review;
	private Timestamp time;

	public Review() {
	}

	public Review(long movieId, long userId, String review, Timestamp time) {
		this.movieId = movieId;
		this.userId = userId;
		this.review = review;
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

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "Review{" +
				"movieId=" + movieId +
				", userId=" + userId +
				", review='" + review + '\'' +
				", time=" + time +
				'}';
	}
}
