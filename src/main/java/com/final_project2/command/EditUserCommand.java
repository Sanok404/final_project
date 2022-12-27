package com.final_project2.command;

import com.final_project2.db.DBException;
import com.final_project2.entity.User;
import com.final_project2.logic.UserManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class EditUserCommand implements Command {
    private static final Logger log = Logger.getLogger(EditUserCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        String paramPage = req.getParameter("page");
        log.info("param page " + paramPage);
        String paramPageSize = req.getParameter("pageSize");
        log.info("param size" + paramPageSize);
        String email = req.getParameter("email");
        User user = UserManager.getInstance().findUserByLogin(email);

        req.getSession().setAttribute("userToEdit", user);
        req.getSession().setAttribute("pageOfEdit", paramPage);
        req.getSession().setAttribute("pageSizeOfEdit", paramPageSize);
        log.info("user to edit " + req.getSession().getAttribute("userToEdit"));
        return "edit_user.jsp";
    }
}
