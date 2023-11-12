package main;

import main.java.com.utils.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        // Perform any necessary initialization here
        System.out.printf("Hello and welcome!");
        // For simplicity, let's assume MainView is your main UI class
        // MainView mainView = new MainView();
        // mainView.show(); // Assuming this method displays the main user interface

        // Testing DB
        try(Connection con = ConnectionUtil.getConnection()){
            String sqlQuery = "SELECT * FROM TEST LIMIT 1";
            try (PreparedStatement preparedStatement = con.prepareStatement(sqlQuery)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String value = resultSet.getString("NAME");
                        System.out.println("Selected value from TEST table: " + value);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
