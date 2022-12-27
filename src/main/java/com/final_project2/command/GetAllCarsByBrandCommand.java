package com.final_project2.command;

import com.final_project2.db.DBException;
import com.final_project2.entity.Car;
import com.final_project2.logic.CarManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;

public class GetAllCarsByBrandCommand implements Command {
    private static final Logger log = Logger.getLogger(GetAllCarsByBrandCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException, ServletException, IOException {
        String brand = req.getParameter("brand");
        req.getSession().setAttribute("chooseValue", brand);
        String paramPage = req.getParameter("page");
        log.info("page " + paramPage);
        String paramPageSize = req.getParameter("pageSize");
        log.info("page size " + paramPageSize);
        int page = Integer.parseInt(paramPage);
        log.debug("int page " + page);
        int pageSize = Integer.parseInt(paramPageSize);
        log.debug("int page size " + pageSize);
        int size = CarManager.getInstance().getCarsSizeByBrand(brand);
        log.debug("cars size " + size);
        int pageCount = size / pageSize;
        log.debug("page count " + pageCount);
        List<Car> carsByBrand = CarManager.getInstance().getAllCarsByBrand(pageSize * (page - 1), pageSize, brand);
        log.debug("cars by brand " + carsByBrand);
        req.getSession().setAttribute("allCars", carsByBrand);
        req.getSession().setAttribute("queryVariations", "allCarsByBrandQuery");
        log.info("all cars " + carsByBrand);
        req.getSession().setAttribute("pageCount", pageCount);
        req.getSession().setAttribute("page", page);
        req.getSession().setAttribute("pageSize", pageSize);
        log.info("admin content " + req.getSession().getAttribute("adminContent"));
        if (req.getSession().getAttribute("orderBy") != null) {
            req.getSession().removeAttribute("orderBy");
        }
        return "admin_page.jsp";
    }
}
