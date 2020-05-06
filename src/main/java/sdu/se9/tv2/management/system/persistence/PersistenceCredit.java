package sdu.se9.tv2.management.system.persistence;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import sdu.se9.tv2.management.system.domain.Credit;
import sdu.se9.tv2.management.system.exceptions.DuplicateRoleNameException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PersistenceCredit implements IPersistenceCredit {

    private static PersistenceCredit instance = null;

    public static PersistenceCredit getInstance() {
        if (instance == null) {
            instance = new PersistenceCredit();
        }

        return instance;
    }

    private PersistenceCredit() {}

    @Override
    public Credit createCredit(int programID, int personID, String roleName) throws DuplicateRoleNameException, SQLException {

        Connection connection = PersistenceDatabaseHelper.getConnection();

            if (this.getCredit(programID, roleName) != null) {
                throw new DuplicateRoleNameException("Role already exists");
            }

            PreparedStatement stmt = connection.prepareStatement("INSERT INTO Credit(programID, personID, roleName) VALUES(?,?,?) ", Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1,  programID);
            stmt.setInt(2,  personID);
            stmt.setString(3,  roleName);
            stmt.execute();
            ResultSet rs = stmt.getGeneratedKeys();
            int i = 0;
            if (rs.next()) {
                i = rs.getInt(1);
            }

            System.out.println("You did it!");
            System.out.println(i);
            return new Credit(i, programID, personID, roleName);
        // Save changes to file
    }

    @Override
    public ArrayList<Credit> getCredits(int programID) {
        Connection connection = PersistenceDatabaseHelper.getConnection();

        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Credit WHERE programID =" + programID);
            ResultSet result = stmt.executeQuery();
            int rowcount = 0;
            ArrayList<Credit> returnValue = new ArrayList<>();
            while (result.next()){
                returnValue.add(new Credit(result.getInt(1), result.getInt(2), result.getInt(3), result.getString(4)));
            }

            System.out.println(result);
            return returnValue;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    /**
     * Get credits a person has for a program
     * @param programID Program id of the program
     * @param personID Person id of the person
     * @return
     */
    public ArrayList<Credit> getCredits(int programID, int personID) throws SQLException {
        Connection connection = PersistenceDatabaseHelper.getConnection();

        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Credit WHERE programID ='" + programID + "' AND personID ='" + personID + "'");
            ResultSet result = stmt.executeQuery();
            int rowcount = 0;
            ArrayList<Credit> returnValue = new ArrayList<>();
            while (result.next()){
                returnValue.add(new Credit(result.getInt(1), result.getInt(2), result.getInt(3), result.getString(4)));
            }

            System.out.println(result);
            return returnValue;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public Credit getCredit (int programID, int personID, String roleName) throws SQLException {

        try {
            PreparedStatement stmt = PersistenceDatabaseHelper.getConnection().prepareStatement("SELECT * FROM Credit WHERE programID ='" + programID + "' AND personID ='" + personID + "'" +
                    " AND roleName = '" + roleName + "'");
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                // No match
                return null;
            }

            int id = rs.getInt("id");
            int program = rs.getInt("programID");
            int person = rs.getInt("personID");
            String role = rs.getString("roleName");

            return new Credit(id, program, person, role);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public Credit getCredit (int programID, String roleName) throws SQLException {

        try {
            PreparedStatement stmt = PersistenceDatabaseHelper.getConnection().prepareStatement("SELECT * FROM Credit WHERE programID ='" + programID + "' AND roleName ='" + roleName + "'");
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                // No match
                return null;
            }

            int id = rs.getInt("id");
            int program = rs.getInt("programID");
            int person = rs.getInt("personID");
            String role = rs.getString("roleName");

            return new Credit(id, program, person, role);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }


    public ArrayList<Credit> getCreditsByPerson (int personID) {
        return this.getCreditsByPerson(personID, Integer.MAX_VALUE);
    }

    public ArrayList<Credit> getCreditsByPerson (int personID, int maxCount) {
        Connection connection = PersistenceDatabaseHelper.getConnection();

        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Credit WHERE personID =" + personID);
            ResultSet result = stmt.executeQuery();
            int rowcount = 0;
            ArrayList<Credit> returnValue = new ArrayList<>();
            while (result.next()){
                returnValue.add(new Credit(result.getInt(1), result.getInt(2), result.getInt(3), result.getString(4)));
                if (returnValue.size() >= maxCount) {
                    break;
                }
            }

            System.out.println(result);
            return returnValue;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }
}

