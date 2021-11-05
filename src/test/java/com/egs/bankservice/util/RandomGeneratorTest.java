package com.egs.bankservice.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RandomGeneratorTest {

    @Test
    public void testGetRandomPin() {
        String pin = RandomGenerator.getRandomPin();
        Assertions.assertEquals(4, pin.length());

        for (char c : pin.toCharArray()) {
            Assertions.assertTrue(Character.isDigit(c));
        }
    }

    @Test
    public void testGetRandomAlphanumericString() {
        String str = RandomGenerator.getRandomAlphanumericString(10);
        Assertions.assertEquals(10, str.length());

        for (char c : str.toCharArray()) {
            Assertions.assertTrue(Character.isDigit(c) || Character.isAlphabetic(c));
        }
    }
}
