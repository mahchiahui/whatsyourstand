package com.app.servlet;

import com.app.dao.TokenDAO;
import com.app.dao.UserDAO;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RegistrationServlet")
public class RegistrationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //takes in parameters
        String token = request.getParameter("token");
        String password = request.getParameter("password");
        String repPassword = request.getParameter("repPassword");

        request.setAttribute("userInsertSuccess", false);
        request.setAttribute("voterUsername", "");

        //checks that token is valid and that password and repeated password is equal
        if (TokenDAO.checkToken(token) && password.equals(repPassword)) {
            //hash the password
            String salt = BCrypt.gensalt(12);
            String hashed_password = BCrypt.hashpw(password, salt);

            //insert user
            String voterUsername = UserDAO.insertUser("", hashed_password,1);
            if (! voterUsername.equals("")) {
                //set return attribute back to user
                request.setAttribute("userInsertSuccess", true);
                request.setAttribute("voterUsername", voterUsername);
            }
        }
        //return
        RequestDispatcher view = request.getRequestDispatcher("/html/Token-CompleteAccountCreation.jsp");
        view.forward(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getParameter("token");
        request.setAttribute("token",token);
        RequestDispatcher view = request.getRequestDispatcher("/html/Token-VoterRegistration.jsp");
        view.forward(request, response);
    }
}
