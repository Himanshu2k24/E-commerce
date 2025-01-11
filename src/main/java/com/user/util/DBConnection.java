package com.user.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/userappdb";
    private static final String USER = "root";
    private static final String PASSWORD = "Kapilsunil#12";

    public static Connection getConnection() throws SQLException {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database connection successful!");
            return conn;
        } catch (ClassNotFoundException e) {
            throw new SQLException("Database driver not found", e);
        } catch (SQLException e) {
            System.out.println("Database Connection Error: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}