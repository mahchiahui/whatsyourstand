package com.app.servlet;

import com.app.dao.TokenDAO;
import com.app.dao.UserDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RegistrationServlet", urlPatterns = {"/VoterRegistration"})
public class RegistrationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getParameter("token");
        String password = request.getParameter("password");
        String repPassword = request.getParameter("repPassword");
        if (TokenDAO.checkToken(token) && password.equals(repPassword)) {
            String voterUsername = UserDAO.insertUser(password);
            boolean success = false;
            if (voterUsername != "") {
                success = true;
            }
            request.setAttribute("userInsertSuccess", success);
            request.setAttribute("voterUsername",voterUsername);
        }
        RequestDispatcher view = request.getRequestDispatcher("/html/RegistrationSuccess.jsp");
        view.forward(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher view = request.getRequestDispatcher("/html/RegistrationToken.html");
        view.forward(request, response);
    }
}
