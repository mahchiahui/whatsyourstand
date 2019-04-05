package com.app.controller;

import com.app.dao.CookieDao;
import com.app.dao.UserDAO;
import com.app.entity.Rootuser;
import com.app.utility.DateUtil;
import com.app.utility.TokenGenerator;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class LoginController {

    /**
     * Login functionality
     * @param username
     * @param pwd
     * @param rememberMe
     * @return
     */
    public static Rootuser login (HttpServletResponse response, String username, String pwd, int role, boolean rememberMe) {
        // step1: call searchUserByName
        Rootuser user = UserDAO.searchUserByName(username);

        if (user != null && BCrypt.checkpw(pwd, user.getHashpwd()) && user.getRole() == role) {
            if (rememberMe) {
                // generate a long, unique, hard-to-guess key (which is in no way related to the user)
                // which represents the cookie_id and store this in the DB along with the user_id
                String cookieId = TokenGenerator.generateToken();
                String curTime = DateUtil.getCurrentTime();
                // locally, cookie is stored as {name: "whatsyourstand", value: cookieid}
                addCookie(response, "whatsyourstand", cookieId, Integer.MAX_VALUE);
                CookieDao.insertCookie(cookieId, Integer.toString(user.getUserId()), curTime);
            }
            return user;
        }
        return null;
    }


    /**
     * Logout by clearing local cookie storage and session,
     * and redirecting user to login page
     * @param request
     * @param response
     * @param context
     * @throws IOException
     */
    public static void logout (HttpServletRequest request, HttpServletResponse response,
                               ServletContext context) throws IOException {
        //get the old session and invalidate
        HttpSession oldSession = request.getSession(false);
        if (oldSession != null) {
            oldSession.invalidate();
        }
        clearLoginCookie(request, response);
        response.sendRedirect(context.getContextPath() + "/login-voter");
    }


    /**
     * Add cookie to storage
     * @param response
     * @param name
     * @param value
     * @param maxAge
     */
    public static void addCookie (HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
        /*
        // Cookie cookie = new Cookie("JSESSIONID", session.getId());
        Cookie cookie = new Cookie(Integer.toString(user.getUserId()), sessionId);
        // Set the “secure” flag in a cookie
        // so that browsers will send the “secure” cookies only through HTTPS channels
//            cookie.setSecure(true);
        // “HttpOnly” flag in a cookie makes it inaccessible to JavaScript’s Document.cookie API.
        // This can help in preventing Cross Site Scripting (XSS) attacks.
//            cookie.setHttpOnly(true);
        cookie.setMaxAge(Integer.MAX_VALUE);
        response.addCookie(cookie);
        */
    }


    /**
     * Remove cookie from storage by setting its maxAge to 0
     * @param response
     * @param name
     */
    public static void removeCookie (HttpServletResponse response, String name) {
        addCookie(response, name, null, 0);
    }


    public static void clearLoginCookie (HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("whatsyourstand")) {
                    removeCookie(response, "whatsyourstand");
                }
            }
        }
    }


    /**
     * Obtain hardcoded local cookieid
     * @param request
     * @return
     */
    public static String getCookieId (HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("whatsyourstand")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }


    /**
     *
     * @param request
     * @param role
     * @return
     */
    public static boolean checkCookieValid (HttpServletRequest request, int role) {
        boolean isCookieValid = false;
        String cookieid = getCookieId(request);
        if (cookieid != null) {
            com.app.entity.Cookie cookie = CookieDao.searchCookie(cookieid);
            String curTime = DateUtil.getCurrentTime();

            // Cookie is valid in db and has not expired
            if (cookie != null &&
                DateUtil.isTimeDiffLessThanOneDay(curTime, cookie.getTimestamp())) {

                String userid = cookie.getUserId();
                Rootuser user = UserDAO.searchUserById(userid);
                if (user.getRole() == role) {
                    isCookieValid = true;
                }
            }
        }
        return isCookieValid;
    }

}
