package com.egs.bankservice.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PasswordHashUtilsTest {

    @Test
    public void testGetSha1EncodedString() {
        Assertions.assertEquals("a94a8fe5ccb19ba61c4c0873d391e987982fbbd3".toUpperCase(),
                PasswordHashUtils.getSha1EncodedString("test").toUpperCase());
    }

    @Test
    public void testValidatePBKDF2HashPassword() {
        Assertions.assertTrue(PasswordHashUtils.validatePBKDF2HashPassword(
                "AQAAAAEAACcQAAAAEHb5EcbN831dFBfsdCeMDeu8U8DYUvdqSHv5fH+FxyxRoC9v1GaL1E3nvt66oJ8Vsw==", "password"));

        Assertions.assertFalse(PasswordHashUtils.validatePBKDF2HashPassword(
                "AQAAAAEAACcQAAAAEHb5EcbN831dFBfsdCeMDeu8U8DYUvdqSHv5fH+FxyxRoC9v1GaL1E3nvt66oJ8Vsw==", "test"));
    }
}
