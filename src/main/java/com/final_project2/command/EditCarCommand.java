package com.final_project2.command;

import com.final_project2.db.DBException;
import com.final_project2.logic.CarManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.io.IOException;

public class EditCarCommand implements Command {
    private static final Logger log = Logger.getLogger(EditCarCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException, ServletException, IOException {
        log.info("start edit car command");
        int id = Integer.parseInt(req.getParameter("id"));
        String newCarImageUrl;
        if (req.getSession().getAttribute("newCarImageUrl") != null) {
            newCarImageUrl = String.valueOf(req.getSession().getAttribute("newCarImageUrl"));
        } else {
            newCarImageUrl = req.getParameter("carImageUrl");
        }
        String carBrand = req.getParameter("brand");
        String carModel = req.getParameter("carModel");
        String transmission = req.getParameter("transmission");
        String carClass = req.getParameter("carClass");
        double price = Double.parseDouble(req.getParameter("price"));
        String info = req.getParameter("info");

        CarManager.getInstance().updateCar(id, newCarImageUrl, carBrand, carModel, transmission, carClass, price, info);

        if (req.getSession().getAttribute("newCarImageUrl") != null) {
            req.getSession().removeAttribute("newCarImageUrl");
        }


        return "controller?command=showCarCardPage&id=" + id;
    }
}
