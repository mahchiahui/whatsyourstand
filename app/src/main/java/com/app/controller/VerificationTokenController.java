package com.app.controller;
import com.app.dao.TokenDAO;
import com.app.utility.AsymmetricCryptography;
import com.app.utility.GenerateKeys;

import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.*;
import java.sql.Timestamp;

public class VerificationTokenController {
    public static boolean verifiedUser(int voterID, String path) {

        //generate token
        String token = generateToken();
        String fakeToken = "" + voterID;

        // generate time stamp
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        //create plaintext
        String plaintext = "token:" + fakeToken + " timestamp:" + timestamp.toString();

        //simulate getting a public key from the Q&A server
        PublicKey qnaPubKey = ActualTokenController.getPublicKey(path);

        //simulate encrypting token and timestamp, sending it to the Q&A system
        try{
            AsymmetricCryptography ac = new AsymmetricCryptography();
            String encrypted_msg = ac.encryptText(plaintext, qnaPubKey);
            return ActualTokenController.receiveEncryptedMessage(encrypted_msg);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    //creates a token with 130 random bits put into string of base 32
    public static String generateToken(){
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
