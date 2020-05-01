package sdu.se9.tv2.management.system.persistence;

import java.sql.*;

public class PersistenceDatabaseHelper {
    private static Connection connection;

    public static Connection getConnection () {
        if (connection == null) {
            try {
                DriverManager.registerDriver(new org.postgresql.Driver());

                connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tv2",
                        "postgres",
                        "1234");
            } catch (SQLException err) {
                err.printStackTrace();
            }
        }

        return connection;
    }
}
