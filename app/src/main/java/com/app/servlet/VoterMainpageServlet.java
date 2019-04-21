package com.app.servlet;

import com.app.controller.LoginController;
import com.app.controller.RedirectController;
import com.app.dao.AnswerDAO;
import com.app.dao.CookieDao;
import com.app.dao.QuestionDAO;
import com.app.dao.UserDAO;
import com.app.entity.*;
import com.app.utility.Constants;
import com.app.utility.DateUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.registry.infomodel.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//@WebServlet(name = "VoterMainpageServlet")
public class VoterMainpageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
                if (user.getRole() == 1) {
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
                (loginedInfo.getRole() == 1))) {

            if (session == null) {
                session = request.getSession();
                session.setAttribute(Constants.SESSION_USER_KEY, loginedInfo);
            }
            System.out.println(loginedInfo.getUserId());


            List<List<Answer>> answersList = new ArrayList<>();
            List<List<Candidate>> candidatesList = new ArrayList<>();
            List<Question> questions = QuestionDAO.readTopQuestionList();

            for (Question question : questions) {
                List<Answer> answers = AnswerDAO.readAnswerList(question);
                answersList.add(answers);

                List<Candidate> candidates = new ArrayList<>();
                for (Answer answer : answers) {
                    candidates.add(UserDAO.searchCandidateById(answer.getUserId()));
                }
                candidatesList.add(candidates);
            }

            request.setAttribute("question_list", questions);
            request.setAttribute("answer_list_of_list", answersList);
            request.setAttribute("candidate_list_of_list", candidatesList);

            // RedirectController.showFrontEnd(request, response, "/html/voter-topQ&A.html");
            RedirectController.showFrontEnd(request, response, "/html/voter-topQ&A.jsp");
        }
        else {
            RedirectController.redirectToLoginPage(request, response);
        }

    }
}
