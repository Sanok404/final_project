package com.final_project2.command;

import com.final_project2.db.DBException;
import com.final_project2.entity.User;
import com.final_project2.logic.UserManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.util.List;

public class UpdateUserCommand implements Command {
    private static final Logger log = Logger.getLogger(UpdateUserCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        int id = Integer.parseInt(req.getParameter("id"));
        log.info("update user id " + id);
        String email = req.getParameter("email");
        log.info("update user email " + email);
        String firstName = req.getParameter("firstName");
        log.info("update user first name " + firstName);
        String lastName = req.getParameter("lastName");
        log.info("update user last name " + lastName);
        String role = req.getParameter("role");
        log.info("update user role " + role);

        String paramPage = (String) req.getSession().getAttribute("pageOfEdit");
        log.info("page " + paramPage);

        String paramPageSize = (String) req.getSession().getAttribute("pageSizeOfEdit");
        log.info("pageSize " + paramPageSize);
        int page = Integer.parseInt(paramPage);
        int pageSize = Integer.parseInt(paramPageSize);

        boolean isUpdated = UserManager.getInstance().updateUser(email, firstName, lastName, role, id);

        log.info("is updated? " + isUpdated);

        if (isUpdated) {
            req.getSession().setAttribute("updateMessage", "Update is successful");
        } else {
            req.getSession().setAttribute("updateMessage", "Update failed");
        }
        List<User> users = UserManager.getInstance().findAllUsers(pageSize * (page - 1), pageSize);
        log.info("update all users " + req.getSession().getAttribute("allUsers"));
        req.setAttribute("users", users);
        log.info("users " + users);
        req.setAttribute("page", page);
        log.info("page  " + page);
        req.setAttribute("pageSize", pageSize);
        log.info("page size " + pageSize);

        return "controller?command=showAllUsers&page=" + page + "&pageSize=" + pageSize;
    }
}
