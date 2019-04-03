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

@WebServlet(name = "VerifyDocumentServlet" , urlPatterns = {"/VoterVerified"})
public class VerifyDocumentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] accounts = request.getParameterValues("account_selection");
        ArrayList<String> list = new ArrayList(Arrays.asList(accounts));
        String voterID = list.get(0);
        int voterIDNum = Integer.parseInt(voterID);
        String path = getServletContext().getRealPath(".");
        if (VerificationTokenController.verifiedUser(voterIDNum, path)) {
            RequestDispatcher view = request.getRequestDispatcher("/html/VerifiedSuccess.html");
            view.forward(request, response);
        } else {
            RequestDispatcher view = request.getRequestDispatcher("/html/VerifiedFail.html");
            view.forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher view = request.getRequestDispatcher("/html/admin-verification.html");
        view.forward(request, response);
    }
}
