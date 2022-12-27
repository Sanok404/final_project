package com.final_project2.logic;

import com.final_project2.db.DBException;
import com.final_project2.db.DBManager;
import com.final_project2.entity.Car;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class CarManager {
    private static final Logger log = Logger.getLogger(CarManager.class);

    private static CarManager instance;
    private DBManager dbManager;

    private CarManager() {
        dbManager = DBManager.getInstance();
    }

    public static synchronized CarManager getInstance() {
        if (instance == null) {
            instance = new CarManager();
        }
        return instance;
    }

    /**
     *
     * @param car created only by admin
     * @return car if successfully added
     * @throws DBException if something wrong with database
     */
    public Car addNewCar(Car car) throws DBException {
        try (Connection connection = dbManager.getConnection()){
            return dbManager.addNewCar(connection, car);
        }catch (SQLException e){
            log.error("connection is fail " + e);
            throw new DBException("Cannot add new car", e);
        }
    }

    /**
     *
     * @param offset - part of all cars from database
     * @param limit - amount items in List for show in page
     * @return List<Car> - part of all machines in the database according to the parameters
     * @throws DBException if something wrong with database
     */
    public List<Car> getAllCars(int offset, int limit) throws DBException {
        List<Car> cars;
        try (Connection connection = dbManager.getConnection()) {
            cars = dbManager.getAllCars(connection, offset,limit);
            log.debug("all cars from database "+ cars);
            return cars;
        } catch (SQLException e) {
            log.error("cannot get all cars ", e);
            throw new DBException("Cannot get all cars ", e);
        }
    }

    /**
     *
     * @param offset - part of all cars from database
     * @param limit - amount items in List for show in page
     * @param brand - String with car brand to search
     * @return List<Car> - part of all machines in the database according to the parameters
     * @throws DBException if something wrong with database
     */
    public List<Car> getAllCarsByBrand(int offset, int limit,String brand) throws DBException {
        List<Car> cars;
        try (Connection connection = dbManager.getConnection()) {
            cars = dbManager.getAllCarsByBrand(connection, offset,limit, brand);
            log.trace("all cars from database by Brand "+ cars);
            return cars;
        } catch (SQLException e) {
            log.error("cannot get all cars ", e);
            throw new DBException("Cannot get all cars ", e);
        }
    }

    /**
     *
     * @return the total number of all cars in the database
     * @throws DBException if something wrong with connection to database
     */
    public int getCarsSize() throws DBException {
        try (Connection connection = dbManager.getConnection()){
            return dbManager.getCarsSize(connection);
        } catch (SQLException e) {
            throw new DBException("cannot get cars size ",e);
        }
    }

    /**
     *
     * @param brand of car
     * @return the total number of all cars with a certain brand in the database
     * @throws DBException if something wrong with connection to database
     */
    public int getCarsSizeByBrand(String brand) throws DBException {
        try (Connection connection = dbManager.getConnection()){
            return dbManager.getCarsSizeByBrand(connection, brand);
        } catch (SQLException e) {
            log.error("cannot get cars size by brand "+ e);
            throw new DBException("cannot get cars size by brand ",e);
        }
    }

    /**
     *
     * @param id of cars
     * @return car with a certain id
     * @throws DBException if something wrong with connection to database
     */
    public Car findCarById(int id) throws DBException {
        try (Connection connection = dbManager.getConnection()) {
            return dbManager.findCarById(connection, id);
        } catch (SQLException e) {
            log.error("connection is fail " + e);
            throw new DBException("Cannot find car by id", e);
        }
    }

    /**
     *
     * @return collection with all brands in database
     * @throws DBException if something wrong with connection to database
     */
    public Set<String> getAllBrands() throws DBException {
        try(Connection connection = dbManager.getConnection()) {
            return dbManager.getAllBrands(connection);
        } catch (SQLException e) {
            throw new DBException("cannot get all brands", e);
        }
    }

    /**
     *
     * @return collection with all cars classes in database
     * @throws DBException if something wrong with connection to database
     */
    public Set<String> getAllCarClass() throws DBException {
        try(Connection connection = dbManager.getConnection()){
            return dbManager.getAllCarClass(connection);
        } catch (SQLException e) {
            throw new DBException("cannot get all car class ", e);
        }
    }

    /**
     *
     * @param carClass of cars
     * @return the total number of all cars with a certain class in the database
     * @throws DBException if something wrong with connection to database
     */
    public int getCarsSizeByClass(String carClass) throws DBException {
        try (Connection connection = dbManager.getConnection()){
            return dbManager.getCarsSizeByClass(connection, carClass);
        } catch (SQLException e) {
            log.error("cannot get cars size by brand "+ e);
            throw new DBException("cannot get cars size by class ",e);
        }
    }

    /**
     *
     * @param offset is a page
     * @param limit is a number of items per page
     * @param carClass is a car class to search in a database
     * @return collection with a part elements by a certain class from the total number that are in the database
     * @throws DBException if something wrong with connection to database
     */
    public List<Car> getAllCarsByClass(int offset, int limit, String carClass) throws DBException {
        List<Car> cars;
        try (Connection connection = dbManager.getConnection()) {
            cars = dbManager.getAllCarsByClass(connection, offset,limit, carClass);
            log.trace("all cars from database by Class "+ cars);
            return cars;
        } catch (SQLException e) {
            log.error("cannot get all cars ", e);
            throw new DBException("Cannot get all cars by class ", e);
        }
    }

    /**
     *
     * @param offset is a page
     * @param limit is a number of items per page
     * @return collection with a part elements sorted by price ascending from the total number that are in the database
     * @throws DBException if something wrong with connection to database
     */
    public List<Car> getAllCarsOrderByPriceAsc(int offset, int limit) throws DBException {
        List<Car> cars;
        try (Connection connection = dbManager.getConnection()) {
            cars = dbManager.getAllCarsOrderByPriceAsc(connection, offset,limit);
            log.debug("all cars from database order by price asc "+ cars);
            return cars;
        } catch (SQLException e) {
            log.error("cannot get all cars order by asc", e);
            throw new DBException("Cannot get all cars ", e);
        }

    }

    /**
     *
     * @param offset is a page
     * @param limit is a number of items per page
     * @return collection with a part elements sorted by price descending from the total number that are in the database
     * @throws DBException if something wrong with connection to database
     */
    public List<Car> getAllCarsOrderByPriceDesc(int offset, int limit) throws DBException {
        List<Car> cars;
        try (Connection connection = dbManager.getConnection()) {
            cars = dbManager.getAllCarsOrderByPriceDesc(connection, offset,limit);
            log.debug("all cars from database order by price desc "+ cars);
            return cars;
        } catch (SQLException e) {
            log.error("cannot get all cars order by price desc ", e);
            throw new DBException("Cannot get all cars order by desc ", e);
        }
    }

    /**
     *
     * @param offset is a page
     * @param limit is a number of items per page
     * @return collection with a part elements by a certain brand sorted by price ascending from the total number that are in the database
     * @throws DBException if something wrong with connection to database
     */
    public List<Car> getAllCarsOrderByBrandAsc(int offset, int limit) throws DBException {
        List<Car> cars;
        try (Connection connection = dbManager.getConnection()) {
            cars = dbManager.getAllCarsOrderByBrandAsc(connection, offset,limit);
            log.trace("all cars from database order by brand asc "+ cars);
            return cars;
        } catch (SQLException e) {
            log.error("cannot get all cars order by brand asc ", e);
            throw new DBException("Cannot get all cars order by brand asc ", e);
        }
    }

    /**
     *
     * @param offset is a page
     * @param limit is a number of items per page
     * @return collection with a part elements by a certain brand sorted by price descending from the total number that are in the database
     * @throws DBException if something wrong with connection to database
     */
    public List<Car> getAllCarsOrderByBrandDesc(int offset, int limit) throws DBException {
        List<Car> cars;
        try (Connection connection = dbManager.getConnection()) {
            cars = dbManager.getAllCarsOrderByBrandDesc(connection, offset,limit);
            log.trace("all cars from database order by brand desc "+ cars);
            return cars;
        } catch (SQLException e) {
            log.error("cannot get all cars order by brand desc ", e);
            throw new DBException("Cannot get all cars order by brand desc ", e);
        }
    }

    /**
     *
     * @param id of car to delete
     * @throws DBException if something wrong with connection to database
     */
    public void deleteCar(int id) throws DBException {
        try(Connection connection = dbManager.getConnection()){
         dbManager.deleteCar(connection,id);
        } catch (SQLException e) {
        log.error("cannot delete car ", e);
        throw new DBException("cannot delete car ", e);
    }
    }

    /**
     *
     * @param id of car to update
     * @param newCarImageUrl is a new image url
     * @param carBrand is a new car brand
     * @param carModel is a new car model
     * @param transmission is a new car transmission
     * @param carClass is a new car class
     * @param price is a new car price
     * @param info is a new info about car
     * @return status of update true or false
     * @throws DBException if something wrong with connection to database
     */
    public boolean updateCar(int id, String newCarImageUrl, String carBrand, String carModel, String transmission, String carClass, double price, String info) throws DBException {
        try(Connection connection = dbManager.getConnection()){
            return dbManager.updateCar(connection,id,newCarImageUrl,carBrand,carModel,transmission, carClass, price, info);
        } catch (SQLException e) {
            log.error("cannot delete car ", e);
            throw new DBException("cannot delete car ", e);
        }
    }
}
