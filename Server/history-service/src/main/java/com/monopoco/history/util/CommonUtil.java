package com.monopoco.history.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.monopoco.history.response.UserPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public static Boolean checkPassword(String originPassword, String saltPassword, String hashPassword) {
        String hashPassword_ = hashPassword(originPassword, saltPassword);
        return hashPassword_.equals(hashPassword);
    }

    public static String generateAccessToken(UserPrincipal userPrincipal, String secret, long timeExp, HttpServletRequest request) {
        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());
        String accessToken = JWT.create()
                .withSubject(userPrincipal.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + timeExp))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", userPrincipal.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .withClaim("fullName", userPrincipal.getFullName())
                .withClaim("id", userPrincipal.getId().toString())
                .sign(algorithm);
        return accessToken;
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
