package com.final_project2;

import com.final_project2.command.Command;
import com.final_project2.command.CommandContainer;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;


import java.io.IOException;

@WebServlet("/controller")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 100
)
public class Controller extends HttpServlet {
    private static final Logger log = Logger.getLogger(Controller.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String address = getAddress(req, resp);
        req.getRequestDispatcher(address).forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String address = getAddress(req, resp);
        resp.sendRedirect(address);

    }

    private static String getAddress(HttpServletRequest req, HttpServletResponse resp) {
        req.getSession();
        String address = "error_page.jsp";
        String commandName = req.getParameter("command");
        log.info("method commandName: " + commandName);
        Command command = CommandContainer.getCommand(commandName);
        try {
            address = command.execute(req, resp);
        } catch (Exception ex) {
            req.getSession().setAttribute("exception", ex);
        }
        log.trace("address: " + address);
        return address;
    }

}
