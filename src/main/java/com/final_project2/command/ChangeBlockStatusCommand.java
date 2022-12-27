package com.final_project2.command;

import com.final_project2.db.DBException;
import com.final_project2.entity.User;
import com.final_project2.logic.UserManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.util.List;

public class ChangeBlockStatusCommand implements Command {
    private static final Logger log = Logger.getLogger(ChangeBlockStatusCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        String paramPage = req.getParameter("page");
        log.info("param page " + paramPage);
        String paramPageSize = req.getParameter("pageSize");
        log.info("param size" + paramPageSize);

        int page = Integer.parseInt(paramPage);
        int pageSize = Integer.parseInt(paramPageSize);

        String email = req.getParameter("email");
        log.info("current email " + email);
        User user = UserManager.getInstance().findUserByLogin(email);
        log.info("founded user by login " + user);

        if (user.isBlockStatus()) {
            log.info("user block status " + user.isBlockStatus());
            UserManager.getInstance().changeBlockStatus(user.getId(), false);
        } else {
            log.info("user block status " + user.isBlockStatus());
            UserManager.getInstance().changeBlockStatus(user.getId(), true);
        }
        List<User> users = UserManager.getInstance().findAllUsers(pageSize * (page - 1), pageSize);
        log.info("update all users " + req.getSession().getAttribute("allUsers"));
        req.setAttribute("users", users);
        log.info("users " + users);
        req.setAttribute("page", page);
        log.info("page  " + page);
        req.setAttribute("pageSize", pageSize);
        log.info("page size " + pageSize);

        return "controller?command=showAllUsers&page=" + paramPage + "&pageSize=" + paramPageSize;
    }
}
