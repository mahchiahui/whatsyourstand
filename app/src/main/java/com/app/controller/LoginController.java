package com.app.controller;

import com.app.dao.CookieDao;
import com.app.entity.User;
import com.app.dao.UserDAO;
import com.app.utility.DateUtil;
import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class LoginController {

    /**
     * Login functionality
     * @param username
     * @param pwd
     * @param rememberMe
     * @return
     */
    public static User login (HttpServletResponse response, String username, String pwd, boolean rememberMe) {
        // step1: call searchUserByName
        User user = UserDAO.searchUserByName(username);

        if (user != null && BCrypt.checkpw(pwd, user.getHashpwd())) {
            if (rememberMe) {
                // generate a long, unique, hard-to-guess key (which is in no way related to the user)
                // which represents the cookie_id and store this in the DB along with the user_id
                String cookieId = generateToken();
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
     * Obtain cookieid of certain userid
     * @param request
     * @param userid
     * @return
     */
    public static String getCookieByCookieId (HttpServletRequest request, String userid) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (userid.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
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
     * creates a token with 130 random bits put into string of base 32
     * @return
     */
    public static String generateToken () {
        SecureRandom rand;
        String token;
        try {
            rand = SecureRandom.getInstanceStrong();
            token = new BigInteger(130,rand).toString(32);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to instantiate random number generator", e);
        }
        return token;
    }
}
