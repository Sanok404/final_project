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

public class GetAllCarsByClassCommand implements Command {
    private static final Logger log = Logger.getLogger(GetAllCarsByClassCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException, ServletException, IOException {
        String carClass = req.getParameter("carClass");
        req.getSession().setAttribute("chooseValue", carClass);
        String paramPage = req.getParameter("page");
        log.info("page " + paramPage);
        String paramPageSize = req.getParameter("pageSize");
        log.info("page size " + paramPageSize);
        int page = Integer.parseInt(paramPage);
        log.debug("int page " + page);
        int pageSize = Integer.parseInt(paramPageSize);
        log.debug("int page size " + pageSize);
        int size = CarManager.getInstance().getCarsSizeByClass(carClass);
        log.debug("cars size " + size);
//        int pageCount = (int) Math.ceil(size / pageSize);
        int pageCount = size / pageSize;
        log.debug("page count " + pageCount);
        List<Car> carsByClass = CarManager.getInstance().getAllCarsByClass(pageSize * (page - 1), pageSize, carClass);
        log.debug("cars by brand " + carsByClass);
        req.getSession().setAttribute("allCars", carsByClass);
        req.getSession().setAttribute("queryVariations", "allCarsByClassQuery");
        log.info("all cars " + carsByClass.toString());
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
