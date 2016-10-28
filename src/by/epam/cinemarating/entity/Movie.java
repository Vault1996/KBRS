package by.epam.cinemarating.entity;

public class Movie extends Entity{
	private long movieId;
	private String name;
	private int year;
	private String description;
	private String country;
	private double rating;
	private String poster;

	public Movie() {
	}

	public Movie(long movieId, String name, int year, String description, String country, double rating, String poster) {
		this.movieId = movieId;
		this.name = name;
		this.year = year;
		this.description = description;
		this.country = country;
		this.rating = rating;
		this.poster = poster;
	}

	public long getMovieId() {
		return movieId;
	}

	public void setMovieId(long movieId) {
		this.movieId = movieId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	@Override
	public String toString() {
		return "Movie{" +
				"movieId=" + movieId +
				", name='" + name + '\'' +
				", year=" + year +
				", description='" + description + '\'' +
				", country='" + country + '\'' +
				", rating=" + rating +
				", poster='" + poster + '\'' +
				'}';
	}
}
