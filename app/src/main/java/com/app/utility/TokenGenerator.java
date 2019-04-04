package com.app.utility;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class TokenGenerator {

    /**
     * creates a token with 130 random bits put into string of base 32
     * @return
     */
    public static String generateToken () {
        SecureRandom rand;
        String token;
        try {
            rand = SecureRandom.getInstanceStrong();
            token = new BigInteger(130,rand).toString(32);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to instantiate random number generator", e);
        }
        return token;
    }
}
