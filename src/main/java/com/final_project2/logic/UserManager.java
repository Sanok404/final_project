package com.final_project2.logic;

import com.final_project2.db.DBException;
import com.final_project2.db.DBManager;
import com.final_project2.entity.User;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class UserManager {

    private static final Logger log = Logger.getLogger(UserManager.class);

    private static UserManager instance;
    private DBManager dbManager;

    private UserManager() {
        dbManager = DBManager.getInstance();
    }

    public static synchronized UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    /**
     *
     * @param email to search (string value)
     * @return user from db by email if exist
     * @throws DBException if something wrong with connection to database
     */
    public User findUserByLogin(String email) throws DBException {
        try (Connection connection = dbManager.getConnection()) {
            log.info("found user by login " + dbManager.findUserByLogin(connection, email));
            return dbManager.findUserByLogin(connection, email);
        } catch (SQLException e) {
            log.error("connection is fail " + e);
            throw new DBException("Cannot find user", e);
        }
    }

    /**
     *
     * @param id to search (int value)
     * @return user from db by id if exist
     * @throws DBException if something wrong with connection to database
     */
    public User findUserById(int id) throws DBException {
        try (Connection connection = dbManager.getConnection()) {
            return dbManager.findUserById(connection, id);
        } catch (SQLException e) {
            log.error("connection is fail " + e);
            throw new DBException("Cannot find user by id", e);
        }
    }

    /**
     *
     * @param user is a new user to add to database
     * @return boolean status
     * @throws DBException if something wrong with connection to database
     */
    public boolean registerNewUser(User user) throws DBException {

        try (Connection connection = dbManager.getConnection()){
            dbManager.createUser(connection, user);
            return true;
        }catch (SQLException e){
            log.error("connection is fail " + e);
            throw new DBException("Cannot register new user", e);
        }
    }

    /**
     *
     * @param id user to block/unblock (int value)
     * @param setStatus true if block or false if unblock (boolean value)
     * @return success status (boolean value)
     * @throws DBException if something wrong with connection to database
     */
    public boolean changeBlockStatus(int id, Boolean setStatus) throws DBException {
        try(Connection connection = dbManager.getConnection()){
            return dbManager.changeBlockStatus(connection,id,setStatus);
        } catch (SQLException e) {
            throw new DBException("cannot change block status ",e);
        }

    }

    /**
     *
     * @param offset - part of all users from database
     * @param limit - amount items in List for show in page
     * @return List <User> with all users from DB
     * @throws DBException if something wrong with connection to database
     */
    public List <User> findAllUsers(int offset, int limit) throws DBException {
        List<User> users;
        try(Connection connection = dbManager.getConnection()) {
            users = dbManager.getAllUsers(connection,offset,limit);
            return users;
        } catch (SQLException e) {
            throw new DBException("Cannot find all users ", e);
        }
    }

    /**
     *
     * @return total number of users in the database
     * @throws DBException if something wrong with connection to database
     */
    public int getUsersSize() throws DBException {
        try (Connection connection = dbManager.getConnection()){
            return dbManager.getUsersSize(connection);
        } catch (SQLException e) {
            throw new DBException("cannot get users size ",e);
        }
    }

    /**
     *
     * @param email is a new email to update (string value)
     * @param firstName is a new first name to update (string value)
     * @param lastName is a new last name to update (string value)
     * @param role is a new role to update (string value)
     * @param id is an id of user which needs to update (int value)
     * @return status of updating (boolean value)
     * @throws DBException if something wrong with connection to database
     */
    public boolean updateUser( String email, String firstName, String lastName, String role, int id) throws DBException {
        try (Connection connection = dbManager.getConnection()){
            return dbManager.updateUser(connection, id, email,firstName,lastName,role);
        } catch (SQLException e) {
            log.error("cannot update user "+ e);
            throw new DBException("Cannot update user ", e);
        }
    }

}
