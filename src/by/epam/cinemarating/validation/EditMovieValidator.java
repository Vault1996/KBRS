package by.epam.cinemarating.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditMovieValidator {
	private static final int MAX_NAME_LENGTH = 30;
	private static final int MAX_COUNTRY_LENGTH = 30;
	private static final int MAX_DESCRIPTION_LENGTH = 512;
	private static final int MIN_DESCRIPTION_LENGTH = 1;
	private static final int MIN_NAME_LENGTH = 1;
	private static final int MIN_COUNTRY_LENGTH = 1;

	/**
	 * Validates the data to edit movie
	 * @param name name of movie
	 * @param year year
	 * @param country country
	 * @param description description to movie
	 * @return true if data is valid and false otherwise
	 */
	public boolean validate(String name, String  year, String country, String description) {
		Pattern yearPattern = Pattern.compile("[12](\\d){3}");
		Matcher yearMatcher = yearPattern.matcher(year);
		if (name != null && !name.isEmpty()) {
			if (name.length() > MAX_NAME_LENGTH || name.length() < MIN_NAME_LENGTH) {
				return false;
			}
		}
		if (country != null && !country.isEmpty()) {
			if (country.length() > MAX_COUNTRY_LENGTH || country.length() < MIN_COUNTRY_LENGTH) {
				return false;
			}
		}
		if (description != null && !description.isEmpty()) {
			if (description.length() < MIN_DESCRIPTION_LENGTH ||
					description.length() > MAX_DESCRIPTION_LENGTH) {
				return false;
			}
		}
		return yearMatcher.matches();
	}
}
