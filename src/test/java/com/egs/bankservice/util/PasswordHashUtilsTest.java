package com.egs.bankservice.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PasswordHashUtilsTest {

    @Test
    public void testGetSha1EncodedString() {
        Assertions.assertEquals("098f6bcd4621d373cade4e832627b4f6", PasswordHashUtils.getMD5PasswordHash("test"));
    }

    @Test
    public void testValidatePBKDF2HashPassword() {
        Assertions.assertTrue(PasswordHashUtils.validateMD5PasswordHash("098f6bcd4621d373cade4e832627b4f6", "test"));

        Assertions.assertFalse(PasswordHashUtils.validateMD5PasswordHash("098f6bcd4621d373cade4e832627b4r2", "test"));
    }
}
