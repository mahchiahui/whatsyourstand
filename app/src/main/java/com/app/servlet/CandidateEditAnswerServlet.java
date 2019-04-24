package com.app.servlet;

import com.app.controller.LoginController;
import com.app.controller.RedirectController;
import com.app.dao.AnswerDAO;
import com.app.dao.CookieDao;
import com.app.dao.UserDAO;
import com.app.entity.Answer;
import com.app.entity.Cookie;
import com.app.entity.Rootuser;
import com.app.utility.Constants;
import com.app.utility.DateUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

@WebServlet(name = "CandidateEditAnswerServlet")
public class CandidateEditAnswerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String answerContent = request.getParameter("answerContent");
        String answerID = request.getParameter("answerID");
        Answer answer = AnswerDAO.getAnswer(answerID);

        //generate new timestamp
        Date date= new Date();
        Timestamp ts = new Timestamp(date.getTime());

        answer.setLastModifiedTime(ts.toString());
        answer.setContent(answerContent);

        AnswerDAO.updateAnswer(answer);
        response.sendRedirect(request.getContextPath() + "/candidate");
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
                if (user.getRole() == 2) {
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
                        (loginedInfo.getRole() == 2))) {

            if (session == null) {
                session = request.getSession();
                session.setAttribute(Constants.SESSION_USER_KEY, loginedInfo);
            }
            System.out.println(loginedInfo.getRole());
        }

        //getAnswerID
        String answerID = request.getParameter("answerID");
        Answer answer = AnswerDAO.getAnswer(answerID);

        request.setAttribute("candidateAnswer", answer);
        RedirectController.showFrontEnd(request, response, "/html/candidate-update-answer.jsp");
    }
}
