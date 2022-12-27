package com.final_project2.command;

import com.final_project2.db.DBException;
import com.final_project2.entity.Order;
import com.final_project2.entity.User;
import com.final_project2.logic.OrderManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.util.List;

public class ShowAllOrdersCommand implements Command {
    private static final Logger log = Logger.getLogger(ShowAllOrdersCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        User user = (User) req.getSession().getAttribute("currentUser");
        String paramPage = req.getParameter("page");
        log.info("page" + paramPage);
        String paramPageSize = req.getParameter("pageSize");
        log.info("page size " + paramPageSize);
        int page = Integer.parseInt(paramPage);
        int pageSize = Integer.parseInt(paramPageSize);
        int size;
        if (user.getRole().equals(User.Role.ADMIN) || user.getRole().equals(User.Role.MANAGER)) {
            size = OrderManager.getInstance().getAllOrdersSize();
        } else {
            size = OrderManager.getInstance().getOrdersSizeByUser(user.getId());
        }
        int pageCount = size / pageSize;
        List<Order> orders;
        if (user.getRole().equals(User.Role.ADMIN) || user.getRole().equals(User.Role.MANAGER)) {
            orders = OrderManager.getInstance().getAllOrders(pageSize * (page - 1), pageSize);
        } else {
            orders = OrderManager.getInstance().getAllOrdersByUser(user.getId(), pageSize * (page - 1), pageSize);
        }
        req.getSession().setAttribute("allOrders", orders);
        req.getSession().setAttribute("adminContent", "orders");
        log.info("all orders " + orders);
        req.getSession().setAttribute("pageCount", pageCount);
        req.getSession().setAttribute("page", page);
        req.getSession().setAttribute("pageSize", pageSize);
        log.info("admin content " + req.getSession().getAttribute("adminContent"));

        return "admin_page.jsp";

    }
}
