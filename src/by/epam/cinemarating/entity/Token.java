package by.epam.cinemarating.entity;

public class Token extends Entity{
	private long userId;
	private String token;

	public Token() {
	}

	public Token(long userId, String token) {

		this.userId = userId;
		this.token = token;
	}

	public long getUserId() {

		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "Token{" +
				"userId=" + userId +
				", token='" + token + '\'' +
				'}';
	}
}
