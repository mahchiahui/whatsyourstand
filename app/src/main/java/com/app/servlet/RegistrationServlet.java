package com.app.servlet;

import com.app.dao.TokenDAO;
import com.app.dao.UserDAO;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This servlet handles HTTP request for url "/TokenRegistration" for account creation
 * as the fourth step in the registration process.
 * doPost function handles password form submission. If token matched and passwords are legal,
 * it create a user in the Q&A system.
 * doGet function display the registration page using html.
 */
public class RegistrationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // takes in parameters from query string
        String token = request.getParameter("token");
        String password = request.getParameter("password");
        String repPassword = request.getParameter("repPassword");

        request.setAttribute("userInsertSuccess", false);
        request.setAttribute("voterUsername", "");

        // check that token is valid and that password and repeated password is equal
        if (TokenDAO.checkToken(token) && password.equals(repPassword)) {
            // hash the password
            String salt = BCrypt.gensalt(12);
            String hashed_password = BCrypt.hashpw(password, salt);

            // insert user
            String voterUsername = UserDAO.insertUser("", hashed_password,1);
            if (! voterUsername.equals("")) {
                //set return attribute back to user
                request.setAttribute("userInsertSuccess", true);
                request.setAttribute("voterUsername", voterUsername);
            }
        }

        // display page
        RequestDispatcher view = request.getRequestDispatcher("/html/Token-CompleteAccountCreation.jsp");
        view.forward(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher view = request.getRequestDispatcher("/html/Token-VoterRegistration.html");
        view.forward(request, response);
    }
}
