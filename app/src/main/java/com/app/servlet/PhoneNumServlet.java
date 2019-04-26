package com.app.servlet;

import com.app.dao.PhoneNumTokenDAO;
import com.app.utility.SendEmailTLS;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;

/**
 * This servlet handles HTTP request on url "/phoneNumberCheck" for validating phone token
 * as the first step in the registration process.
 * doPost function always sends an email with random token string generated on the fly to a voter's
 * gmail account. After this is done, redirect user to a new page using jsp.
 * doGet function handles page display using jsp.
 */
public class PhoneNumServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /*
         * This creates a random token
         * store the token in the database
         * sends an email to testing voter gmail account with the token
         */
        String token = getRandomNumberString();
        PhoneNumTokenDAO.insertPhoneNumToken(token);
        String message = "Dear Voter, \n\n This is the token the verify your phone number: " + token;
        SendEmailTLS.sendEmail(message,"whatsyourstandtest@gmail.com");
        RequestDispatcher view = request.getRequestDispatcher("/html/register-code.jsp");
        view.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher view = request.getRequestDispatcher("/html/register-tel.html");
        view.forward(request, response);
    }

    /**
     * generates 6 digits random number
     * @return 6 digits number
     */
    public static String getRandomNumberString() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }
}
