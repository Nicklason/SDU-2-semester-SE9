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
            PreparedStatement stmt = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Producer (" +
                    "id SERIAL PRIMARY KEY," +
                    "name VARCHAR(255) UNIQUE NOT NULL)"
            );
            stmt.execute();
        } catch (SQLException err) {
            err.printStackTrace();
        }
      
        try {
            PreparedStatement stmt = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Account ( id serial Primary key, username varchar(255) not null, password varchar(255) not null, type varchar(255) not null, producerID int REFERENCES Producer(id))");
            stmt.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // Create table Program
        try {
            PreparedStatement stmt = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Program (" +
                    "id SERIAL PRIMARY KEY," +
                    "producerID INTEGER NOT NULL REFERENCES Producer(id)," +
                    "name VARCHAR(255) UNIQUE NOT NULL," +
                    "internalID INTEGER NOT NULL," +
                    "pendingApproval BOOLEAN NOT NULL," +
                    "approved BOOLEAN NOT NULL" +
                    ")");
            stmt.execute();
        } catch (SQLException err) {
            err.printStackTrace();
        }

        try {
            PreparedStatement stmt = connection.prepareStatement("CREATE TABLE IF NOT EXISTS Credit (" +
                    "id serial PRIMARY KEY," +
                    "programID INTEGER NOT NULL REFERENCES Program(id)," +
                    "personID INTEGER NOT NULL REFERENCES Person(id)," +
                    "roleName VARCHAR(50) NOT NULL" +
            ")");
            stmt.execute();
        } catch (SQLException err) {
            err.printStackTrace();
        }

        try {
            // It is very important that you add an if statement to the function or it will result in it recursively triggering itself
            PreparedStatement stmt = connection.prepareStatement("CREATE OR REPLACE FUNCTION update_pending_approval()\n" +
                    "\tRETURNS trigger AS\n" +
                    "$BODY$\n" +
                    "BEGIN\n" +
                    "\tIF NEW.approved = true AND NEW.pendingApproval = true THEN\n" +
                    "\t\tUPDATE Program\n" +
                    "\t\tSET pendingApproval = false\n" +
                    "\t\tWHERE id = NEW.id;\n" +
                    "\tEND IF;\n" +
                    "\tRETURN NEW;\n" +
                    "END;\n" +
                    "$BODY$\n" +
                    "LANGUAGE plpgsql;");
            stmt.execute();
        } catch (SQLException err) {
            err.printStackTrace();
        }

        try {
            PreparedStatement stmt = connection.prepareStatement("DROP TRIGGER IF EXISTS approved_changes on Program;\n" +
                    "CREATE TRIGGER approved_changes\n" +
                    "\tAFTER UPDATE\n" +
                    "\tON Program\n" +
                    "\tFOR EACH ROW\n" +
                    "\tEXECUTE PROCEDURE update_pending_approval();");
            stmt.execute();
        } catch (SQLException err) {
            err.printStackTrace();
        }
    }
}
