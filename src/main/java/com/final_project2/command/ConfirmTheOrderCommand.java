package com.final_project2.command;

import com.final_project2.db.DBException;
import com.final_project2.entity.Car;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class ConfirmTheOrderCommand implements Command {
    private static final Logger log = Logger.getLogger(ConfirmTheOrderCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException, ServletException, IOException {
        log.info("Start confirm order command");
        Car car = (Car) req.getSession().getAttribute("currentCarToShow");
        String passport = req.getParameter("passport");
        req.getSession().setAttribute("passport", passport);
        String driverService = req.getParameter("withADriver");
        req.getSession().setAttribute("withADriver", driverService);
        String dateStart = req.getParameter("dateStart");
        req.getSession().setAttribute("dateStart", dateStart);
        String dateEnd = req.getParameter("dateEnd");
        req.getSession().setAttribute("dateEnd", dateEnd);
        Date dateStartDate;
        Date dateEndDate;
        try {
            dateStartDate = new SimpleDateFormat("dd-MM-yyyy").parse(dateStart);
            dateEndDate = new SimpleDateFormat("dd-MM-yyyy").parse(dateEnd);
        } catch (ParseException e) {
            throw new DBException("Some problem with date", e);
        }
        int days = Math.toIntExact(ChronoUnit.DAYS.between(dateStartDate.toInstant(), dateEndDate.toInstant()));

        if (days < 1) {
            req.setAttribute("incorrectDate", "period entered incorrectly");
            return "create_order.jsp";
        }
        req.getSession().setAttribute("days", days);

        double cost = days * car.getPrice();

        if (driverService.equals("Yes")) {
            cost += (500 * days);
        }
        req.getSession().setAttribute("cost", cost);
        return "confirm_order_page.jsp";
    }
}
