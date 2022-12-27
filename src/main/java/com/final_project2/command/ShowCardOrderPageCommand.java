package com.final_project2.command;

import com.final_project2.db.DBException;
import com.final_project2.entity.Order;
import com.final_project2.logic.OrderManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.io.IOException;

public class ShowCardOrderPageCommand implements Command {
    private static final Logger log = Logger.getLogger(ShowCardOrderPageCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException, ServletException, IOException {
        int orderId = Integer.parseInt(req.getParameter("id"));
        log.trace("order id " + orderId);
        Order order = OrderManager.getInstance().getOrderById(orderId);
        log.trace("current order " + order);
        String pdfOrder = OrderManager.getInstance().createPdfOrder(req, orderId);
        log.trace(pdfOrder);
        req.getSession().setAttribute("pdfOrder", pdfOrder);
        if (order.isDamaged()) {
            String pdfDamageOrder = OrderManager.getInstance().createPdfDamageOrder(req, orderId);
            log.trace(pdfDamageOrder);
            req.getSession().setAttribute("pdfDamageOrder", pdfDamageOrder);
        }
        req.setAttribute("currentOrder", order);
        log.debug(" request attribute current order " + req.getAttribute("currentOrder"));
        return "card_order_page.jsp";
    }
}
