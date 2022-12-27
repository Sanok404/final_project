package com.final_project2.command;

import com.final_project2.db.DBException;

import com.final_project2.entity.User;

import com.final_project2.logic.UserManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;


public class LoginCommand implements Command {
    private static final Logger log = Logger.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        String email = req.getParameter("email");
        log.trace("email: " + email);
        String password = req.getParameter("password");
        log.trace("password is: " + password);
        if (email.trim().isEmpty() || password.trim().isEmpty()) {
            req.getSession().setAttribute("message", "Login or password field is empty");
            log.info("reqAt " + req.getSession().getAttribute("message"));
            return "login_page.jsp";
        }
        User user = UserManager.getInstance().findUserByLogin(email.toLowerCase());
        if (user != null) {
            if (user.isBlockStatus()) {
                req.getSession().setAttribute("message", "You are blocked by admin");
                log.info("reqAt " + req.getSession().getAttribute("message"));
                return "login_page.jsp";
            }
            if (user.getPassword().equals(password)) {
                log.info("current user " + user);
                req.getSession().setAttribute("currentUser", user);
                log.info("go to admin page " + user);
                return "controller?command=showAllCars&page=1&pageSize=6";
            }
            req.getSession().setAttribute("message", "Login or password field is invalid");
            log.info("fail login with message" + req.getSession().getAttribute("message"));
            return "login_page.jsp";
        }
        req.getSession().setAttribute("message", "Cannot find user with this email");
        log.debug("fail login with message" + req.getSession().getAttribute("message"));
        return "login_page.jsp";

    }

}
