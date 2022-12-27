package com.final_project2.command;

import com.final_project2.db.DBException;
import com.final_project2.entity.Car;
import com.final_project2.entity.Order;
import com.final_project2.entity.User;
import com.final_project2.logic.OrderManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class AddNewOrderCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException, ServletException, IOException {
        User user = (User) req.getSession().getAttribute("currentUser");
        Car car = (Car) req.getSession().getAttribute("currentCarToShow");
        String passport = String.valueOf(req.getSession().getAttribute("passport"));
        String driverService = String.valueOf(req.getSession().getAttribute("withADriver"));
        String dateStart = String.valueOf(req.getSession().getAttribute("dateStart"));
        String dateEnd = String.valueOf(req.getSession().getAttribute("dateEnd"));
        double cost = Double.parseDouble(String.valueOf(req.getSession().getAttribute("cost")));

        Order order = new Order();
        order.setCar(car);
        order.setUser(user);
        order.setSeriesAndNumberOfThePassport(passport);
        if (driverService.equals("Yes")) {
            order.setWithADriver(true);
        }
        if (driverService.equals("No")) {
            order.setWithADriver(false);
        }
        order.setDateStart(dateStart);
        order.setDateEnd(dateEnd);
        order.setCost(cost);
        order.setDamaged(false);
        order.setDamageCost(0);
        order.setStatus(Order.Status.AWAITING_PAYMENT);
        order.setManagersComment(null);
        OrderManager.getInstance().addNewOrder(order);
        if (req.getSession().getAttribute("passport") != null) {
            req.getSession().removeAttribute("passport");
        }
        if (req.getSession().getAttribute("withADriver") != null) {
            req.getSession().removeAttribute("withADriver");
        }
        if (req.getSession().getAttribute("dateStart") != null) {
            req.getSession().removeAttribute("dateStart");
        }
        if (req.getSession().getAttribute("dateEnd") != null) {
            req.getSession().removeAttribute("dateEnd");
        }
        if (req.getSession().getAttribute("cost") != null) {
            req.getSession().removeAttribute("cost");
        }


        return "controller?command=showAllOrders&page=1&pageSize=6";
    }
}
