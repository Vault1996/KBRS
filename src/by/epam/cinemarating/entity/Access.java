package by.epam.cinemarating.entity;

public class Access extends Entity {
	private long userId;
	private long movieId;
	private AccessType accessType;

	public Access() {
	}

	public Access(long userId, long movieId, AccessType accessType) {
		this.userId = userId;
		this.movieId = movieId;
		this.accessType = accessType;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getMovieId() {
		return movieId;
	}

	public void setMovieId(long movieId) {
		this.movieId = movieId;
	}

	public AccessType getAccessType() {
		return accessType;
	}

	public void setAccessType(AccessType accessType) {
		this.accessType = accessType;
	}

	@Override
	public String toString() {
		return "Access{" +
				"userId=" + userId +
				", movieId=" + movieId +
				", accessType=" + accessType +
				'}';
	}
}
