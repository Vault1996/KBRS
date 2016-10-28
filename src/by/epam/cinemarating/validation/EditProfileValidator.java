package by.epam.cinemarating.validation;

public class EditProfileValidator {
	private static final int MAX_NAME_LENGTH = 30;
	private static final int MAX_SURNAME_LENGTH = 30;
	private static final int MAX_PASSWORD_LENGTH = 20;
	private static final int MIN_PASSWORD_LENGTH = 4;
	private static final int MIN_NAME_LENGTH = 1;
	private static final int MIN_SURNAME_LENGTH = 1;

	/**
	 * Validates the data to edit profile
	 * @param name name of user
	 * @param surname surname of user
	 * @param newPassword new password
	 * @param repeatPassword repeated password
	 * @return true if data is valid and false otherwise
	 */
	public boolean validate(String name, String surname, String newPassword, String repeatPassword) {
		boolean isValid = true;
		if (name != null && !name.isEmpty()) {
			if (name.length() > MAX_NAME_LENGTH || name.length() < MIN_NAME_LENGTH) {
				isValid = false;
			}
		}
		if (surname != null && !surname.isEmpty()) {
			if (surname.length() > MAX_SURNAME_LENGTH || surname.length() < MIN_SURNAME_LENGTH) {
				isValid = false;
			}
		}
		if (newPassword != null && !newPassword.isEmpty()) {
			if (!newPassword.equals(repeatPassword) || newPassword.length() < MIN_PASSWORD_LENGTH ||
					newPassword.length() > MAX_PASSWORD_LENGTH) {
				isValid = false;
			}
		}
		return isValid;
	}
}
