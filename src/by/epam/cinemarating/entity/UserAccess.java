package by.epam.cinemarating.entity;

public class UserAccess extends Entity {
	User user;
	AccessType accessType;

	public UserAccess() {
	}

	public UserAccess(User user, AccessType accessType) {
		this.user = user;
		this.accessType = accessType;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public AccessType getAccessType() {
		return accessType;
	}

	public void setAccessType(AccessType accessType) {
		this.accessType = accessType;
	}

	@Override
	public String toString() {
		return "UserAccess{" +
				"user=" + user +
				", accessType=" + accessType +
				'}';
	}
}
