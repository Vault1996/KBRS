package by.epam.cinemarating.entity;

public class ReviewAndRatingInfo extends Entity{
	private User user;
	private Review review;
	private Rating rating;

	public ReviewAndRatingInfo() {
	}

	public ReviewAndRatingInfo(User user, Review review, Rating rating) {
		this.user = user;
		this.review = review;
		this.rating = rating;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Review getReview() {
		return review;
	}

	public void setReview(Review review) {
		this.review = review;
	}

	public Rating getRating() {
		return rating;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
	}

	@Override
	public String toString() {
		return "ReviewAndRatingInfo{" +
				"user=" + user +
				", review='" + review + '\'' +
				", rating=" + rating +
				'}';
	}
}
