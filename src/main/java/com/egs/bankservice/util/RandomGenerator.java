package com.egs.bankservice.util;

import java.util.Random;

public class RandomGenerator {

    public static String getRandomPin() {
        Random rnd = new Random();
        int number = rnd.nextInt(10000);
        return String.format("%04d", number);
    }

    public static String getRandomAlphanumericString(int StringLength) {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(StringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
