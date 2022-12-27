package com.final_project2.command;


import com.final_project2.db.DBException;
import com.final_project2.logic.OrderManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.io.IOException;

public class SetInfoAboutDamageCommand implements Command {
    private static final Logger log = Logger.getLogger(SetInfoAboutDamageCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException, ServletException, IOException {
        int orderId = Integer.parseInt(req.getParameter("orderId"));
        double damageCost = Double.parseDouble(req.getParameter("damageCost"));
        log.trace("damage cost "+ damageCost);
        String managersComment = req.getParameter("managersComment");

        OrderManager.getInstance().setInfoAboutDamage(orderId, damageCost, managersComment);


        return "controller?command=showCardOrderPage&id=" + orderId;
    }
}
