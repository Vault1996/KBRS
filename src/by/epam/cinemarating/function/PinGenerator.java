package by.epam.cinemarating.function;

import java.util.Random;

public class PinGenerator {
	private Random random = new Random(System.currentTimeMillis());
	public String generatePin() {
		char one = (char)((int)'0'+random.nextInt(10));
		char two = (char)((int)'0'+random.nextInt(10));
		char three = (char)((int)'0'+random.nextInt(10));
		char four = (char)((int)'0'+random.nextInt(10));
		return "" + one + two + three + four;
	}

}
