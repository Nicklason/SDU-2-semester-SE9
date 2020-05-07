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

        //Creating the tables in database if they do not already exist
        try {
            PreparedStatement stmt = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Person (" +
                    "id serial PRIMARY KEY," +
                    "firstName varchar(20) NOT NULL," +
                    "lastName varchar(20) NOT NULL" +
            ")");
            stmt.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            PreparedStatement stmt = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Credit (" +
                    "id serial PRIMARY KEY," +
                    "programID INTEGER NOT NULL REFERENCES Program(id)," +
                    "personID INTEGER NOT NULL REFERENCES Person(id)," +
                    "roleName VARCHAR(50) NOT NULL" +
            ")");
            stmt.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
