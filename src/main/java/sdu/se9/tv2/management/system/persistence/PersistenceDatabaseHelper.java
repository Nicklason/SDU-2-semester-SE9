package sdu.se9.tv2.management.system.persistence;

import sdu.se9.tv2.management.system.helpers.Config;

import java.sql.*;

public class PersistenceDatabaseHelper {
    private static Connection connection;

    public static Connection getConnection () {
        if (connection == null) {
            try {
                DriverManager.registerDriver(new org.postgresql.Driver());

                connection = DriverManager.getConnection((String)Config.get("database_connection_url"),
                        (String)Config.get("database_user"),
                        (String)Config.get("database_password"));
            } catch (SQLException err) {
                err.printStackTrace();
            }
        }

        return connection;
    }

    public static void main(String[] args) {
        // Load config
        Config.load();

        // Get connection
        Connection connection = getConnection();

        // Make query
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM SomeTable");
            ResultSet result = stmt.executeQuery();

            System.out.println(result);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
