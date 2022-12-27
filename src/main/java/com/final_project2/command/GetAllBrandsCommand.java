package com.final_project2.command;

import com.final_project2.db.DBException;
import com.final_project2.logic.CarManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Set;


public class GetAllBrandsCommand implements Command {
    private static final Logger log = Logger.getLogger(GetAllBrandsCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException, ServletException, IOException {
        Set<String> allBrands = CarManager.getInstance().getAllBrands();
        req.getSession().setAttribute("selectByGroup", allBrands);
        req.getSession().setAttribute("choose", "Brand");
        log.debug("selected group in session " + req.getSession().getAttribute("selectByGroup"));
        return "admin_page.jsp";
    }
}
