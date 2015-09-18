package com.crud.app.util;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Leon K
 */
public class DatabaseConnectionUtil {
    private static final Logger LOGGER = Logger.getLogger(DatabaseConnectionUtil.class);

    /**
     * @return Database connection.
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static Connection connect() throws ClassNotFoundException, SQLException {
        //Google Cloud SQL instance
//        final String URL = "jdbc:mysql://173.194.106.167:3306/test1";
//        final String USER = "user";
//        final String PW = "";

        //Local instance
        final String URL = "jdbc:mysql://localhost:3306/unity_settings";
        final String USER = "root";
        final String PW = "";

        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection(URL, USER, PW);
        LOGGER.info("Connected to database.");
        return connection;
    }
}
