package com.app.servlet;

import com.app.dao.VerVoterDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "VerificationServlet", urlPatterns = {"/VoterVerification"})
public class VerificationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("full_name");
        String email = request.getParameter("email");
        String city = request.getParameter("city");
        String pNum = request.getParameter("phoneNum");
        String locationDocumentPath = "placeholder";
        VerVoterDAO.setVerVoter(pNum, city, locationDocumentPath, email, name);
        RequestDispatcher view = request.getRequestDispatcher("/html/Verification.html");
        view.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher view = request.getRequestDispatcher("/html/Verification.html");
        view.forward(request, response);
    }
}
