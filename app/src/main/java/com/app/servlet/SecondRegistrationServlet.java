package com.app.servlet;

import com.app.dao.UserDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * This servlet is only available for developer's account creation testing purpose.
 * The url it handles is "/second-registration".
 * It should not be included in the actual functionality.
 */
public class SecondRegistrationServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//    response.getWriter().append("Served at: ").append(request.getContextPath());

        // Get HttpSession object
        HttpSession session = request.getSession();

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        int role = Integer.parseInt(request.getParameter("role"));
        System.out.println(username);
        System.out.println(password);

        boolean isRegis = UserDAO.registration(username, password, role);
        System.out.println(isRegis);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher view = request.getRequestDispatcher("/html/SecondRegistrationPage.html");
        view.forward(request, response);
    }
}
