package com.final_project2.command;

import com.final_project2.db.DBException;
import com.final_project2.db.DBManager;
import com.final_project2.entity.Car;
import com.final_project2.logic.CarManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.sql.SQLException;

public class AddNewCarCommand implements Command {
    private static final Logger log = Logger.getLogger(AddNewCarCommand.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        String newCarImageUrl = String.valueOf(req.getSession().getAttribute("newCarImageUrl"));
        String carBrand = req.getParameter("brand");
        String carModel = req.getParameter("carModel");
        String transmission = req.getParameter("transmission");
        String carClass = req.getParameter("carClass");
        double price = Double.parseDouble(req.getParameter("price"));
        String info = req.getParameter("info");
        Car car = new Car();
        car.setImageUrl(newCarImageUrl);
        car.setBrand(carBrand.trim());
        car.setModel(carModel.trim());
        car.setCarTransmission(Car.CarTransmission.valueOf(transmission));
        car.setCarClass(Car.CarClass.valueOf(carClass));
        car.setPrice(price);
        car.setInfoAboutCar(info);
        req.getSession().removeAttribute("newCarImageUrl");
        log.debug("car " + car);
        CarManager.getInstance().addNewCar(car);
        log.debug("add new car command");
        return "controller?command=showAllCars&page=1&pageSize=6";
    }
}
