package com.final_project2.command;

import com.final_project2.db.DBException;
import com.final_project2.entity.Car;
import com.final_project2.logic.CarManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.util.List;

public class ShowAllCarsOrderByPriceAscCommand implements Command {
    private static final Logger log = Logger.getLogger(ShowAllCarsOrderByPriceAscCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        String paramPage = req.getParameter("page");
        log.info("page" + paramPage);
        String paramPageSize = req.getParameter("pageSize");
        log.info("page size " + paramPageSize);
        int page = Integer.parseInt(paramPage);
        int pageSize = Integer.parseInt(paramPageSize);
        int size = CarManager.getInstance().getCarsSize();
        int pageCount = size / pageSize;
        List<Car> carsOrderByPriceAsc = CarManager.getInstance().getAllCarsOrderByPriceAsc(pageSize * (page - 1), pageSize);
        req.getSession().setAttribute("allCars", carsOrderByPriceAsc);
        req.getSession().setAttribute("queryVariations", "carsOrderByPriceAsc");
        log.info("all cars order by Price ask " + carsOrderByPriceAsc.toString());
        req.getSession().setAttribute("pageCount", pageCount);
        req.getSession().setAttribute("orderBy", "sort by increasing price");
        req.getSession().setAttribute("page", page);
        req.getSession().setAttribute("pageSize", pageSize);
        log.info("admin content " + req.getSession().getAttribute("adminContent"));
        if (req.getSession().getAttribute("selectByGroup") != null) {
            req.getSession().removeAttribute("selectByGroup");
        }
        if (req.getSession().getAttribute("chooseValue") != null) {
            req.getSession().removeAttribute("chooseValue");
        }
        return "admin_page.jsp";
    }
}
