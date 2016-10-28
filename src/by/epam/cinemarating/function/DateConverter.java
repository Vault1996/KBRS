package by.epam.cinemarating.function;

import java.sql.Timestamp;

public class DateConverter {
	/**
	 * Converts time from HTML pattern to Timestamp
	 * @param time HTML pattern of time
	 * @return converted Timestamp
	 */
	public static Timestamp stringToTimestamp(String time) {
		time = time.replace('T',' ');
		time = time + ":00";
		return Timestamp.valueOf(time);
	}
}
