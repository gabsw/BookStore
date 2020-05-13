package tqs.group4.bestofbooks.security;

import javax.servlet.http.HttpSession;

import tqs.group4.bestofbooks.exception.LoginRequiredException;

public class SecurityUtils {
	private SecurityUtils(){}

    public static String getUserIdentity(HttpSession session) throws LoginRequiredException {
        String username = (String) session.getAttribute("username");

        if (username == null) {
            throw new LoginRequiredException("Login required.");
        }

        return username;
    }
}
