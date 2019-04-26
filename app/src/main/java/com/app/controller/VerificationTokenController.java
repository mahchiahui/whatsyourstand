package com.app.controller;

import com.app.utility.AsymmetricCryptography;
import com.app.utility.SendEmailTLS;
import com.app.utility.TokenGenerator;
import org.slf4j.LoggerFactory;

import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.sql.Timestamp;

/**
 * Controller class for token generation and verification in business logic layer.
 */
public class VerificationTokenController {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(VerificationTokenController.class);

    /**
     * Generates a token and a timestamp, encrypt all of it and send it to the Q&A servlet.
     * It also sends the token via email to the user.
     * This simulates the communication between the verification server and the Q&A server.
     * @param voterID
     * @param path
     * @return result
     */
    public static boolean verifiedUser(int voterID, String path) {

        // generate token
        String token = TokenGenerator.getAlphaNumeric(32);

        // send token to user
        String message = "Dear Voter, \n\n This is the token create your account: " + token;
        SendEmailTLS.sendEmail(message,"whatsyourstandtest@gmail.com");

        // generate time stamp
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        // create plaintext
        String plaintext = "token:" + token + " timestamp:" + timestamp.toString();

        // simulate getting a public key from the Q&A server
        PublicKey qnaPubKey = ActualTokenController.getPublicKey(path);

        // simulate encrypting token and timestamp, sending it to the Q&A system
        try{
            AsymmetricCryptography ac = new AsymmetricCryptography();
            String encrypted_msg = ac.encryptText(plaintext, qnaPubKey);
            return ActualTokenController.receiveEncryptedMessage(encrypted_msg);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            logger.error("verifiedUser failed",e);
        } catch (Exception e) {
            logger.error("verifiedUser failed",e);
        }
        return false;
    }

}
