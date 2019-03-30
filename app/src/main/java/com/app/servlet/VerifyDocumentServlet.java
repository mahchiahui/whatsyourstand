package com.app.servlet;

import com.app.controller.VerificationTokenController;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "VerifyDocumentServlet" , urlPatterns = {"/VoterVerified"})
public class VerifyDocumentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String voterID = request.getParameter("voterID");
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
        RequestDispatcher view = request.getRequestDispatcher("/html/verified.html");
        view.forward(request, response);
    }
}
