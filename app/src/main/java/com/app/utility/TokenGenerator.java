package com.app.utility;

import java.security.SecureRandom;

/**
 * Utility class for token generation
 */
public class TokenGenerator {

    /**
     * Generate a token of a given length with certain degree of secure randomization
     * @param len
     * @return
     */
    public static String getAlphaNumeric(int len) {
        char[] ch = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
        char[] c = new char[len];
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < len; i++) {
            c[i] = ch[random.nextInt(ch.length)];
        }
        return new String(c);
    }
}
