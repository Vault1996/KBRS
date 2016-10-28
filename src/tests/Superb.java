package tests;

import by.epam.cinemarating.function.PinGenerator;

import java.time.ZoneId;
import java.util.TimeZone;

public class Superb{
	private static final ZoneId zoneId = TimeZone.getTimeZone("GMT-02:00").toZoneId();
	public static void main(String[] args) {
//		Calendar calendar = new GregorianCalendar();
//		LocalDateTime localDateTime = LocalDateTime.now();
//		Instant instant = calendar.toInstant();
//		System.out.println(localDateTime.atZone(zoneId));
//		Timestamp timestamp1 = Timestamp.valueOf(localDateTime.atZone(zoneId).toLocalDateTime());
//		Timestamp timestamp2 = Timestamp.valueOf(instant.atZone(zoneId).toLocalDateTime());
//
//		System.out.println(timestamp1);
//		System.out.println(timestamp2);

		System.out.println(new PinGenerator().generatePin());

//		System.out.println(timestamp2.toLocalDateTime().atZone().atZone(TimeZone.getDefault().toZoneId()));
//		System.out.println(timestamp);
//		System.out.println();
//		calendar.setTimeZone(TimeZone.getTimeZone("GMT-00:00"));
//
//		System.out.println(calendar.getTime());
	}
}