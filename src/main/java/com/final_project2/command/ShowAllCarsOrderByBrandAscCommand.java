package com.final_project2.command;

import com.final_project2.db.DBException;
import com.final_project2.entity.Car;
import com.final_project2.logic.CarManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.util.List;

public class ShowAllCarsOrderByBrandAscCommand implements Command {
    private static final Logger log = Logger.getLogger(ShowAllCarsOrderByBrandAscCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        String paramPage = req.getParameter("page");
        log.info("page" + paramPage);
        String paramPageSize = req.getParameter("pageSize");
        log.info("page size " + paramPageSize);
        int page = Integer.parseInt(paramPage);
        int pageSize = Integer.parseInt(paramPageSize);
        int size = CarManager.getInstance().getCarsSize();
//        int pageCount = (int) Math.ceil(size / pageSize);
        int pageCount = size / pageSize;
        List<Car> carsOrderByBrandAsc = CarManager.getInstance().getAllCarsOrderByBrandAsc(pageSize * (page - 1), pageSize);
        req.getSession().setAttribute("allCars", carsOrderByBrandAsc);
        req.getSession().setAttribute("queryVariations", "carsOrderByBrandAsc");
        log.info("all cars order by brand ask " + carsOrderByBrandAsc.toString());
        req.getSession().setAttribute("pageCount", pageCount);
        req.getSession().setAttribute("orderBy", "sort by brand A-Z");
        req.getSession().setAttribute("page", page);
        req.getSession().setAttribute("pageSize", pageSize);
        if (req.getSession().getAttribute("selectByGroup") != null) {
            req.getSession().removeAttribute("selectByGroup");
        }
        if (req.getSession().getAttribute("chooseValue") != null) {
            req.getSession().removeAttribute("chooseValue");
        }
        return "admin_page.jsp";
    }
}
