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

/**
 * Controller class for login and logout operations in business logic layer.
 * Utilize utility class to obtain system's current date time and random string.
 * Utilize external library jBCrypt for hashing and validating password.
 * Manipulate local cookie storage to record and validate login status.
 */
public class LoginController {

    /**
     * Login functionality.
     * @param response HTTP Response object passed from the Servlet
     * @param username value of username textfield
     * @param pwd value of password textfield
     * @param role value of user's chosen login role
     * @param rememberMe if true, the user don't need to login again in 24 hours
     * @return Root user object login succeeds, otherwise null
     */
    public static Rootuser login (HttpServletResponse response, String username, String pwd, int role, boolean rememberMe) {
        // search for user in database using username
        Rootuser user = UserDAO.searchUserByName(username);

        // check whether user exists, input password matches, and role is correct
        if (user != null && BCrypt.checkpw(pwd, user.getHashpwd()) && user.getRole() == role) {
            if (rememberMe) {
                // generate a long, unique, hard-to-guess key (which is in no way related to the user)
                // which represents the cookie_id and store this in the DB along with the user_id
                String cookieId = TokenGenerator.getAlphaNumeric(32);
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
     * Logout by clearing local cookie storage and previous session,
     * and redirecting user to login page.
     * @param request HTTP Request object passed from the Servlet
     * @param response HTTP Response object passed from the Servlet
     * @param context Servlet context
     * @throws IOException
     */
    public static void logout (HttpServletRequest request, HttpServletResponse response,
                               ServletContext context) throws IOException {
        // get the old session and invalidate
        HttpSession oldSession = request.getSession(false);
        if (oldSession != null) {
            oldSession.invalidate();
        }
        clearLoginCookie(request, response);
        response.sendRedirect(context.getContextPath() + "/login-voter");
    }


    /**
     * Add cookie to storage, and set its expected lifetime to be permanent.
     * @param response HTTP Response object passed from the Servlet
     * @param name cookie's name
     * @param value cookie's value
     * @param maxAge cookie's expected lifetime
     */
    public static void addCookie (HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }


    /**
     * Remove cookie from storage by setting its expected lifetime to 0.
     * @param response HTTP Response object passed from the Servlet
     * @param name cookie's name
     */
    public static void removeCookie (HttpServletResponse response, String name) {
        addCookie(response, name, null, 0);
    }


    /**
     * Clear local cookie storage.
     * @param request HTTP Request object passed from the Servlet
     * @param response HTTP Response object passed from the Servlet
     */
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
     * Read cookieid from local cookie storage if there is any.
     * @param request HTTP Request object passed from the Servlet
     * @return cookie id if there is any, otherwise null
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

}
