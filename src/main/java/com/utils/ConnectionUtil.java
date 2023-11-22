package main.java.com.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
    // Adjust these variables based on your H2 database configuration
    private static final String JDBC_URL = "jdbc:h2:tcp://localhost/~/test";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    private static Connection connection;
    private ConnectionUtil() {
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
        }
        return connection;
    }
}
