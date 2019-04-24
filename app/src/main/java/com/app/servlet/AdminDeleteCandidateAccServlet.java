package com.app.servlet;

import com.app.dao.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

@WebServlet(name = "AdminDeleteCandidateAccServlet")
public class AdminDeleteCandidateAccServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] candidateSelections = request.getParameterValues("candidate_selection");
        ArrayList<String> candidateAccountsAL = new ArrayList(Arrays.asList(candidateSelections));
        for (String userid: candidateAccountsAL){
            UserDAO.deleteCandidate(Integer.parseInt(userid));
        }
        response.sendRedirect(request.getContextPath() + "/admin-accounts");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
