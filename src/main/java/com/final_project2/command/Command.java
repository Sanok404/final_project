package com.final_project2.command;

import com.final_project2.db.DBException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

public interface Command {
    String execute(HttpServletRequest req, HttpServletResponse resp) throws DBException, ServletException, IOException, SQLException;

}
