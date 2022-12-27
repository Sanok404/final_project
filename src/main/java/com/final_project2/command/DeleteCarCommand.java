package com.final_project2.command;

import com.final_project2.db.DBException;
import com.final_project2.logic.CarManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.io.IOException;

public class DeleteCarCommand implements Command {

    private static final Logger log = Logger.getLogger(DeleteCarCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException, ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        log.debug("car id to Delete " + id);
        CarManager.getInstance().deleteCar(id);
        return "controller?command=showAllCars&page=1&pageSize=6";
    }
}
