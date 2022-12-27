package com.final_project2.listener;

import com.final_project2.db.DBManager;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;


@WebListener
public class ContextListener implements ServletContextListener {
    private static final Logger log = Logger.getLogger(ContextListener.class);
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("start working");
        Connection connection;
        try {
            connection = DBManager.getInstance().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(connection==null){
            log.error("oops, something went wrong, database is not available now");
            System.exit(-1);

        }


    }
}
