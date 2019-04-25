package com.app.servlet;

import com.app.controller.LoginController;
import com.app.controller.RedirectController;
import com.app.dao.CookieDao;
import com.app.dao.QuestionDAO;
import com.app.dao.UserDAO;
import com.app.entity.Cookie;
import com.app.entity.Question;
import com.app.entity.ReportedQuestion;
import com.app.entity.Rootuser;
import com.app.utility.Constants;
import com.app.utility.DateUtil;
import com.app.utility.SendEmailTLS;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

//@WebServlet(name = "AdminMainpageServlet")
public class AdminMainpageServlet extends HttpServlet {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(QuestionDAO.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] reportedQuestionSelections = request.getParameterValues("reportedQuestionSelection");
        ArrayList<String> reportedQuestionSelectionsAL = new ArrayList(Arrays.asList(reportedQuestionSelections));
        for (String questionID: reportedQuestionSelectionsAL) {
            ReportedQuestion reportedQuestion = QuestionDAO.getReportedQuestion(Integer.parseInt(questionID));
            String message = "Dear Voter, \n\nYour question has been flagged for " + reportedQuestion.getReportedIssue() + ".\n";
            message += "After investigation, the WhatsYourStand staff have decided to delete the question as it violates our community guidelines.\n";
            message += "We encourage you to keep asking, but using language that is appropriate for a civil discussion.\n\nRegards\nWhatsYourStand";
            SendEmailTLS.sendEmail(message,"Reported Question","whatsyourstandtest@gmail.com");

            QuestionDAO.deleteReportedQuestion(Integer.parseInt(questionID));
            QuestionDAO.deleteQuestion(Integer.parseInt(questionID));
        }

        response.sendRedirect(request.getContextPath() + "/admin");
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
            logger.info("user " + loginedInfo.getUserId() + " has accessed AdminMainpageServlet");
            ArrayList<ReportedQuestion> reportedQuestions = QuestionDAO.getAllReportedQuestion();
            request.setAttribute("reportedQuestions",reportedQuestions);
            RedirectController.showFrontEnd(request, response, "/html/admin-Q&A.jsp");
        }
        else {
            RedirectController.redirectToLoginPage(request, response);
        }

    }
}
