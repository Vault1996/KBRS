package by.epam.cinemarating.validation;

import org.apache.commons.validator.routines.EmailValidator;

public class RegistrationValidator {
	private static final int MAX_LOGIN_LENGTH = 64;
	private static final int MAX_PASSWORD_LENGTH = 64;
	private static final int MIN_PASSWORD_LENGTH = 4;
	private static final int MIN_LOGIN_LENGTH = 1;

	/**
	 * Validates the data to register
	 * @param login login of user
	 * @param email email of user
	 * @param password password of user
	 * @return true if data is valid and false otherwise
	 */
	public boolean validate(String login, String email, String password) {
		EmailValidator emailValidator =  EmailValidator.getInstance();
		return (login != null && password != null &&
				login.length() >= MIN_LOGIN_LENGTH &&
				login.length() <= MAX_LOGIN_LENGTH &&
				password.length() >= MIN_PASSWORD_LENGTH &&
				password.length() <= MAX_PASSWORD_LENGTH) &&
				emailValidator.isValid(email);
	}
}
