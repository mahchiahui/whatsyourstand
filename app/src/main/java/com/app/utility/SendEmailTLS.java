package com.app.utility;

import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Utility class for configuring email client to send email in registration process
 */
public class SendEmailTLS {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(SendEmailTLS.class);

    /**
     * This function sends an email from the whatsyourstand official email to the user
     * @param emailContent
     * @param toEmailAddr
     */
    public static void sendEmail(String emailContent, String toEmailAddr) {
        final String username = "whatsyourstandeps@gmail.com";
        final String password = "whatsyourstand1993";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("whatsyourstandeps@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(toEmailAddr)
            );
            message.setSubject("Registration");
            message.setText(emailContent);

            Transport.send(message);

            logger.info("email sent to " + toEmailAddr);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
