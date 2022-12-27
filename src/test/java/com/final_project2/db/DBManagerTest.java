package com.final_project2.db;

import com.final_project2.entity.Car;
import com.final_project2.entity.Order;
import com.final_project2.entity.User;
import org.junit.jupiter.api.*;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


public class DBManagerTest {

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

    private static final String CREATE_USER = "insert into users values (default, 'admin@admin.com', 'admin', 'admin', '1234', 'ADMIN', false)";


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

    @Test
    void findUserByLoginTest() throws SQLException {
        connection.createStatement().executeUpdate(CREATE_USER);
        User expectedUser = buildUserForTest();
        User actualUser = DBManager.getInstance().findUserByLogin(connection, "admin@admin.com");
        Assertions.assertTrue(new ReflectionEquals(expectedUser).matches(actualUser));
    }

    @Test
    void changeBlockStatusTest() throws SQLException {
        connection.createStatement().executeUpdate(CREATE_USER);
        DBManager.getInstance().changeBlockStatus(connection, 1, true);
        User user = DBManager.getInstance().findUserById(connection, 1);
        Assertions.assertTrue(user.isBlockStatus());
    }

    @Test
    void updateUserTest() throws SQLException {
        connection.createStatement().executeUpdate(CREATE_USER);
        User expectedUser = buildTestUser(1, "admin2@admin2.com", "user", "user", "1234", User.Role.CLIENT);
        DBManager.getInstance().updateUser(connection, 1, "admin2@admin2.com", "user", "user", "CLIENT");
        User actualUser = DBManager.getInstance().findUserById(connection, 1);
        Assertions.assertTrue(new ReflectionEquals(expectedUser).matches(actualUser));
    }

    @Test
    void findUserByIdTest() throws SQLException {
        connection.createStatement().executeUpdate(CREATE_USER);
        User expectedUser = buildUserForTest();
        User actualUser = DBManager.getInstance().findUserById(connection, 1);
        Assertions.assertTrue(new ReflectionEquals(expectedUser).matches(actualUser));
    }

    @Test
    void createUserTest() throws SQLException {
        User expectedUser = buildUserForTest();
        boolean isCreated = DBManager.getInstance().createUser(connection, expectedUser);
        Assertions.assertTrue(isCreated);
    }

    private static User buildUserForTest() {
        return buildTestUser(1, "admin@admin.com", "admin", "admin", "1234", User.Role.ADMIN);
    }

    @Test
    void createUserTest2() throws SQLException {
        User expectedUser = buildTestUser(1, "client@client.com", "client1", "client2", "12345", User.Role.CLIENT);
        DBManager.getInstance().createUser(connection, expectedUser);
        User actualUser = DBManager.getInstance().findUserById(connection, 1);
        Assertions.assertTrue(new ReflectionEquals(expectedUser).matches(actualUser));
    }

    @Test
    void getAllUsersTest() throws SQLException {
        List<User> expectedListUsers = new ArrayList<>();
        User user1 = buildTestUser(1, "some@email.com", "someFirstName", "someLastName", "12345", User.Role.CLIENT);
        User user2 = buildTestUser(2, "some2@email.com", "2someFirstName", "2someLastName", "212345", User.Role.CLIENT);
        expectedListUsers.add(user1);
        expectedListUsers.add(user2);
        DBManager.getInstance().createUser(connection, user1);
        DBManager.getInstance().createUser(connection, user2);
        List<User> actualListUsers = DBManager.getInstance().getAllUsers(connection, 0, 10);
        Assertions.assertEquals(expectedListUsers, actualListUsers);
    }

    private static User buildTestUser(int id, String email, String someFirstName, String someLastName, String password, User.Role client) {
        User user1 = new User();
        user1.setId(id);
        user1.setEmail(email);
        user1.setFirstName(someFirstName);
        user1.setLastName(someLastName);
        user1.setPassword(password);
        user1.setRole(client);
        user1.setBlockStatus(false);
        return user1;
    }

    @Test
    void getUsersSizeTest() throws SQLException {
        User user1 = buildUserForTest();
        DBManager.getInstance().createUser(connection, user1);
        int usersSize = DBManager.getInstance().getUsersSize(connection);
        Assertions.assertEquals(1, usersSize);
    }

    @Test
    void addNewCarTest() throws SQLException {
        Car expectedCar = buildTestCar();
        Car actualCar = DBManager.getInstance().addNewCar(connection, expectedCar);
        Assertions.assertTrue(new ReflectionEquals(expectedCar).matches(actualCar));
    }

    private static Car buildTestCar() {
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

    @Test
    void findCarByIdTest() throws SQLException {
        Car expectedCar = buildTestCar();
        DBManager.getInstance().addNewCar(connection, expectedCar);
        Car actualCar = DBManager.getInstance().findCarById(connection, 1);
        Assertions.assertTrue(new ReflectionEquals(expectedCar).matches(actualCar));
    }

    @Test
    void getAllCarClassTest() throws SQLException {
        Set<String> expectedCarClass = new TreeSet<>();
        expectedCarClass.add("BUSINESS");
        expectedCarClass.add("ECONOMY");
        Car car1 = buildTestCar();
        Car car2 = buildTestCar();
        car2.setCarClass(Car.CarClass.ECONOMY);
        DBManager.getInstance().addNewCar(connection, car1);
        DBManager.getInstance().addNewCar(connection, car2);
        Set<String> actualCarClass = DBManager.getInstance().getAllCarClass(connection);
        Assertions.assertEquals(expectedCarClass, actualCarClass);
    }

    @Test
    void getCarsSizeByClassTest() throws SQLException {
        Car car1 = buildTestCar();
        Car car2 = buildTestCar();
        Car car3 = buildTestCar();
        Car car4 = buildTestCar();
        car1.setCarClass(Car.CarClass.BUSINESS);
        car2.setCarClass(Car.CarClass.BUSINESS);
        car3.setCarClass(Car.CarClass.ECONOMY);
        car4.setCarClass(Car.CarClass.COMFORT);
        DBManager.getInstance().addNewCar(connection, car1);
        DBManager.getInstance().addNewCar(connection, car2);
        DBManager.getInstance().addNewCar(connection, car3);
        DBManager.getInstance().addNewCar(connection, car4);
        int actualSize = DBManager.getInstance().getCarsSizeByClass(connection, String.valueOf(Car.CarClass.BUSINESS));
        Assertions.assertEquals(2, actualSize);
    }

    @Test
    void getAllCarsByClass() throws SQLException {
        Car car1 = buildTestCar();
        Car car2 = buildTestCar();
        Car car3 = buildTestCar();
        Car car4 = buildTestCar();
        car1.setCarClass(Car.CarClass.BUSINESS);
        car2.setCarClass(Car.CarClass.BUSINESS);
        car3.setCarClass(Car.CarClass.ECONOMY);
        car4.setCarClass(Car.CarClass.COMFORT);
        DBManager.getInstance().addNewCar(connection, car1);
        DBManager.getInstance().addNewCar(connection, car2);
        DBManager.getInstance().addNewCar(connection, car3);
        DBManager.getInstance().addNewCar(connection, car4);
        List<Car> expectedCars = new ArrayList<>();
        expectedCars.add(car1);
        expectedCars.add(car2);

        List<Car> actualCars = DBManager.getInstance().getAllCarsByClass(connection, 0, 10, String.valueOf(Car.CarClass.BUSINESS));
        Assertions.assertEquals(expectedCars, actualCars);
    }

    @Test
    void getAllCarsOrderByPriceAscTest() throws SQLException {
        Car car1 = buildTestCar();
        Car car2 = buildTestCar();
        Car car3 = buildTestCar();
        Car car4 = buildTestCar();
        car1.setPrice(1000);
        car2.setPrice(2000);
        car3.setPrice(3000);
        car4.setPrice(4000);
        DBManager.getInstance().addNewCar(connection, car3);
        DBManager.getInstance().addNewCar(connection, car1);
        DBManager.getInstance().addNewCar(connection, car4);
        DBManager.getInstance().addNewCar(connection, car2);
        List<Car> expectedCars = new ArrayList<>();
        expectedCars.add(car1);
        expectedCars.add(car2);
        expectedCars.add(car3);
        expectedCars.add(car4);
        List<Car> actualCars = DBManager.getInstance().getAllCarsOrderByPriceAsc(connection, 0, 10);
        Assertions.assertEquals(expectedCars, actualCars);
    }

    @Test
    void getAllCarsOrderByPriceDescTest() throws SQLException {
        Car car1 = buildTestCar();
        Car car2 = buildTestCar();
        Car car3 = buildTestCar();
        Car car4 = buildTestCar();
        car1.setPrice(1000);
        car2.setPrice(2000);
        car3.setPrice(3000);
        car4.setPrice(4000);
        DBManager.getInstance().addNewCar(connection, car3);
        DBManager.getInstance().addNewCar(connection, car1);
        DBManager.getInstance().addNewCar(connection, car4);
        DBManager.getInstance().addNewCar(connection, car2);
        List<Car> expectedCars = new ArrayList<>();
        expectedCars.add(car4);
        expectedCars.add(car3);
        expectedCars.add(car2);
        expectedCars.add(car1);
        List<Car> actualCars = DBManager.getInstance().getAllCarsOrderByPriceDesc(connection, 0, 10);
        Assertions.assertEquals(expectedCars, actualCars);
    }

    @Test
    void getAllCarsOrderByBrandAscTest() throws SQLException {
        Car car1 = buildTestCar();
        Car car2 = buildTestCar();
        Car car3 = buildTestCar();
        Car car4 = buildTestCar();
        car1.setBrand("Aston Martin");
        car2.setBrand("Bentley");
        car3.setBrand("Chevrolet");
        car3.setBrand("Dodge");
        DBManager.getInstance().addNewCar(connection, car3);
        DBManager.getInstance().addNewCar(connection, car1);
        DBManager.getInstance().addNewCar(connection, car4);
        DBManager.getInstance().addNewCar(connection, car2);
        List<Car> expectedCars = new ArrayList<>();
        expectedCars.add(car1);
        expectedCars.add(car2);
        expectedCars.add(car3);
        expectedCars.add(car4);
        List<Car> actualCars = DBManager.getInstance().getAllCarsOrderByBrandAsc(connection, 0, 10);
        Assertions.assertEquals(expectedCars, actualCars);
    }

    @Test
    void getAllCarsOrderByBrandDescTest() throws SQLException {
        Car car1 = buildTestCar();
        Car car2 = buildTestCar();
        Car car3 = buildTestCar();
        Car car4 = buildTestCar();
        car1.setBrand("Aston Martin");
        car2.setBrand("Bentley");
        car3.setBrand("Chevrolet");
        car4.setBrand("Dodge");
        DBManager.getInstance().addNewCar(connection, car3);
        DBManager.getInstance().addNewCar(connection, car1);
        DBManager.getInstance().addNewCar(connection, car4);
        DBManager.getInstance().addNewCar(connection, car2);
        List<Car> expectedCars = new ArrayList<>();
        expectedCars.add(car4);
        expectedCars.add(car3);
        expectedCars.add(car2);
        expectedCars.add(car1);
        List<Car> actualCars = DBManager.getInstance().getAllCarsOrderByBrandDesc(connection, 0, 10);
        Assertions.assertEquals(expectedCars, actualCars);
    }

    @Test
    void deleteCarTest() throws SQLException {
        Car car1 = buildTestCar();
        Car car2 = buildTestCar();
        Car car3 = buildTestCar();
        Car car4 = buildTestCar();
        DBManager.getInstance().addNewCar(connection, car1);
        DBManager.getInstance().addNewCar(connection, car2);
        DBManager.getInstance().addNewCar(connection, car3);
        DBManager.getInstance().addNewCar(connection, car4);
        List<Car> expectedCar = new ArrayList<>();
        expectedCar.add(car2);
        expectedCar.add(car3);
        expectedCar.add(car4);
        DBManager.getInstance().deleteCar(connection, 1);
        List<Car> actualCar = DBManager.getInstance().getAllCars(connection, 0, 10);
        Assertions.assertEquals(expectedCar, actualCar);
    }

    @Test
    void updateCarTest() throws SQLException {
        Car expectedCar = buildTestCar();
        DBManager.getInstance().addNewCar(connection, expectedCar);
        expectedCar.setBrand("Zaporozhec");
        DBManager.getInstance().updateCar(connection, expectedCar.getId(), expectedCar.getImageUrl(), expectedCar.getBrand(), expectedCar.getModel(), String.valueOf(expectedCar.getCarTransmission()), String.valueOf(expectedCar.getCarClass()), expectedCar.getPrice(), expectedCar.getInfoAboutCar());
        Car actualCar = DBManager.getInstance().findCarById(connection, 1);

        Assertions.assertEquals(expectedCar, actualCar);
    }

    @Test
    void addNewOrder() throws SQLException {
        Order expectedOrder = buildTestOrder();
        Order actualOrder = DBManager.getInstance().addNewOrder(connection, expectedOrder);

        Assertions.assertNotNull(actualOrder);
    }

    private static Order buildTestOrder() {
        Order order = new Order();
        order.setId(1);
        order.setCar(buildTestCar());
        order.setUser(buildTestUser(1, "so@em.ua", "Fn", "Ln", "12", User.Role.CLIENT));
        order.setSeriesAndNumberOfThePassport("passport");
        order.setWithADriver(true);
        order.setDateStart("08-09-2022");
        order.setDateEnd("10-09-2022");
        order.setCost(1500);
        order.setDamaged(false);
        order.setDamageCost(0);
        order.setStatus(Order.Status.AWAITING_PAYMENT);
        order.setManagersComment(null);
        return order;
    }

    @Test
    void getAllOrdersSize() throws SQLException {
        Order order1 = buildTestOrder();
        Order order2 = buildTestOrder();
        DBManager.getInstance().addNewOrder(connection, order1);
        DBManager.getInstance().addNewOrder(connection, order2);
        int actualSize = DBManager.getInstance().getAllOrdersSize(connection);
        Assertions.assertEquals(2, actualSize);
    }

    @Test
    void getOrdersSizeByUserTest() throws SQLException {
        Order order1 = buildTestOrder();
        Order order2 = buildTestOrder();
        Order order3 = buildTestOrder();
        Order order4 = buildTestOrder();
        order1.getUser().setId(1);
        order2.getUser().setId(1);
        order3.getUser().setId(2);
        order4.getUser().setId(3);

        DBManager.getInstance().addNewOrder(connection, order1);
        DBManager.getInstance().addNewOrder(connection, order2);
        DBManager.getInstance().addNewOrder(connection, order3);
        DBManager.getInstance().addNewOrder(connection, order4);

        int actualSize = DBManager.getInstance().getOrdersSizeByUser(connection, 1);
        Assertions.assertEquals(2, actualSize);
    }

    @Test
    void getAllOrdersTest() throws SQLException {
        Order order1 = buildTestOrder();
        Order order2 = buildTestOrder();
        Order order3 = buildTestOrder();
        Order order4 = buildTestOrder();
        DBManager.getInstance().addNewOrder(connection, order1);
        DBManager.getInstance().addNewOrder(connection, order2);
        DBManager.getInstance().addNewOrder(connection, order3);
        DBManager.getInstance().addNewOrder(connection, order4);
        List<Order> expectedOrder = new ArrayList<>();
        expectedOrder.add(order1);
        expectedOrder.add(order2);
        expectedOrder.add(order3);
        expectedOrder.add(order4);
        DBManager.getInstance().addNewCar(connection, buildTestCar());
        DBManager.getInstance().createUser(connection, buildTestUser(1, "so@em.ua", "Fn", "Ln", "12", User.Role.CLIENT));
        List<Order> actualOrder = DBManager.getInstance().getAllOrders(connection, 0, 10);
        Assertions.assertEquals(expectedOrder, actualOrder);
    }

    @Test
    void getAllOrdersByUserTest() throws SQLException {
        Order order1 = buildTestOrder();
        Order order2 = buildTestOrder();
        Order order3 = buildTestOrder();
        Order order4 = buildTestOrder();
        order1.getUser().setId(1);
        order2.getUser().setId(1);
        order3.getUser().setId(3);
        order4.getUser().setId(4);
        User user1 = buildTestUser(1, "so@em.ua", "Fn", "Ln", "12", User.Role.CLIENT);
        User user2 = buildTestUser(3, "email3", "fm", "ln", "12", User.Role.CLIENT);
        User user3 = buildTestUser(4, "email4", "fm", "ln", "12", User.Role.CLIENT);
        DBManager.getInstance().createUser(connection, user1);
        DBManager.getInstance().createUser(connection, user2);
        DBManager.getInstance().createUser(connection, user3);
        Car car1 = buildTestCar();
        DBManager.getInstance().addNewCar(connection, car1);
        DBManager.getInstance().addNewOrder(connection, order1);
        DBManager.getInstance().addNewOrder(connection, order2);
        DBManager.getInstance().addNewOrder(connection, order3);
        DBManager.getInstance().addNewOrder(connection, order4);
        List<Order> expectedOrders = new ArrayList<>();
        expectedOrders.add(order1);
        expectedOrders.add(order2);
        List<Order> actualOrder = DBManager.getInstance().getAllOrdersByUser(connection, 1, 0, 10);
        Assertions.assertEquals(expectedOrders, actualOrder);
    }

    @Test
    void getOrderByIdTest() throws SQLException {
        Order order1 = buildTestOrder();
        Order order2 = buildTestOrder();
        order1.setId(1);
        order2.setId(2);
        DBManager.getInstance().addNewCar(connection, buildTestCar());
        DBManager.getInstance().createUser(connection, buildTestUser(1, "so@em.ua", "Fn", "Ln", "12", User.Role.CLIENT));
        DBManager.getInstance().addNewOrder(connection, order1);
        DBManager.getInstance().addNewOrder(connection, order2);
        Order actualOrder = DBManager.getInstance().getOrderById(connection, 1);
        Assertions.assertEquals(order1, actualOrder);
    }

    @Test
    void setNewOrderStatusTest() throws SQLException {
        Order order1 = buildTestOrder();
        DBManager.getInstance().addNewCar(connection, buildTestCar());
        DBManager.getInstance().createUser(connection, buildTestUser(1, "so@em.ua", "Fn", "Ln", "12", User.Role.CLIENT));
        DBManager.getInstance().addNewOrder(connection, order1);
        DBManager.getInstance().setNewOrderStatus(connection, 1, String.valueOf(Order.Status.PAID));
        Order order = DBManager.getInstance().getOrderById(connection, 1);
        Assertions.assertEquals(Order.Status.PAID, order.getStatus());
    }

    @Test
    void setInfoAboutDamageTest() throws SQLException {
        Order order1 = buildTestOrder();
        DBManager.getInstance().addNewCar(connection, buildTestCar());
        DBManager.getInstance().createUser(connection, buildTestUser(1, "so@em.ua", "Fn", "Ln", "12", User.Role.CLIENT));
        DBManager.getInstance().addNewOrder(connection, order1);
        Order order2 = buildTestOrder();
        order2.setDamaged(true);
        order2.setDamageCost(1000);
        order2.setManagersComment("Something wrong");
        DBManager.getInstance().setInfoAboutDamage(connection, 1, 1000, "Something wrong");
        Order actualOrder = DBManager.getInstance().getOrderById(connection, 1);
        Assertions.assertEquals(order2, actualOrder);

    }

    @Test
    void getAllBrandsTest() throws SQLException {
        Car car1 = buildTestCar();
        Car car2 = buildTestCar();
        Car car3 = buildTestCar();
        Car car4 = buildTestCar();
        String brand1 = "Aston Martin";
        String brand2 = "Bentley";
        String brand3 = "Chevrolet";
        String brand4 = "Dodge";
        car1.setBrand(brand1);
        car2.setBrand(brand2);
        car3.setBrand(brand3);
        car4.setBrand(brand4);
        Set<String> expectedBrands = new TreeSet<>();
        expectedBrands.add(brand1);
        expectedBrands.add(brand2);
        expectedBrands.add(brand3);
        expectedBrands.add(brand4);
        DBManager.getInstance().addNewCar(connection, car1);
        DBManager.getInstance().addNewCar(connection, car2);
        DBManager.getInstance().addNewCar(connection, car3);
        DBManager.getInstance().addNewCar(connection, car4);
        Set<String> actualBrands = DBManager.getInstance().getAllBrands(connection);
        Assertions.assertEquals(expectedBrands, actualBrands);
    }

    @Test
    void getAllCarsByBrandTest() throws SQLException {
        Car car1 = buildTestCar();
        Car car2 = buildTestCar();
        Car car3 = buildTestCar();
        Car car4 = buildTestCar();
        car1.setBrand("Ford");
        car2.setBrand("Mazda");
        car3.setBrand("Opel");
        car4.setBrand("Ford");
        DBManager.getInstance().addNewCar(connection, car1);
        DBManager.getInstance().addNewCar(connection, car2);
        DBManager.getInstance().addNewCar(connection, car3);
        DBManager.getInstance().addNewCar(connection, car4);
        List<Car> expectedCar = new ArrayList<>();
        expectedCar.add(car1);
        expectedCar.add(car4);
        List<Car> actualCar = DBManager.getInstance().getAllCarsByBrand(connection, 0, 10, "Ford");
        Assertions.assertEquals(expectedCar, actualCar);
    }

    @Test
    void getCarsSizeTest() throws SQLException {
        Car car1 = buildTestCar();
        Car car2 = buildTestCar();
        Car car3 = buildTestCar();
        Car car4 = buildTestCar();
        DBManager.getInstance().addNewCar(connection, car1);
        DBManager.getInstance().addNewCar(connection, car2);
        DBManager.getInstance().addNewCar(connection, car3);
        DBManager.getInstance().addNewCar(connection, car4);
        int actualSize = DBManager.getInstance().getCarsSize(connection);
        Assertions.assertEquals(4, actualSize);
    }

    @Test
    void getCarsSizeByBrandTest() throws SQLException {
        Car car1 = buildTestCar();
        Car car2 = buildTestCar();
        Car car3 = buildTestCar();
        Car car4 = buildTestCar();
        car1.setBrand("Ford");
        car2.setBrand("Mazda");
        car3.setBrand("Opel");
        car4.setBrand("Ford");

        DBManager.getInstance().addNewCar(connection,car1);
        DBManager.getInstance().addNewCar(connection,car2);
        DBManager.getInstance().addNewCar(connection,car3);
        DBManager.getInstance().addNewCar(connection,car4);

        int actualSize = DBManager.getInstance().getCarsSizeByBrand(connection,"Ford");

        Assertions.assertEquals(2,actualSize);

    }
}
