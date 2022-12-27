package com.final_project2.command;

import com.final_project2.db.DBException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Locale;

public class SetLocaleCommand implements Command {
    private static final Logger log = Logger.getLogger(SetLocaleCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException, ServletException, IOException {
        String[] planguage = req.getParameter("language").split("_");
        String language = planguage[0];
        log.trace("language " + language);
        String country = planguage[1];
        log.trace("country" + country);
        Locale locale = new Locale(language, country);
        req.getSession().setAttribute("country", locale.getDisplayCountry());
        req.getSession().setAttribute("language", req.getParameter("language"));

        return "admin_page.jsp";
    }
}
