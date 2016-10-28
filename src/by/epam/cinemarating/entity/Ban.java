package by.epam.cinemarating.entity;

import java.sql.Timestamp;

public class Ban extends Entity{
	private long banId;
	private long userId;
	private Timestamp till;
	private String reason;

	public Ban() {
	}

	public Ban(long banId, long userId, Timestamp till, String reason) {
		this.banId = banId;
		this.userId = userId;
		this.till = till;
		this.reason = reason;
	}

	public long getBanId() {
		return banId;
	}

	public void setBanId(long banId) {
		this.banId = banId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Timestamp getTill() {
		return till;
	}

	public void setTill(Timestamp till) {
		this.till = till;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Override
	public String toString() {
		return "Ban{" +
				"banId=" + banId +
				", userId=" + userId +
				", till=" + till +
				", reason='" + reason + '\'' +
				'}';
	}
}
