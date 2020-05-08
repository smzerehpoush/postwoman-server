package com.mahdiyar.util;

import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @author mahdiyar
 */
@Slf4j
public class HashUtil {
    private HashUtil() {
    }

    public static String hash(String value) {
        if (value == null)
            throw new NullPointerException();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = messageDigest.digest(value.getBytes(StandardCharsets.UTF_8));
            return new String(Base64.getEncoder().encode(hashedBytes), StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException e) {
//            this would not happen
            return null;
        }
    }
}
