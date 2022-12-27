package com.final_project2.command;

import com.final_project2.db.DBException;
import com.final_project2.entity.User;
import com.final_project2.logic.UserManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;


public class RegisterCommand implements Command {

    Logger logger = Logger.getLogger(RegisterCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        String email = req.getParameter("email").trim();
        logger.info(email);
        String password = req.getParameter("password");
        logger.info(password);
        String firstName = req.getParameter("firstName");
        logger.info(firstName);
        String lastName = req.getParameter("lastName");
        logger.info(lastName);
        User user = new User();
        user.setId(0);
        user.setEmail(email.toLowerCase());
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        logger.info("input user is " + user);
        if (UserManager.getInstance().findUserByLogin(email) != null) {
            req.getSession().setAttribute("emailAlreadyExist", "This email is already exist");
            return "register_page.jsp";
        }

        boolean isSuccessfullyRegister = UserManager.getInstance().registerNewUser(user);

        logger.info("result of register is " + isSuccessfullyRegister);


        if (isSuccessfullyRegister) {
            req.getSession().setAttribute("afterRegMessage", "registration successful, please enter your username and password");
            return "login_page.jsp";
        }
        return "error_page.jsp";
    }
}
