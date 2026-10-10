package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseOperations {

    public static void testDatabase() {

        String query = "SHOW TABLES";

        try (
            Connection connection = DatabaseConnection.getConnection()
        ) {

            if (connection == null) {
                System.out.println("Could not connect to database.");
                return;
            }

            try (
                Statement statement = connection.createStatement();

                ResultSet resultSet = statement.executeQuery(query)
            ) {

                System.out.println("Tables in payroll_db:");

                while (resultSet.next()) {

                    System.out.println(
                            resultSet.getString(1)
                    );
                }
            }

        } catch (SQLException e) {

            System.out.println("Database operation failed!");

            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        testDatabase();
    }
}