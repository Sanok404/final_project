package com.final_project2.command;

import com.final_project2.db.DBException;
import com.final_project2.db.DBManager;
import com.final_project2.entity.Car;
import com.final_project2.logic.CarManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

public class ShowCardCarPageCommand implements Command{
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException, ServletException, IOException, SQLException {
        int id = Integer.parseInt(req.getParameter("id"));
        Car car = CarManager.getInstance().findCarById(id);
        req.getSession().setAttribute("currentCarToShow", car);
        if(req.getSession().getAttribute("newCarImageUrl")!=null){
            req.getSession().removeAttribute("newCarImageUrl");
        }

        return "card_car_page.jsp";
    }
}
