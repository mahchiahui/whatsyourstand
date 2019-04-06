package com.app.controller;
import com.app.dao.TokenDAO;
import com.app.utility.AsymmetricCryptography;
import com.app.utility.GenerateKeys;
import com.app.utility.TokenGenerator;

import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.*;
import java.sql.Timestamp;

public class VerificationTokenController {
    public static boolean verifiedUser(int voterID, String path) {

        //generate token
        String token = TokenGenerator.getAlphaNumeric(32);
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


}
