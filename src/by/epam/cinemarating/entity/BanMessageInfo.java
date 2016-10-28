package by.epam.cinemarating.entity;

public class BanMessageInfo extends Entity {
	private User user;
	private Ban ban;
	private BanMessage banMessage;

	public BanMessageInfo() {
	}

	public BanMessageInfo(User user, Ban ban, BanMessage banMessage) {
		this.user = user;
		this.ban = ban;
		this.banMessage = banMessage;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Ban getBan() {
		return ban;
	}

	public void setBan(Ban ban) {
		this.ban = ban;
	}

	public BanMessage getBanMessage() {
		return banMessage;
	}

	public void setBanMessage(BanMessage banMessage) {
		this.banMessage = banMessage;
	}

	@Override
	public String toString() {
		return "BanMessageInfo{" +
				"user=" + user +
				", ban=" + ban +
				", banMessage=" + banMessage +
				'}';
	}
}
