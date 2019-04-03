package com.app.controller;

import com.app.dao.TokenDAO;
import com.app.utility.AsymmetricCryptography;
import com.app.utility.GenerateKeys;
import jdk.nashorn.internal.parser.Token;

import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;

public class ActualTokenController {

    private static String publicKeyPath = "";
    private static String privateKeyPath = "";
    public static boolean receiveEncryptedMessage(String encrypted_msg) {

        try{
            //get private key to decrypt
            AsymmetricCryptography ac = new AsymmetricCryptography();
            PrivateKey privateKey = ac.getPrivate(privateKeyPath);

            //decrypt token
            String decrypted_msg = ac.decryptText(encrypted_msg, privateKey);
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

    public static PublicKey getPublicKey(String path){

        //generateKeys if no keys
        publicKeyPath = path + "\\KeyPair\\QnAPublicKey";
        privateKeyPath = path + "\\KeyPair\\QnAPrivateKey";
        PublicKey pubKey = null;
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

        //get the generated publicKey and return it
        try {
            AsymmetricCryptography ac = new AsymmetricCryptography();
            pubKey = ac.getPublic(publicKeyPath);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return pubKey;
    }
}
