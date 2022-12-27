package com.final_project2.command;

import com.final_project2.db.DBException;
import com.final_project2.logic.OrderManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.io.IOException;

public class SetNewOrderStatusCommand implements Command {
    private static final Logger log = Logger.getLogger(SetNewOrderStatusCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException, ServletException, IOException {
        int orderId = Integer.parseInt(req.getParameter("orderId"));
        log.debug("current order id " + orderId);
        String newStatus = req.getParameter("newStatus");
        log.debug("new status " + newStatus);
        OrderManager.getInstance().setNewOrderStatus(orderId, newStatus);


        return "controller?command=showCardOrderPage&id=" + orderId;
    }
}
