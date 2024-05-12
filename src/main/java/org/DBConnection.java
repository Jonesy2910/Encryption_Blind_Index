package org;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static Connection getConnection() {
        Connection connection = null;
        try {
            // Using the class in dependancy of mysql
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Creating connection to mysql by using url, username and password
            System.out.println("Connecting to the database");
            String url = "jdbc:mysql://localhost:3306/encryption_database";
            String username = "root";
            String password = "IndividualPassword1107";
            connection = DriverManager.getConnection(url, username, password);
        } catch(SQLException e) {
            System.err.println("Cannot Connect to Database :" + e.getMessage());
        } catch(ClassNotFoundException e) {
            System.err.println("Could not find class in project: " + e.getMessage());
        }
        System.out.println("Connected");
        return connection;
    }
}