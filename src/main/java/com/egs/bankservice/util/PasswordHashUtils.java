package com.egs.bankservice.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PasswordHashUtils {

    private static final Logger logger = LoggerFactory.getLogger(PasswordHashUtils.class);

    public static String getSha1EncodedString(String input) {
        String sha1 = null;
        try {
            MessageDigest msdDigest = MessageDigest.getInstance("SHA-1");
            msdDigest.update(input.getBytes("UTF-8"), 0, input.length());
            sha1 = DatatypeConverter.printHexBinary(msdDigest.digest());
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            logger.error("Error occurred while encode input", ex);
        }
        return sha1;
    }

    public static boolean validatePBKDF2HashPassword(String fullPasswordHash, String password) {
        try {
            byte[] fullPasswordHashBytes = Base64.decodeBase64(fullPasswordHash);

            byte[] saltBytes = Arrays.copyOfRange(fullPasswordHashBytes, 13, 29);
            byte[] passwordHashBytes = Arrays.copyOfRange(fullPasswordHashBytes, 29, 61);

            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), saltBytes, 10_000, 256);
            SecretKey key = skf.generateSecret(spec);
            byte[] res = key.getEncoded();

            return Arrays.equals(res, passwordHashBytes);
        } catch (Exception ex) {
            logger.error("Error occurred while encode input", ex);
            return false;
        }
    }
}
