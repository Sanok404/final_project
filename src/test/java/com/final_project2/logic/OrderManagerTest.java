package com.final_project2.logic;

import com.final_project2.db.DBException;
import com.final_project2.entity.Car;
import com.final_project2.entity.Order;
import com.final_project2.entity.User;
import org.junit.jupiter.api.*;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderManagerTest {

    private static Connection connection;

    private static final String CREATE_TABLE_CARS = "CREATE TABLE cars (" +
            "    id           INT PRIMARY KEY auto_increment," +
            "    image        VARCHAR(512)," +
            "    brand        VARCHAR(20)," +
            "    model        VARCHAR(20)," +
            "    transmission VARCHAR(20)," +
            "    class        VARCHAR(20)," +
            "    price        DECIMAL(10, 2)," +
            "    info         VARCHAR(1000)," +
            "    isBroken     BOOLEAN," +
            "    isFree       BOOLEAN" +
            ")";

    private static final String CREATE_TABLE_USERS = "CREATE TABLE users (" +
            "    id           INT PRIMARY KEY auto_increment," +
            "    email        VARCHAR(50) UNIQUE," +
            "    first_name   VARCHAR(50)," +
            "    last_name    VARCHAR(50)," +
            "    password     VARCHAR(20)," +
            "    role         VARCHAR(20)," +
            "    block_status BOOLEAN" +
            ")";

    private static final String CREATE_TABLE_ORDERS = "CREATE TABLE orders (" +
            "    id              int primary key auto_increment," +
            "    car_id          INT REFERENCES cars (id) on delete cascade," +
            "    user_id         INT REFERENCES users (id) on delete cascade," +
            "    passport        VARCHAR(500)," +
            "    driverService   BOOLEAN," +
            "    dateStart       VARCHAR(25)," +
            "    dateEnd         VARCHAR(25)," +
            "    cost            DECIMAL(10, 2)," +
            "    isDamaged       BOOLEAN," +
            "    damageCost      DECIMAL(10, 2)," +
            "    status          VARCHAR(50)," +
            "    managersComment VARCHAR(500)" +
            ")";


    private static final String DROP_TABLE_USERS = "DROP TABLE users";
    private static final String DROP_TABLE_CARS = "DROP TABLE cars";
    private static final String DROP_TABLE_ORDERS = "DROP TABLE orders";

    @BeforeAll
    public static void getTestConnection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/car_rental_test", "root", "1234");


    }

    @BeforeEach
    void setUp() throws SQLException {
        connection.createStatement().executeUpdate(CREATE_TABLE_CARS);
        connection.createStatement().executeUpdate(CREATE_TABLE_USERS);
        connection.createStatement().executeUpdate(CREATE_TABLE_ORDERS);
    }

    @AfterEach
    void tearDown() throws SQLException {
        connection.createStatement().executeUpdate(DROP_TABLE_USERS);
        connection.createStatement().executeUpdate(DROP_TABLE_CARS);
        connection.createStatement().executeUpdate(DROP_TABLE_ORDERS);
    }

    private Car buildTestCar() {
        Car car = new Car();
        car.setId(1);
        car.setImageUrl("test/image.jpeg");
        car.setBrand("maserati");
        car.setModel("someTestModel");
        car.setCarTransmission(Car.CarTransmission.MANUAL);
        car.setCarClass(Car.CarClass.BUSINESS);
        car.setPrice(1500.00);
        car.setInfoAboutCar("some info about this exclusive car");
        car.setBroken(false);
        car.setFree(true);
        return car;
    }

    private User buildTestUser() {
        User user1 = new User();
        user1.setId(1);
        user1.setEmail("test@test.com");
        user1.setFirstName("someFirstName");
        user1.setLastName("someLastName");
        user1.setPassword("password");
        user1.setRole(User.Role.CLIENT);
        user1.setBlockStatus(false);
        return user1;
    }

    private Order buildTestOrder() {
        Order order = new Order();
        order.setId(1);
        order.setCar(buildTestCar());
        order.setUser(buildTestUser());
        order.setSeriesAndNumberOfThePassport("DA1234");
        order.setWithADriver(true);
        order.setDateStart("10-01-2022");
        order.setDateEnd("15-01-2022");
        order.setCost(1000);
        order.setDamaged(false);
        order.setDamageCost(0);
        order.setStatus(Order.Status.AWAITING_PAYMENT);
        order.setManagersComment("something about damage");
        return order;
    }

    @Test
    void addNewOrderTest() throws DBException {
        Order order = buildTestOrder();
        OrderManager.getInstance().addNewOrder(order);
        CarManager.getInstance().addNewCar(buildTestCar());
        UserManager.getInstance().registerNewUser(buildTestUser());
        Order actual = OrderManager.getInstance().getOrderById(1);
        Assertions.assertEquals(order, actual);
    }

    @Test
    void getAllOrdersSizeTest() throws DBException {
        Order order1 = buildTestOrder();
        Order order2 = buildTestOrder();
        Order order3 = buildTestOrder();

        OrderManager.getInstance().addNewOrder(order1);
        OrderManager.getInstance().addNewOrder(order2);
        OrderManager.getInstance().addNewOrder(order3);

        int actual = OrderManager.getInstance().getAllOrdersSize();
        Assertions.assertEquals(3, actual);
    }

    @Test
    void getOrdersSizeByUserTest() throws DBException {
        Order order1 = buildTestOrder();
        Order order2 = buildTestOrder();
        Order order3 = buildTestOrder();
        order1.getUser().setId(50);
        order2.getUser().setId(50);
        OrderManager.getInstance().addNewOrder(order1);
        OrderManager.getInstance().addNewOrder(order2);
        OrderManager.getInstance().addNewOrder(order3);
        int actual = OrderManager.getInstance().getOrdersSizeByUser(50);
        Assertions.assertEquals(2, actual);
    }

    @Test
    void getAllOrdersTest() throws DBException {
        Order order1 = buildTestOrder();
        Order order2 = buildTestOrder();
        Order order3 = buildTestOrder();
        OrderManager.getInstance().addNewOrder(order1);
        OrderManager.getInstance().addNewOrder(order2);
        OrderManager.getInstance().addNewOrder(order3);
        UserManager.getInstance().registerNewUser(order1.getUser());
        CarManager.getInstance().addNewCar(order1.getCar());
        List<Order> expected = new ArrayList<>();
        expected.add(order1);
        expected.add(order2);
        expected.add(order3);
        List<Order> actual = OrderManager.getInstance().getAllOrders(0, 10);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getAllOrdersByUserTest() throws DBException {
        Order order1 = buildTestOrder();
        Order order2 = buildTestOrder();
        Order order3 = buildTestOrder();
        Order order4 = buildTestOrder();
        order1.getUser().setId(1);
        order2.getUser().setId(1);
        order3.getUser().setId(2);
        order4.getUser().setId(3);
        order1.getUser().setEmail("em1");
        order2.getUser().setEmail("em1");
        order3.getUser().setEmail("em2");
        order4.getUser().setEmail("em3");
        UserManager.getInstance().registerNewUser(order1.getUser());
        UserManager.getInstance().registerNewUser(order3.getUser());
        UserManager.getInstance().registerNewUser(order4.getUser());
        CarManager.getInstance().addNewCar(order1.getCar());
        OrderManager.getInstance().addNewOrder(order1);
        OrderManager.getInstance().addNewOrder(order2);
        OrderManager.getInstance().addNewOrder(order3);
        OrderManager.getInstance().addNewOrder(order4);
        List<Order> expected = new ArrayList<>();
        expected.add(order1);
        expected.add(order2);
        List<Order> actual = OrderManager.getInstance().getAllOrdersByUser(1, 0, 10);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getOrderByIdTest() throws DBException {
        Order expected = buildTestOrder();
        Order order2 = buildTestOrder();
        CarManager.getInstance().addNewCar(expected.getCar());
        UserManager.getInstance().registerNewUser(expected.getUser());
        OrderManager.getInstance().addNewOrder(expected);
        OrderManager.getInstance().addNewOrder(order2);
        Order actual = OrderManager.getInstance().getOrderById(1);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void setNewOrderStatusTest() throws DBException {
        Order order = buildTestOrder();
        OrderManager.getInstance().addNewOrder(order);
        OrderManager.getInstance().setNewOrderStatus(1, String.valueOf(Order.Status.ORDER_COMPLETE));
        Order order1 = OrderManager.getInstance().getOrderById(1);
        Assertions.assertEquals(Order.Status.ORDER_COMPLETE,order1.getStatus());
    }
    @Test
    void setInfoAboutDamageTest() throws DBException {
        Order order1 = buildTestOrder();
        OrderManager.getInstance().addNewOrder(order1);
        OrderManager.getInstance().setInfoAboutDamage(1,1000,"somethingWrong");
        Order order2 = OrderManager.getInstance().getOrderById(1);
        Assertions.assertTrue(order2.getDamageCost()==1000&&order2.getManagersComment().equals("somethingWrong"));
    }

}
