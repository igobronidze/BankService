package com.egs.bankservice.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.egs.bankservice.exception.BankException;

public class PasswordHashUtils {

    private static final Logger logger = LoggerFactory.getLogger(PasswordHashUtils.class);

    public static String getMD5PasswordHash(String password) {
        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(password.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest();
            //These bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            logger.error("Error occurred while hash password", ex);
            throw new BankException("Can't hash password");
        }
        return generatedPassword;
    }

    public static boolean validateMD5PasswordHash(String fullPasswordHash, String password) {
        return fullPasswordHash.equals(getMD5PasswordHash(password));
    }
}
