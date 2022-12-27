package com.final_project2.command;

import com.final_project2.db.DBException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LogoutCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.removeAttribute("currentUser");
        }

        return "index.jsp";
    }
}
