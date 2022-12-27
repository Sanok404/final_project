package com.final_project2.logic;

import com.final_project2.db.DBException;
import com.final_project2.entity.User;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserManagerTest {
    private static Connection connection;

    private static final String CREATE_TABLE_USERS = "CREATE TABLE users (" +
            "    id           INT PRIMARY KEY auto_increment," +
            "    email        VARCHAR(50) UNIQUE," +
            "    first_name   VARCHAR(50)," +
            "    last_name    VARCHAR(50)," +
            "    password     VARCHAR(20)," +
            "    role         VARCHAR(20)," +
            "    block_status BOOLEAN" +
            ")";

    private static final String DROP_TABLE_USERS = "DROP TABLE users";

    @BeforeAll
    public static void getTestConnection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/car_rental_test", "root", "1234");
    }

    @BeforeEach
    void setUp() throws SQLException {
        connection.createStatement().executeUpdate(CREATE_TABLE_USERS);

    }

    @AfterEach
    void tearDown() throws SQLException {
        connection.createStatement().executeUpdate(DROP_TABLE_USERS);
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

    @Test
    void findUserByLoginTest() throws DBException {
        User expected = buildTestUser();
        expected.setEmail("user@for.test");
        UserManager.getInstance().registerNewUser(expected);
        User actual = UserManager.getInstance().findUserByLogin("user@for.test");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findUserByIdTest() throws DBException {
        User expected = buildTestUser();
        expected.setEmail("dif@user.tast");
        User another = buildTestUser();
        UserManager.getInstance().registerNewUser(expected);
        UserManager.getInstance().registerNewUser(another);
        User actual = UserManager.getInstance().findUserById(1);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void registerNewUserTest() throws DBException {
        User expected = buildTestUser();
        expected.setEmail("dif@user.tast");
        UserManager.getInstance().registerNewUser(expected);

        User actual = UserManager.getInstance().findUserByLogin("dif@user.tast");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void changeBlockStatusTest() throws DBException {
        User expected = buildTestUser();
        expected.setBlockStatus(true);
        UserManager.getInstance().registerNewUser(expected);
        UserManager.getInstance().changeBlockStatus(1, false);
        User actual = UserManager.getInstance().findUserById(1);
        Assertions.assertFalse(actual.isBlockStatus());
    }

    @Test
    void findAllUsersTest() throws DBException {
        User user1 = buildTestUser();
        User user2 = buildTestUser();
        User user3 = buildTestUser();
        User user4 = buildTestUser();
        user1.setEmail("one");
        user2.setEmail("two");
        user3.setEmail("three");
        user4.setEmail("four");
        UserManager.getInstance().registerNewUser(user1);
        UserManager.getInstance().registerNewUser(user2);
        UserManager.getInstance().registerNewUser(user3);
        UserManager.getInstance().registerNewUser(user4);
        List<User> expected = new ArrayList<>();
        expected.add(user1);
        expected.add(user2);
        expected.add(user3);
        expected.add(user4);
        List<User> actual = UserManager.getInstance().findAllUsers(0, 10);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getUsersSizeTest() throws DBException {
        User user1 = buildTestUser();
        User user2 = buildTestUser();
        User user3 = buildTestUser();
        User user4 = buildTestUser();
        user1.setEmail("one");
        user2.setEmail("two");
        user3.setEmail("three");
        user4.setEmail("four");
        UserManager.getInstance().registerNewUser(user1);
        UserManager.getInstance().registerNewUser(user2);
        UserManager.getInstance().registerNewUser(user3);
        UserManager.getInstance().registerNewUser(user4);
        int actual = UserManager.getInstance().getUsersSize();
        Assertions.assertEquals(4, actual);
    }

    @Test
    void updateUserTest() throws DBException {
        User user = buildTestUser();
        UserManager.getInstance().registerNewUser(user);
        user.setEmail("update@ema.il");
        user.setRole(User.Role.ADMIN);
        UserManager.getInstance().updateUser(user.getEmail(), user.getFirstName(), user.getLastName(), String.valueOf(user.getRole()), user.getId());
        User actual = UserManager.getInstance().findUserById(user.getId());
        Assertions.assertEquals(user, actual);
    }
}
