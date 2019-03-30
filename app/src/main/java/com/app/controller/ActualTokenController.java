package com.app.controller;

import com.app.dao.TokenDAO;
import com.app.utility.AsymmetricCryptography;
import jdk.nashorn.internal.parser.Token;

import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

public class ActualTokenController {
    public static boolean receiveEncryptedMessage(PublicKey publicKey, String encrypted_msg) {

        try{
            AsymmetricCryptography ac = new AsymmetricCryptography();

            //decrypt token
            String decrypted_msg = ac.decryptText(encrypted_msg, publicKey);
            String token = decrypted_msg.substring(decrypted_msg.indexOf("token:")+6,decrypted_msg.indexOf(" "));
            String timestamp = decrypted_msg.substring(decrypted_msg.indexOf("timestamp:")+10);

            //insert token to database
            return TokenDAO.insertToken(token, timestamp);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return false;

        //store token and timestamp

    }
}
