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

//@WebServlet("/second-registration")
public class SecondRegistrationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public SecondRegistrationServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//    response.getWriter().append("Served at: ").append(request.getContextPath());

        // Get HttpSession object
        HttpSession session = request.getSession();

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println(username);
        System.out.println(password);

        boolean isRegis = UserDAO.registration(username, password, 0);
        System.out.println(isRegis);

/*
        // Suppose a user has successfully logged.
        UserInfo loginedInfo = new UserInfo("Tom", "USA", 5);

        // Storing user information in an attribute of Session.
        session.setAttribute(Constants.SESSION_USER_KEY, loginedInfo);
*/
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher view = request.getRequestDispatcher("/html/SecondRegistrationPage.html");
        view.forward(request, response);
    }
}
