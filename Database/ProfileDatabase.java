package com.example.finalproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ProfileDatabase {
    public static void main(String[] args) {
        // SQLite connection string
        String url = "jdbc:sqlite:E:/sqlite/sqlitetools/usersdb.db";

        // SQL query
        String query = "SELECT * FROM users";

        try {
            // Create a connection to the database
            Connection conn = DriverManager.getConnection(url);

            // Create a statement
            Statement stmt = conn.createStatement();

            // Execute the query
            ResultSet rs = stmt.executeQuery(query);

            // Iterate through the result set
            while (rs.next()) {
                // Retrieve data from each row
                int id = rs.getInt("id");
                String email = rs.getString("email");
                String password = rs.getString("password");

                // Print the data
                System.out.println("ID: " + id + ", email: " + email + ", password: " + password);
            }

            // Close resources
            rs.close();
            stmt.close();
            conn.close();
            System.out.println("Connection closed.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
