package com.monopoco.common.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.warehouse.util
 * Author: hungdq
 * Date: 21/03/2024
 * Time: 17:50
 */
@Component
public class CommonUtil {

    @Autowired
    private Environment env;

    public static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private static final SecureRandom random = new SecureRandom();

    public static UUID generateRandomUUID() {
        return UUID.randomUUID();
    }

    public static String hashPassword(String password, String saltPassword) {
        try {
            // Concatenate password and salt
            String passwordWithSalt = password + saltPassword;

            // Create MessageDigest instance for SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // Get the hash's bytes
            byte[] bytes = md.digest(passwordWithSalt.getBytes());

            // This bytes[] has bytes in decimal format;
            // Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }

            // Return the hashed password as a string
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String generateRandomString(Integer length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(randomIndex));
        }
        return sb.toString();
    }

    public static UUID convertFromByteArray(byte[] bytes) {
        if (bytes == null || bytes.length != 16) {
            return null;
//            throw new IllegalArgumentException("Invalid UUID byte array");
        }
        long mostSigBits = 0;
        long leastSigBits = 0;
        for (int i = 0; i < 8; i++) {
            mostSigBits = (mostSigBits << 8) | (bytes[i] & 0xff);
        }
        for (int i = 8; i < 16; i++) {
            leastSigBits = (leastSigBits << 8) | (bytes[i] & 0xff);
        }
        return new UUID(mostSigBits, leastSigBits);
    }

    public static String EncodingFix(String mismathString) {
        try {
            // Convert the string to bytes using Latin-1 (common source of garbling)
            byte[] bytes = mismathString.getBytes("ISO-8859-1");

            // Decode the bytes to UTF-8
            String correctedText = new String(bytes, "UTF-8");

            System.out.println(correctedText); // Should output readable text
            return correctedText;
        } catch (Exception e) {
            e.printStackTrace();
            return mismathString;
        }
    }



}
