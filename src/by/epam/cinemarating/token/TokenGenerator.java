package by.epam.cinemarating.token;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Random;

public class TokenGenerator {
    private static final Random RANDOM = new Random(System.currentTimeMillis());

    public static String generateToken() {
        return DigestUtils.sha256Hex(String.valueOf(RANDOM.nextInt()));
    }
}
