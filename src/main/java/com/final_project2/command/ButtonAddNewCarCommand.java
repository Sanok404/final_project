package com.final_project2.command;

import com.final_project2.db.DBException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ButtonAddNewCarCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException, ServletException, IOException {
        if (req.getSession().getAttribute("newCarImageUrl") != null) {
            req.getSession().removeAttribute("newCarImageUrl");
        }
        return "add_car.jsp";
    }
}
