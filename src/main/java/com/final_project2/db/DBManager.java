package com.final_project2.db;

import com.final_project2.entity.Car;
import com.final_project2.entity.Order;
import com.final_project2.entity.User;
import org.apache.log4j.Logger;


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class DBManager {
    public static final String FIND_USER_BY_EMAIL = "SELECT * from users where email=?";
    private static final String INSERT_USER = "INSERT INTO users values (default, ?,?,?,?,'CLIENT',false)";
    private static final String FIND_USER_BY_ID = "SELECT * from users where id=?";
    private static final String UPDATE_USER = "UPDATE users SET email=?, first_name=?, last_name=?, role=? WHERE id=?";
    private static final String BLOCK_UNBLOCK_USER = "UPDATE users SET block_status=? WHERE id=?";
    private static final String GET_ALL_USERS_PAGINATION = "SELECT * FROM users limit ? offset ?";
    private static final String GET_USER_COUNT = "select count(*) from users";
    private static final String GET_CAR_COUNT = "select count(*) from cars";
    private static final String GET_CAR_COUNT_BY_BRAND = "select count(*) from cars where brand=?";
    private static final String GET_ALL_CARS_PAGINATION = "SELECT * FROM cars limit ? offset ?";
    private static final String GET_ALL_CARS_BY_BRAND = "SELECT * FROM cars WHERE brand=? limit ? offset ?";
    private static final String INSERT_NEW_CAR = "insert into cars values (default,?,?,?,?,?,?,?,false,true)";
    private static final String FIND_CAR_BY_ID = "SELECT * from cars c where c.id=?";
    private static final String GET_ALL_BRANDS = "select brand from cars";
    private static final String GET_ALL_CAR_CLASS = "select class from cars";
    private static final String GET_CAR_COUNT_BY_CLASS = "select count(*) from cars where class=?";
    private static final String GET_ALL_CARS_BY_CLASS = "SELECT * FROM cars WHERE class=? limit ? offset ?";
    private static final String GET_ALL_CARS_ORDER_BY_PRICE_ACS_PAGINATION = "SELECT * FROM cars ORDER BY price ASC limit ? offset ?";
    private static final String GET_ALL_CARS_ORDER_BY_PRICE_DESC_PAGINATION = "SELECT * FROM cars ORDER BY price DESC limit ? offset ?";
    private static final String GET_ALL_CARS_ORDER_BY_BRAND_ASC_PAGINATION = "SELECT * FROM cars ORDER BY brand ASC limit ? offset ?";
    private static final String GET_ALL_CARS_ORDER_BY_BRAND_DESC_PAGINATION = "SELECT * FROM cars ORDER BY brand DESC limit ? offset ?";
    private static final String DELETE_CAR_BY_ID = "DELETE FROM cars WHERE id=?";
    private static final String UPDATE_CAR = "UPDATE cars SET image=?, brand=?, model=?, transmission=?, class=?, price=?, info=? WHERE id=?";
    private static final String INSERT_NEW_ORDER = "insert into orders values (default,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String GET_ORDERS_COUNT = "select count(*) from orders";
    private static final String GET_ORDERS_COUNT_BY_USER = "select count(*) from orders where user_id=?";
    private static final String GET_ALL_ORDERS_PAGINATION = "SELECT * FROM orders limit ? offset ?";
    private static final String GET_ALL_ORDERS_BY_USER_PAGINATION = "SELECT * FROM orders WHERE user_id=? limit ? offset ?";
    private static final String FIND_ORDER_BY_ID = "SELECT * from orders where id=?";
    private static final String UPDATE_ORDER_STATUS = "UPDATE orders SET status=? WHERE id=?";
    private static final String UPDATE_ORDER_INFO_ABOUT_DAMAGE = "UPDATE orders SET damageCost=?, managersComment=?, isDamaged=true WHERE id=?";
    private static DBManager instance;
//    private final DataSource ds;

    Logger logger = Logger.getLogger(DBManager.class);

    private DataSource getDs (){
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            DataSource ds = (DataSource) envContext.lookup("jdbc/TestDB");
            logger.trace(ds);
            return ds;

        } catch (NamingException exception) {
            logger.error("Cannot obtain a data source " + exception);
            throw new IllegalStateException("Cannot obtain a data source " + exception);
        }
    }

    private DBManager() {
    }

    public Connection getConnection() throws SQLException {
        return getDs().getConnection();
//        return getTestConnection();
    }
    private Connection getTestConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/car_rental_test", "root", "1234");
    }


    public static synchronized DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    // User methods
    public boolean changeBlockStatus(Connection con, int id, boolean setStatus) throws SQLException {
        logger.trace("blocking/unblocking user id" + id);
        try (PreparedStatement pstmt = con.prepareStatement(BLOCK_UNBLOCK_USER)) {
            int k = 1;
            pstmt.setBoolean(k++, setStatus);
            pstmt.setInt(k++, id);

            pstmt.executeUpdate();
            return true;
        }
    }

    public User findUserByLogin(Connection con, String email) throws SQLException {
        ResultSet rs = null;
        try (PreparedStatement preparedStatement = con.prepareStatement(FIND_USER_BY_EMAIL)){
            preparedStatement.setString(1, email);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return extractUser(rs);
            }
        } finally {
            closeRs(rs);
        }
        return null;
    }

    private static void closeRs(ResultSet rs) throws SQLException {
        if (rs != null) {
            rs.close();
        }
    }

    public boolean updateUser(Connection connection, int id, String email, String firstName, String lastName, String role)
            throws SQLException {
        logger.debug(id);
        logger.debug(email);
        logger.debug(firstName);
        logger.debug(lastName);
        logger.debug(role);
        try (PreparedStatement pstmt = connection.prepareStatement(UPDATE_USER)) {
            int k = 1;
            pstmt.setString(k++, email);
            pstmt.setString(k++, firstName);
            pstmt.setString(k++, lastName);
            pstmt.setString(k++, role);
            pstmt.setInt(k++, id);
            pstmt.executeUpdate();
            return true;
        }
    }


    public User findUserById(Connection con, int id) throws SQLException {
        ResultSet rs = null;
        try (PreparedStatement preparedStatement = con.prepareStatement(FIND_USER_BY_ID);){

            preparedStatement.setInt(1, id);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return extractUser(rs);
            }
        } finally {
            closeRs(rs);
        }
        return null;
    }


    public boolean createUser(Connection con, User user) throws SQLException {
        ResultSet rs = null;

        try (PreparedStatement preparedStatement = con.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {

            int k = 1;
            preparedStatement.setString(k++, user.getEmail());
            preparedStatement.setString(k++, user.getFirstName());
            preparedStatement.setString(k++, user.getLastName());
            preparedStatement.setString(k++, user.getPassword());

            if (preparedStatement.executeUpdate() > 0) {
                rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    int userId = rs.getInt(1);
                    user.setId(userId);
                }
                return true;
            }
            return false;

        } finally {
            closeRs(rs);
        }
    }

    public List<User> getAllUsers(Connection connection, int offset, int limit) throws SQLException {//tested
        List<User> users = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(GET_ALL_USERS_PAGINATION)) {
            stmt.setInt(1, limit);
            stmt.setInt(2, offset);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                users.add(extractUser(rs));
            }
        }
        return users;
    }

    public int getUsersSize(Connection connection) throws SQLException {
        int size = 0;
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(GET_USER_COUNT)) {
            if (rs.next()) {
                size = rs.getInt(1);
            }
            return size;
        }
    }


    private static User extractUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setEmail(rs.getString("email"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setPassword(rs.getString("password"));
        user.setRole(User.Role.valueOf(rs.getString("role")));
        user.setBlockStatus(rs.getBoolean("block_status"));

        return user;

    }

    //Car
    public List<Car> getAllCars(Connection connection, int offset, int limit) throws SQLException {
        List<Car> allCars = new ArrayList<>();
        ResultSet rs = null;
        try (PreparedStatement stmt = connection.prepareStatement(GET_ALL_CARS_PAGINATION)) {
            stmt.setInt(1, limit);
            stmt.setInt(2, offset);
            rs = stmt.executeQuery();
            while (rs.next()) {
                allCars.add(extractCar(rs));
            }
        } finally {
            closeRs(rs);
        }
        return allCars;
    }

    public Set<String> getAllBrands(Connection connection) throws SQLException {
        Set<String> brands = new TreeSet<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(GET_ALL_BRANDS)) {
            while (rs.next()) {
                brands.add(rs.getString("brand"));
            }
        }
        return brands;
    }

    public List<Car> getAllCarsByBrand(Connection connection, int offset, int limit, String brand) throws SQLException {
        List<Car> allCarsByBrand = new ArrayList<>();
        ResultSet rs = null;
        try (PreparedStatement stmt = connection.prepareStatement(GET_ALL_CARS_BY_BRAND)) {
            int k = 1;
            stmt.setString(k++, brand);
            stmt.setInt(k++, limit);
            stmt.setInt(k++, offset);
            rs = stmt.executeQuery();
            while (rs.next()) {
                allCarsByBrand.add(extractCar(rs));
            }
        } finally {
            closeRs(rs);
        }
        return allCarsByBrand;
    }

    public int getCarsSize(Connection connection) throws SQLException {
        int size = 0;
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(GET_CAR_COUNT)) {
            if (rs.next()) {
                size = rs.getInt(1);
            }
            return size;
        }
    }

    public int getCarsSizeByBrand(Connection connection, String brand) throws SQLException {
        int size = 0;
        ResultSet rs = null;
        try(PreparedStatement pstms = connection.prepareStatement(GET_CAR_COUNT_BY_BRAND);) {

            pstms.setString(1, brand);
            rs = pstms.executeQuery();
            if (rs.next()) {
                size = rs.getInt(1);
            }
            return size;
        } finally {
            closeRs(rs);
        }
    }

    public Car findCarById(Connection con, int id) throws SQLException {
        ResultSet rs = null;
        try (PreparedStatement preparedStatement = con.prepareStatement(FIND_CAR_BY_ID)) {
            preparedStatement.setInt(1, id);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return extractCar(rs);
            }
        } finally {
            closeRs(rs);
        }
        return null;
    }

    public Car addNewCar(Connection con, Car car) throws SQLException {
        ResultSet rs = null;

        try (PreparedStatement preparedStatement = con.prepareStatement(INSERT_NEW_CAR, Statement.RETURN_GENERATED_KEYS);){
            int k = 1;
            preparedStatement.setString(k++, car.getImageUrl());
            preparedStatement.setString(k++, car.getBrand());
            preparedStatement.setString(k++, car.getModel());
            preparedStatement.setString(k++, String.valueOf(car.getCarTransmission()));
            preparedStatement.setString(k++, String.valueOf(car.getCarClass()));
            preparedStatement.setDouble(k++, car.getPrice());
            preparedStatement.setString(k++, car.getInfoAboutCar());

            if (preparedStatement.executeUpdate() > 0) {
                rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    int carId = rs.getInt(1);
                    car.setId(carId);
                }
            }
            return car;
        } finally {
            closeRs(rs);
        }
    }
    private static Car extractCar(ResultSet rs) throws SQLException {
        Car car = new Car();
        car.setId(rs.getInt("id"));
        car.setImageUrl(rs.getString("image"));
        car.setBrand(rs.getString("brand"));
        car.setModel(rs.getString("model"));
        car.setCarTransmission(Car.CarTransmission.valueOf(rs.getString("transmission")));
        car.setCarClass(Car.CarClass.valueOf(rs.getString("class")));
        car.setPrice(rs.getDouble("price"));
        car.setInfoAboutCar(rs.getString("info"));
        car.setBroken(rs.getBoolean("isBroken"));
        car.setFree(rs.getBoolean("isFree"));
        return car;
    }

    public Set<String> getAllCarClass(Connection connection) throws SQLException {
        Set<String> carClass = new TreeSet<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(GET_ALL_CAR_CLASS)) {
            while (rs.next()) {
                carClass.add(rs.getString("class"));
            }
        }
        return carClass;
    }

    public int getCarsSizeByClass(Connection connection, String carClass) throws SQLException {
        int size = 0;
        ResultSet rs = null;
        try (PreparedStatement pstms = connection.prepareStatement(GET_CAR_COUNT_BY_CLASS);){

            pstms.setString(1, carClass);
            rs = pstms.executeQuery();
            if (rs.next()) {
                size = rs.getInt(1);
            }
            return size;
        } finally {
            closeRs(rs);
        }
    }

    public List<Car> getAllCarsByClass(Connection connection, int offset, int limit, String carClass) throws SQLException {
        List<Car> cars = new ArrayList<>();
        ResultSet rs = null;
        try (PreparedStatement stmt = connection.prepareStatement(GET_ALL_CARS_BY_CLASS)) {
            int k = 1;
            stmt.setString(k++, carClass);
            stmt.setInt(k++, limit);
            stmt.setInt(k++, offset);
            rs = stmt.executeQuery();
            while (rs.next()) {
                cars.add(extractCar(rs));
            }
        } finally {
            closeRs(rs);
        }
        return cars;

    }

    public List<Car> getAllCarsOrderByPriceAsc(Connection connection, int offset, int limit) throws SQLException {
        List<Car> cars = new ArrayList<>();
        ResultSet rs = null;
        try (PreparedStatement stmt = connection.prepareStatement(GET_ALL_CARS_ORDER_BY_PRICE_ACS_PAGINATION)) {
            stmt.setInt(1, limit);
            stmt.setInt(2, offset);
            rs = stmt.executeQuery();
            while (rs.next()) {
                cars.add(extractCar(rs));
            }
        } finally {
            closeRs(rs);
        }
        return cars;
    }

    public List<Car> getAllCarsOrderByPriceDesc(Connection connection, int offset, int limit) throws SQLException {
        List<Car> cars = new ArrayList<>();
        ResultSet rs = null;
        try (PreparedStatement stmt = connection.prepareStatement(GET_ALL_CARS_ORDER_BY_PRICE_DESC_PAGINATION)) {
            stmt.setInt(1, limit);
            stmt.setInt(2, offset);
            rs = stmt.executeQuery();
            while (rs.next()) {
                cars.add(extractCar(rs));
            }
        } finally {
            closeRs(rs);
        }
        return cars;
    }

    public List<Car> getAllCarsOrderByBrandAsc(Connection connection, int offset, int limit) throws SQLException {
        List<Car> cars = new ArrayList<>();
        ResultSet rs = null;
        try (PreparedStatement stmt = connection.prepareStatement(GET_ALL_CARS_ORDER_BY_BRAND_ASC_PAGINATION)) {
            stmt.setInt(1, limit);
            stmt.setInt(2, offset);
            rs = stmt.executeQuery();
            while (rs.next()) {
                cars.add(extractCar(rs));
            }
        } finally {
            closeRs(rs);
        }
        return cars;
    }

    public List<Car> getAllCarsOrderByBrandDesc(Connection connection, int offset, int limit) throws SQLException {
        List<Car> cars = new ArrayList<>();
        ResultSet rs = null;
        try (PreparedStatement stmt = connection.prepareStatement(GET_ALL_CARS_ORDER_BY_BRAND_DESC_PAGINATION)) {
            stmt.setInt(1, limit);
            stmt.setInt(2, offset);
            rs = stmt.executeQuery();
            while (rs.next()) {
                cars.add(extractCar(rs));
            }
        } finally {
            closeRs(rs);
        }
        return cars;

    }

    public void deleteCar(Connection connection, int id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_CAR_BY_ID)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    public boolean updateCar(Connection connection, int id, String newCarImageUrl, String carBrand, String carModel, String transmission, String carClass, double price, String info) throws SQLException {
        try (PreparedStatement pstmt = connection.prepareStatement(UPDATE_CAR)) {
            int k = 1;
            pstmt.setString(k++, newCarImageUrl);
            pstmt.setString(k++, carBrand);
            pstmt.setString(k++, carModel);
            pstmt.setString(k++, transmission);
            pstmt.setString(k++, carClass);
            pstmt.setDouble(k++, price);
            pstmt.setString(k++, info);
            pstmt.setInt(k++, id);
            pstmt.executeUpdate();
            return true;
        }
    }

    public Order addNewOrder(Connection connection, Order order) throws SQLException {
        ResultSet rs = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEW_ORDER, Statement.RETURN_GENERATED_KEYS)){
            int k = 1;
            preparedStatement.setInt(k++, order.getCar().getId());
            preparedStatement.setInt(k++, order.getUser().getId());
            preparedStatement.setString(k++, order.getSeriesAndNumberOfThePassport());
            preparedStatement.setBoolean(k++, order.isWithADriver());
            preparedStatement.setString(k++, order.getDateStart());
            preparedStatement.setString(k++, order.getDateEnd());
            preparedStatement.setDouble(k++, order.getCost());
            preparedStatement.setBoolean(k++, order.isDamaged());
            preparedStatement.setDouble(k++, order.getDamageCost());
            preparedStatement.setString(k++, String.valueOf(order.getStatus()));
            preparedStatement.setString(k++, order.getManagersComment());
            if (preparedStatement.executeUpdate() > 0) {
                rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    int orderId = rs.getInt(1);
                    order.setId(orderId);
                }
                return order;
            }
            return null;
        } finally {
            closeRs(rs);
        }
    }

    public int getAllOrdersSize(Connection connection) throws SQLException {
        int size = 0;
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(GET_ORDERS_COUNT)) {
            if (rs.next()) {
                size = rs.getInt(1);
            }
            return size;
        }
    }

    public int getOrdersSizeByUser(Connection connection, int id) throws SQLException {
        int size = 0;
        ResultSet rs = null;
        try (PreparedStatement pstms = connection.prepareStatement(GET_ORDERS_COUNT_BY_USER);){

            pstms.setInt(1, id);
            rs = pstms.executeQuery();
            if (rs.next()) {
                size = rs.getInt(1);
            }
            return size;
        } finally {
            closeRs(rs);
        }
    }

    public List<Order> getAllOrders(Connection connection, int offset, int limit) throws SQLException {
        List<Order> orders = new ArrayList<>();
        ResultSet rs = null;
        try (PreparedStatement stmt = connection.prepareStatement(GET_ALL_ORDERS_PAGINATION)) {
            stmt.setInt(1, limit);
            stmt.setInt(2, offset);
            rs = stmt.executeQuery();
            while (rs.next()) {
                orders.add(extractOrder(connection,rs));
            }
        } finally {
            closeRs(rs);
        }
        return orders;
    }

    private static Order extractOrder(Connection connection,ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setId(rs.getInt("id"));
        order.setCar(DBManager.getInstance().findCarById(connection,rs.getInt("car_id")));
        order.setUser(DBManager.getInstance().findUserById(connection,rs.getInt("user_id")));
        order.setSeriesAndNumberOfThePassport(rs.getString("passport"));
        order.setWithADriver(rs.getBoolean("driverService"));
        order.setDateStart(rs.getString("dateStart"));
        order.setDateEnd(rs.getString("dateEnd"));
        order.setCost(rs.getDouble("cost"));
        order.setDamaged(rs.getBoolean("isDamaged"));
        order.setDamageCost(rs.getDouble("damageCost"));
        order.setStatus(Order.Status.valueOf(rs.getString("status")));
        order.setManagersComment(rs.getString("managersComment"));
        return order;
    }

    public List<Order> getAllOrdersByUser(Connection connection, int id, int offset, int limit) throws SQLException {
        List<Order> orders = new ArrayList<>();
        ResultSet rs = null;
        try (PreparedStatement stmt = connection.prepareStatement(GET_ALL_ORDERS_BY_USER_PAGINATION)) {
            stmt.setInt(1, id);
            stmt.setInt(2, limit);
            stmt.setInt(3, offset);
            rs = stmt.executeQuery();
            while (rs.next()) {
                orders.add(extractOrder(connection,rs));
            }
        } finally {
            closeRs(rs);
        }
        return orders;

    }
    public Order getOrderById(Connection connection, int orderId) throws SQLException {
        ResultSet rs = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_ORDER_BY_ID);){

            preparedStatement.setInt(1, orderId);
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return extractOrder(connection,rs);
            }
        } finally {
            closeRs(rs);
        }
        return null;
    }
    public void setNewOrderStatus(Connection connection, int orderId, String newStatus) throws SQLException {
        logger.debug(orderId);
        logger.debug(newStatus);

        try (PreparedStatement pstmt = connection.prepareStatement(UPDATE_ORDER_STATUS)) {
            int k = 1;
            pstmt.setString(k++, newStatus);
            pstmt.setInt(k++, orderId);
            pstmt.executeUpdate();
        }
    }

    public void setInfoAboutDamage(Connection connection, int orderId, double damageCost, String managersComment) throws SQLException {
        logger.debug(orderId);
        logger.debug(damageCost);
        logger.debug(managersComment);
        try (PreparedStatement pstmt = connection.prepareStatement(UPDATE_ORDER_INFO_ABOUT_DAMAGE)) {
            int k = 1;
            pstmt.setDouble(k++, damageCost);
            pstmt.setString(k++, managersComment);
            pstmt.setInt(k++, orderId);
            pstmt.executeUpdate();

        }
    }
}
