package com.app.servlet;

import com.app.dao.UserDAO;
import com.app.entity.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "testing", urlPatterns = {"/testing"})
public class testing extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher view = request.getRequestDispatcher("/html/LoginPage.html");
        User user = UserDAO.retrieveUser();
        System.out.println("user id: " + user.getUserId());
        System.out.println("username: " + user.getUsername());
        view.forward(request, response);
    }
}
