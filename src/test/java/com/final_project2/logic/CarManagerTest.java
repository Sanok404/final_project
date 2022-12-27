package com.final_project2.logic;

import com.final_project2.db.DBException;
import com.final_project2.entity.Car;
import org.junit.jupiter.api.*;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public class CarManagerTest {

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

    private static final String DROP_TABLE_CARS = "DROP TABLE cars";

    @BeforeAll
    public static void getTestConnection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/car_rental_test", "root", "1234");


    }

    @BeforeEach
    void setUp() throws SQLException {
        connection.createStatement().executeUpdate(CREATE_TABLE_CARS);
    }

    @AfterEach
    void tearDown() throws SQLException {
        connection.createStatement().executeUpdate(DROP_TABLE_CARS);
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
    void addNewCarTest() throws DBException {
        Car car = buildTestCar();
        CarManager.getInstance().addNewCar(car);
        Car actualCar = CarManager.getInstance().findCarById(1);
        Assertions.assertTrue(new ReflectionEquals(car).matches(actualCar));
    }

    @Test
    void getAllCarsTest() throws DBException {
        Car car = buildTestCar();
        Car car1 = buildTestCar();
        Car car2 = buildTestCar();
        Car car3 = buildTestCar();
        car.setBrand("car");
        car1.setBrand("car1");
        car2.setBrand("car2");
        car3.setBrand("car3");
        CarManager.getInstance().addNewCar(car);
        CarManager.getInstance().addNewCar(car1);
        CarManager.getInstance().addNewCar(car2);
        CarManager.getInstance().addNewCar(car3);
        List<Car> expectedCars = new ArrayList<>();
        expectedCars.add(car);
        expectedCars.add(car1);
        expectedCars.add(car2);
        expectedCars.add(car3);
        List<Car> actualCars = CarManager.getInstance().getAllCars(0, 10);
        Assertions.assertEquals(expectedCars, actualCars);
    }

    @Test
    void getAllCarsByBrandTest() throws DBException {
        Car car1 = buildTestCar();
        Car car2 = buildTestCar();
        Car car3 = buildTestCar();
        Car car4 = buildTestCar();
        car1.setBrand("car1");
        car2.setBrand("car1");
        car3.setBrand("car2");
        car4.setBrand("car3");
        CarManager.getInstance().addNewCar(car1);
        CarManager.getInstance().addNewCar(car2);
        CarManager.getInstance().addNewCar(car3);
        CarManager.getInstance().addNewCar(car4);
        List<Car> expectedCars = new ArrayList<>();
        expectedCars.add(car1);
        expectedCars.add(car2);
        List<Car> actualCars = CarManager.getInstance().getAllCarsByBrand(0, 10, "car1");
        Assertions.assertEquals(expectedCars, actualCars);
    }

    @Test
    void getCarsSizeTest() throws DBException {
        Car car1 = buildTestCar();
        Car car2 = buildTestCar();
        Car car3 = buildTestCar();
        Car car4 = buildTestCar();

        CarManager.getInstance().addNewCar(car1);
        CarManager.getInstance().addNewCar(car2);
        CarManager.getInstance().addNewCar(car3);
        CarManager.getInstance().addNewCar(car4);

        int actualSize = CarManager.getInstance().getCarsSize();

        Assertions.assertEquals(4, actualSize);

    }

    @Test
    void getCarsSizeByBrandTest() throws DBException {
        Car car1 = buildTestCar();
        Car car2 = buildTestCar();
        Car car3 = buildTestCar();
        Car car4 = buildTestCar();
        car1.setBrand("car1");
        car2.setBrand("car1");
        car3.setBrand("car3");
        car4.setBrand("car2");
        CarManager.getInstance().addNewCar(car1);
        CarManager.getInstance().addNewCar(car2);
        CarManager.getInstance().addNewCar(car3);
        CarManager.getInstance().addNewCar(car4);
        int actualSize = CarManager.getInstance().getCarsSizeByBrand("car1");
        Assertions.assertEquals(2, actualSize);
    }

    @Test
    void findCarByIdTest() throws DBException {
        Car car1 = buildTestCar();
        Car car2 = buildTestCar();
        Car car3 = buildTestCar();
        Car car4 = buildTestCar();
        car1.setBrand("carForThisTest");
        CarManager.getInstance().addNewCar(car1);
        CarManager.getInstance().addNewCar(car2);
        CarManager.getInstance().addNewCar(car3);
        CarManager.getInstance().addNewCar(car4);
        Car actualCar = CarManager.getInstance().findCarById(1);
        Assertions.assertTrue(new ReflectionEquals(car1).matches(actualCar));

    }

    @Test
    void getAllBrandsTest() throws DBException {
        Car car1 = buildTestCar();
        Car car2 = buildTestCar();
        Car car3 = buildTestCar();
        Car car4 = buildTestCar();
        car1.setBrand("car1");
        car2.setBrand("car2");
        car3.setBrand("car3");
        car4.setBrand("car4");
        CarManager.getInstance().addNewCar(car1);
        CarManager.getInstance().addNewCar(car2);
        CarManager.getInstance().addNewCar(car3);
        CarManager.getInstance().addNewCar(car4);
        Set<String> expected = new TreeSet<>();
        expected.add("car1");
        expected.add("car2");
        expected.add("car3");
        expected.add("car4");
        Set<String> actual = CarManager.getInstance().getAllBrands();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getAllCarClassTest() throws DBException {
        Car car1 = buildTestCar();
        Car car2 = buildTestCar();
        Car car3 = buildTestCar();
        Car car4 = buildTestCar();
        car1.setCarClass(Car.CarClass.BUSINESS);
        car2.setCarClass(Car.CarClass.BUSINESS);
        car3.setCarClass(Car.CarClass.ECONOMY);
        car4.setCarClass(Car.CarClass.ECONOMY);
        CarManager.getInstance().addNewCar(car1);
        CarManager.getInstance().addNewCar(car2);
        CarManager.getInstance().addNewCar(car3);
        CarManager.getInstance().addNewCar(car4);
        Set<String> expected = new TreeSet<>();
        expected.add(String.valueOf(Car.CarClass.BUSINESS));
        expected.add(String.valueOf(Car.CarClass.ECONOMY));
        Set<String> actual = CarManager.getInstance().getAllCarClass();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getCarsSizeByClassTest() throws DBException {
        Car car1 = buildTestCar();
        Car car2 = buildTestCar();
        Car car3 = buildTestCar();
        Car car4 = buildTestCar();
        Car car5 = buildTestCar();
        car1.setCarClass(Car.CarClass.BUSINESS);
        car2.setCarClass(Car.CarClass.BUSINESS);
        car5.setCarClass(Car.CarClass.BUSINESS);
        car3.setCarClass(Car.CarClass.ECONOMY);
        car4.setCarClass(Car.CarClass.ECONOMY);
        CarManager.getInstance().addNewCar(car1);
        CarManager.getInstance().addNewCar(car2);
        CarManager.getInstance().addNewCar(car3);
        CarManager.getInstance().addNewCar(car4);
        CarManager.getInstance().addNewCar(car5);

        int actual = CarManager.getInstance().getCarsSizeByClass(String.valueOf(Car.CarClass.BUSINESS));
        Assertions.assertEquals(3, actual);
    }

    @Test
    void getAllCarsByClassTest() throws DBException {
        Car car1 = buildTestCar();
        Car car2 = buildTestCar();
        Car car3 = buildTestCar();
        Car car4 = buildTestCar();
        Car car5 = buildTestCar();
        car1.setCarClass(Car.CarClass.BUSINESS);
        car1.setBrand("car1");
        car2.setCarClass(Car.CarClass.BUSINESS);
        car2.setBrand("car2");
        car5.setCarClass(Car.CarClass.BUSINESS);
        car5.setBrand("car5");
        car3.setCarClass(Car.CarClass.ECONOMY);
        car4.setCarClass(Car.CarClass.ECONOMY);
        CarManager.getInstance().addNewCar(car1);
        CarManager.getInstance().addNewCar(car2);
        CarManager.getInstance().addNewCar(car3);
        CarManager.getInstance().addNewCar(car4);
        CarManager.getInstance().addNewCar(car5);
        List<Car> expectedCar = new ArrayList<>();
        expectedCar.add(car1);
        expectedCar.add(car2);
        expectedCar.add(car5);
        List<Car> actualCar = CarManager.getInstance().getAllCarsByClass(0, 10, String.valueOf(Car.CarClass.BUSINESS));
        Assertions.assertEquals(expectedCar, actualCar);
    }

    @Test
    void getAllCarsOrderByPriceAscTest() throws DBException {
        Car car1 = buildTestCar();
        Car car2 = buildTestCar();
        Car car3 = buildTestCar();
        Car car4 = buildTestCar();
        Car car5 = buildTestCar();
        car1.setPrice(100);
        car2.setPrice(200);
        car3.setPrice(300);
        car4.setPrice(400);
        car5.setPrice(500);
        CarManager.getInstance().addNewCar(car4);
        CarManager.getInstance().addNewCar(car3);
        CarManager.getInstance().addNewCar(car5);
        CarManager.getInstance().addNewCar(car2);
        CarManager.getInstance().addNewCar(car1);
        List<Car> expected = new ArrayList<>();
        expected.add(car1);
        expected.add(car2);
        expected.add(car3);
        expected.add(car4);
        expected.add(car5);
        List<Car> actual = CarManager.getInstance().getAllCarsOrderByPriceAsc(0, 10);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getAllCarsOrderByPriceDescTest() throws DBException {
        Car car1 = buildTestCar();
        Car car2 = buildTestCar();
        Car car3 = buildTestCar();
        Car car4 = buildTestCar();
        Car car5 = buildTestCar();
        car1.setPrice(100);
        car2.setPrice(200);
        car3.setPrice(300);
        car4.setPrice(400);
        car5.setPrice(500);
        CarManager.getInstance().addNewCar(car4);
        CarManager.getInstance().addNewCar(car3);
        CarManager.getInstance().addNewCar(car5);
        CarManager.getInstance().addNewCar(car2);
        CarManager.getInstance().addNewCar(car1);
        List<Car> expected = new ArrayList<>();
        expected.add(car5);
        expected.add(car4);
        expected.add(car3);
        expected.add(car2);
        expected.add(car1);
        List<Car> actual = CarManager.getInstance().getAllCarsOrderByPriceDesc(0, 10);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getAllCarsOrderByBrandAscTest() throws DBException {
        Car car1 = buildTestCar();
        Car car2 = buildTestCar();
        Car car3 = buildTestCar();
        Car car4 = buildTestCar();
        Car car5 = buildTestCar();

        car1.setBrand("Acar");
        car2.setBrand("Bcar");
        car3.setBrand("Ccar");
        car4.setBrand("Dcar");
        car5.setBrand("Ecar");

        CarManager.getInstance().addNewCar(car4);
        CarManager.getInstance().addNewCar(car3);
        CarManager.getInstance().addNewCar(car5);
        CarManager.getInstance().addNewCar(car2);
        CarManager.getInstance().addNewCar(car1);
        List<Car> expected = new ArrayList<>();
        expected.add(car1);
        expected.add(car2);
        expected.add(car3);
        expected.add(car4);
        expected.add(car5);
        List<Car> actual = CarManager.getInstance().getAllCarsOrderByBrandAsc(0, 10);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getAllCarsOrderByBrandDescTest() throws DBException {
        Car car1 = buildTestCar();
        Car car2 = buildTestCar();
        Car car3 = buildTestCar();
        Car car4 = buildTestCar();
        Car car5 = buildTestCar();
        car1.setBrand("Acar");
        car2.setBrand("Bcar");
        car3.setBrand("Ccar");
        car4.setBrand("Dcar");
        car5.setBrand("Ecar");
        CarManager.getInstance().addNewCar(car4);
        CarManager.getInstance().addNewCar(car3);
        CarManager.getInstance().addNewCar(car5);
        CarManager.getInstance().addNewCar(car2);
        CarManager.getInstance().addNewCar(car1);
        List<Car> expected = new ArrayList<>();
        expected.add(car5);
        expected.add(car4);
        expected.add(car3);
        expected.add(car2);
        expected.add(car1);
        List<Car> actual = CarManager.getInstance().getAllCarsOrderByBrandDesc(0, 10);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void deleteCar() throws DBException {
        Car car1 = buildTestCar();
        Car car2 = buildTestCar();
        Car car3 = buildTestCar();
        Car car4 = buildTestCar();
        Car car5 = buildTestCar();
        CarManager.getInstance().addNewCar(car1);
        CarManager.getInstance().addNewCar(car2);
        CarManager.getInstance().addNewCar(car3);
        CarManager.getInstance().addNewCar(car4);
        CarManager.getInstance().addNewCar(car5);
        List<Car> expected = new ArrayList<>();
        expected.add(car2);
        expected.add(car3);
        expected.add(car4);
        expected.add(car5);
        CarManager.getInstance().deleteCar(1);
        List<Car> actual = CarManager.getInstance().getAllCars(0, 10);
        Assertions.assertEquals(expected, actual);

    }

    @Test
    void updateCarTest() throws DBException {
        Car car = buildTestCar();
        CarManager.getInstance().addNewCar(car);
        car.setBrand("UpdatedCar");
        CarManager.getInstance().updateCar(car.getId(), car.getImageUrl(), car.getBrand(), car.getModel(), String.valueOf(car.getCarTransmission()),String.valueOf(car.getCarClass()),car.getPrice(), car.getInfoAboutCar());

        Car actual = CarManager.getInstance().findCarById(car.getId());

        Assertions.assertEquals(car,actual);
    }

}
