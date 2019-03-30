package com.app.controller;
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

        //generateKeys if no keys
        String publicKeyPath = path + "\\KeyPair\\VerificationPublicKey";
        String privateKeyPath = path + "\\KeyPair\\VerificationPrivateKey";
        File temp = new File(publicKeyPath);
        if (!temp.exists()){
            try {
                GenerateKeys gk = new GenerateKeys(1024);
                gk.createKeys();
                gk.writeToFile(publicKeyPath, gk.getPublicKey().getEncoded());
                gk.writeToFile(privateKeyPath, gk.getPrivateKey().getEncoded());
            } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
                System.err.println(e.getMessage());
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }

        //encrypt token with timestamp
        try{
            AsymmetricCryptography ac = new AsymmetricCryptography();
            PrivateKey privateKey = ac.getPrivate(privateKeyPath);
            PublicKey publicKey = ac.getPublic(publicKeyPath);
            String encrypted_msg = ac.encryptText(plaintext, privateKey);
            return ActualTokenController.receiveEncryptedMessage(publicKey,encrypted_msg);
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
