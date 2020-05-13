package sdu.se9.tv2.management.system.persistence;

import sdu.se9.tv2.management.system.domain.Credit;
import sdu.se9.tv2.management.system.exceptions.DuplicateRoleNameException;

import java.sql.*;
import java.util.ArrayList;

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

        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Credit(programID, personID, roleName) VALUES(?,?,?) RETURNING id;");
        stmt.setInt(1,  programID);
        stmt.setInt(2,  personID);
        stmt.setString(3,  roleName);

        ResultSet rs = stmt.executeQuery();
        rs.next();

        int id = rs.getInt(1);

        return new Credit(id, programID, personID, roleName);
    }

    @Override
    public ArrayList<Credit> getCredits(int programID) throws SQLException {
        Connection connection = PersistenceDatabaseHelper.getConnection();

        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Credit WHERE programID = ?;");
        stmt.setInt(1, programID);

        ResultSet result = stmt.executeQuery();

        ArrayList<Credit> returnValue = new ArrayList<>();
        while (result.next()){
            returnValue.add(new Credit(result.getInt("id"), result.getInt("programID"), result.getInt("personID"), result.getString("roleName")));
        }

        return returnValue;
    }

    /**
     * Get credits a person has for a program
     * @param programID Program id of the program
     * @param personID Person id of the person
     * @return
     */
    public ArrayList<Credit> getCredits(int programID, int personID) throws SQLException {
        Connection connection = PersistenceDatabaseHelper.getConnection();

        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Credit WHERE programID = ? AND personID = ?;");
        stmt.setInt(1, programID);
        stmt.setInt(2, personID);

        ResultSet result = stmt.executeQuery();

        ArrayList<Credit> returnValue = new ArrayList<>();
        while (result.next()){
            returnValue.add(new Credit(result.getInt("id"), result.getInt("programID"), result.getInt("personID"), result.getString("roleName")));
        }

        System.out.println(result);
        return returnValue;
    }

    public Credit getCredit (int programID, int personID, String roleName) throws SQLException {
        PreparedStatement stmt = PersistenceDatabaseHelper.getConnection().prepareStatement("SELECT * FROM Credit WHERE programID = ? AND personID = ? AND roleName = ? LIMIT 1;");
        stmt.setInt(1, programID);
        stmt.setInt(2, personID);
        stmt.setString(3, roleName);

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
    }

    public Credit getCredit (int programID, String roleName) throws SQLException {
        PreparedStatement stmt = PersistenceDatabaseHelper.getConnection().prepareStatement("SELECT * FROM Credit WHERE programID = ? AND roleName = ? LIMIT 1;");
        stmt.setInt(1, programID);
        stmt.setString(2, roleName);

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
    }


    public ArrayList<Credit> getCreditsByPerson (int personID) throws SQLException {
        return this.getCreditsByPerson(personID, -1);
    }

    /**
     * Gets credits by person
     * @param personID The id of the person
     * @param maxCount If less than 0 then it will return all matching rows
     */
    public ArrayList<Credit> getCreditsByPerson (int personID, int maxCount) throws SQLException {
        Connection connection = PersistenceDatabaseHelper.getConnection();

        PreparedStatement stmt = null;

        if (maxCount < 0) {
            stmt = connection.prepareStatement("SELECT * FROM Credit WHERE personID = ? LIMIT ALL;");
        } else {
            stmt = connection.prepareStatement("SELECT * FROM Credit WHERE personID = ? LIMIT ?;");
            stmt.setInt(2, maxCount);
        }

        stmt.setInt(1, personID);

        ResultSet result = stmt.executeQuery();

        ArrayList<Credit> returnValue = new ArrayList<>();
        while (result.next()){
            returnValue.add(new Credit(result.getInt("id"), result.getInt("programID"), result.getInt("personID"), result.getString("roleName")));
            if (returnValue.size() >= maxCount) {
                break;
            }
        }

        return returnValue;
    }
}
