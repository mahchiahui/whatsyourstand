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

/**
 * This servlet handles HTTP request on url "/adminDeleteVoterAccount" for admin's account page.
 * doPost function handles voter accounts deletion. Parameters are passed as a string list.
 * After deleting account in the list, refresh admin's account page.
 */
public class AdminDeleteVoterAccServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] voterAccounts = request.getParameterValues("voter_selection");
        ArrayList<String> voterAccountsAL = new ArrayList(Arrays.asList(voterAccounts));
        for (String userid: voterAccountsAL){
            UserDAO.deleteVoter(Integer.parseInt(userid));
        }
        response.sendRedirect(request.getContextPath() + "/admin-accounts");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
