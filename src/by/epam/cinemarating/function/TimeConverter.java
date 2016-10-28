package by.epam.cinemarating.function;

import java.sql.Timestamp;

public class TimeConverter {
	private static final String DAYS = " days; ";
	private static final String HOURS = " hours; ";
	private static final String MINUTES = " minutes; ";
	private static final String SECONDS = " seconds;";

	/**
	 * Finds difference between two Timestamps
	 * @param firstTimestamp first Timestamp
	 * @param secondTimestamp second Timestamp
	 * @return difference in such format:
	 * <p>
	 *     %d days; %d hours; %d minutes; %d seconds;
	 * </p>
	 */
	public static String findDifference(Timestamp firstTimestamp, Timestamp secondTimestamp) {
		long diff = firstTimestamp.getTime() - secondTimestamp.getTime();

		long diffSeconds = diff / 1000 % 60;
		long diffMinutes = diff / (60 * 1000) % 60;
		long diffHours = diff / (60 * 60 * 1000) % 24;
		long diffDays = diff / (24 * 60 * 60 * 1000);

		String result = "";
		if (diffDays != 0) {
			result += diffDays + DAYS;
		}
		if (diffHours != 0) {
			result += diffHours + HOURS;
		}
		if (diffMinutes != 0) {
			result += diffMinutes + MINUTES;
		}
		if (diffSeconds != 0) {
			result += diffSeconds + SECONDS;
		}
		return result;
	}
}
