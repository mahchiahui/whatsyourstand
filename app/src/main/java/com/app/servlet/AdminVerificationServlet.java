package com.app.servlet;

import com.app.controller.LoginController;
import com.app.controller.RedirectController;
import com.app.controller.VerificationTokenController;
import com.app.dao.CookieDao;
import com.app.dao.UserDAO;
import com.app.dao.VerVoterDAO;
import com.app.entity.Cookie;
import com.app.entity.Rootuser;
import com.app.entity.VerVoter;
import com.app.utility.Constants;
import com.app.utility.DateUtil;
import com.app.utility.SendEmailTLS;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import static com.app.dao.VerVoterDAO.getAllVerVoter;

/**
 * This servlet handles HTTP request for admin's voter verification dashboard page.
 * doPost function handles voter account approval when all voter records with the checkbox selected
 * are sent back as a list of string.
 * if the admin chooses to deny the voter's verification documents, the voter will receive an email informing them about the denial and an email to contact for more information
 * doGet function handles page display on url "/admin-verification". It checks the login status first.
 * If login status exists, read all the unverified voter records
 * from the database and dynamically display this page using jsp.
 */
public class AdminVerificationServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
         * takes the array of accounts that the admin has approved
         * convert it to an Arraylist
         * get the voterID
         */

        String option = request.getParameter("option");
        if(option.equals("Approve")) {
            String[] accounts = request.getParameterValues("account_selection");
            ArrayList<String> list = new ArrayList(Arrays.asList(accounts));
            for (String voterID : list) {
                int voterIDNum = Integer.parseInt(voterID);
                String path = getServletContext().getRealPath(".");
                /**
                 * sends token to Q&A database
                 */
                boolean tokenResult = VerificationTokenController.verifiedUser(voterIDNum, path);
                if (tokenResult) {
                    VerVoterDAO.deleteVerVoter(list);
                }
                request.setAttribute("tokenResult", tokenResult);
                RequestDispatcher view = request.getRequestDispatcher("/html/admin-verificationSuccessful.jsp");
                view.forward(request, response);
            }
        } else {
            String[] accounts = request.getParameterValues("account_selection");
            ArrayList<String> list = new ArrayList(Arrays.asList(accounts));
            for (String voterID : list) {
                VerVoter verVoter = VerVoterDAO.getVerVoter(Integer.parseInt(voterID));
                String message = "Dear " + verVoter.getName() + ", \n\nYour request for verification has been denied\n";
                message += "Please email whatsyourstandeps@gmail.com to enquire about the reason for denial\n";
                message += "\n\nRegards\nWhatsYourStand";
                SendEmailTLS.sendEmail(message,"Verification Failure","whatsyourstandtest@gmail.com");
            }
            VerVoterDAO.deleteVerVoter(list);
            response.sendRedirect(request.getContextPath() + "/admin-verification");
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //read HttpSession.
        //1) if exist, read the user object within it, role check who you are
        //   1.1 if you are the role accessing pages belonging to your role, no redirection
        //   1.2 if you are the role accessing pages not belonging to your role, redirect you to login page
        //2) if it doesn't exist, but has valid cookie
        //   same as above
        //3) if it doesn't exist, and doesn't have valid cookie
        //  2.1 if you are on login pages, no redirection
        //  2.2 if you are not on login pages, redirect you to /login-voter

        // Get HttpSession object
        HttpSession session = request.getSession(false);
        Rootuser loginedInfo = null;

        boolean isCookieValid = false;
        String cookieid = LoginController.getCookieId(request);
        if (cookieid != null) {
            Cookie cookie = CookieDao.searchCookie(cookieid);
            String curTime = DateUtil.getCurrentTime();

            // Cookie is valid in db and has not expired
            if (cookie != null &&
                DateUtil.isTimeDiffLessThanOneDay(curTime, cookie.getTimestamp())) {

                String userid = cookie.getUserId();
                Rootuser user = UserDAO.searchUserById(userid);
                if (user.getRole() == 0) {
                    loginedInfo = user;
                    isCookieValid = true;
                }
            }
        }

        if (! isCookieValid) {
            // Old local storage needs to be cleared
            LoginController.clearLoginCookie(request, response);
        }

        if ((session == null && isCookieValid) || // no session, but has cookie
            // has session and has login
            (session != null && (loginedInfo = (Rootuser) session.getAttribute(Constants.SESSION_USER_KEY)) != null &&
                (loginedInfo.getRole() == 0))) {

            if (session == null) {
                session = request.getSession();
                session.setAttribute(Constants.SESSION_USER_KEY, loginedInfo);
            }
            ArrayList<VerVoter> allVerVoter = VerVoterDAO.getAllVerVoter();
            request.setAttribute("allVerVoter",allVerVoter);
            RedirectController.showFrontEnd(request, response, "/html/admin-verification.jsp");
        }
        else {
            RedirectController.redirectToLoginPage(request, response);
        }

    }
}
