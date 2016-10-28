package by.epam.cinemarating.entity;

import java.sql.Date;

public class User extends Entity{
	private long userId;
	private Role role;
	private String login;
	private String password;
	private String pin;
	private String email;
	private Date createDate;
	private String name;
	private String surname;
	private int status;
	private String photo;

	public User() {
	}

	public User(long userId, Role role, String login, String password, String pin, String email, Date createDate, String name, String surname, int status, String photo) {

		this.userId = userId;
		this.role = role;
		this.login = login;
		this.password = password;
		this.pin = pin;
		this.email = email;
		this.createDate = createDate;
		this.name = name;
		this.surname = surname;
		this.status = status;
		this.photo = photo;
	}

	public long getUserId() {

		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	@Override
	public String toString() {
		return "User{" +
				"userId=" + userId +
				", role=" + role +
				", login='" + login + '\'' +
				", password='" + password + '\'' +
				", pin='" + pin + '\'' +
				", email='" + email + '\'' +
				", createDate=" + createDate +
				", name='" + name + '\'' +
				", surname='" + surname + '\'' +
				", status=" + status +
				", photo='" + photo + '\'' +
				'}';
	}
}
