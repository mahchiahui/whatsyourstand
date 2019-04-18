package com.app.servlet;

import com.app.dao.PhoneNumTokenDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "VerifyPhoneNumServlet")
public class VerifyPhoneNumServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * this takes the token sent from the form
         * checks if the token exists
         * if it does, send it to the document registration page
         * if not send it back to the page and show an error message
         */
        String phoneNumToken = request.getParameter("verificationCode");
        if(PhoneNumTokenDAO.checkPhoneNumToken(phoneNumToken)){
            RequestDispatcher view = request.getRequestDispatcher("/html/register-proofdoc.html");
            view.forward(request, response);
        } else {
            request.setAttribute("failedVerification",true);
            RequestDispatcher view = request.getRequestDispatcher("/html/register-code.jsp");
            view.forward(request, response);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher view = request.getRequestDispatcher("/html/register-code.jsp");
        view.forward(request, response);
    }
}
