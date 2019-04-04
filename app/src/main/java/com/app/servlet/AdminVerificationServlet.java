package com.app.servlet;

import com.app.controller.VerificationTokenController;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "AdminVerificationServlet")
public class AdminVerificationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] accounts = request.getParameterValues("account_selection");
        ArrayList<String> list = new ArrayList(Arrays.asList(accounts));
        String voterID = list.get(0);
        int voterIDNum = Integer.parseInt(voterID);
        String path = getServletContext().getRealPath(".");
        //boolean tokenResult = VerificationTokenController.verifiedUser(voterIDNum, path);
        request.setAttribute("tokenResult",true);
        RequestDispatcher view = request.getRequestDispatcher("/html/admin-verificationSuccessful.jsp");
        view.forward(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher view = request.getRequestDispatcher("/html/admin-verification.html");
        view.forward(request, response);
    }
}
