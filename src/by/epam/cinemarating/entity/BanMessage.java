package by.epam.cinemarating.entity;

public class BanMessage extends Entity{
	private long banId;
	private String message;

	public BanMessage() {
	}

	public BanMessage(long banId, String message) {
		this.banId = banId;
		this.message = message;
	}

	public long getBanId() {
		return banId;
	}

	public void setBanId(long banId) {
		this.banId = banId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "BanMessage{" +
				"banId=" + banId +
				", message='" + message + '\'' +
				'}';
	}
}
