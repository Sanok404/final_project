package com.final_project2.command;

import com.final_project2.db.DBException;
import com.final_project2.entity.User;
import com.final_project2.logic.UserManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.util.List;

public class ShowAllUsersCommand implements Command {
    private static final Logger log = Logger.getLogger(ShowAllUsersCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        String paramPage = req.getParameter("page");
        log.info("page" + paramPage);
        String paramPageSize = req.getParameter("pageSize");
        log.info("page size " + paramPageSize);
        int page = Integer.parseInt(paramPage);
        int pageSize = Integer.parseInt(paramPageSize);
        int size = UserManager.getInstance().getUsersSize();
        int pageCount = size / pageSize;
        List<User> users = UserManager.getInstance().findAllUsers(pageSize * (page - 1), pageSize);
        req.getSession().setAttribute("allUsers", users);
        req.getSession().setAttribute("adminContent", "users");
        log.info("all users " + users.toString());
        req.getSession().setAttribute("users", users);
        req.getSession().setAttribute("pageCount", pageCount);
        req.getSession().setAttribute("page", page);
        req.getSession().setAttribute("pageSize", pageSize);
        if (req.getSession().getAttribute("updateMessage") != null) {
            req.getSession().removeAttribute("updateMessage");
        }

        return "admin_page.jsp";
    }
}
