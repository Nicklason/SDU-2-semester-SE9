package sdu.se9.tv2.management.system.persistence;

import sdu.se9.tv2.management.system.domain.Program;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Implementation of the IPersistenceProgram interface
 */
public class PersistenceProgram implements IPersistenceProgram {

    private static PersistenceProgram instance = null;

    public static PersistenceProgram getInstance() {
        if (instance == null) {
            instance = new PersistenceProgram();
        }

        return instance;
    }

    /**
     * Creates a new instance of the PersistenceProducer class
     */
    private PersistenceProgram() {}

    /**
     * Creates a new program and saves it to file
     * @param producerID The ID of the producer who made the program
     * @param programName The name of the program
     * @param internalID TV2's internal ID for the program
     * @return
     */
    public Program createProgram (int producerID, String programName, int internalID) throws SQLException {
        PreparedStatement stmt = PersistenceDatabaseHelper.getConnection().prepareStatement("INSERT INTO Program (producerID, name, internalID, pendingApproval, approved) VALUES (?, ?, ?, false, false) RETURNING id;");
        stmt.setInt(1, producerID);
        stmt.setString(2, programName);
        stmt.setInt(3, internalID);

        // https://stackoverflow.com/questions/241003/how-to-get-a-value-from-the-last-inserted-row

        ResultSet rs = stmt.executeQuery();
        rs.next();

        int id = rs.getInt(1);

        // Return new instance of program with values inserted
        return new Program(id, producerID, programName, internalID, false, false);
    }

    /**
     * Gets a program by ID
     * @param programID The ID of the program
     * @return
     */
    public Program getProgram (int programID) throws SQLException {
        PreparedStatement stmt = PersistenceDatabaseHelper.getConnection().prepareStatement("SELECT * FROM Program WHERE id = ?;");
        stmt.setInt(1, programID);

        ResultSet rs = stmt.executeQuery();

        if (!rs.next()) {
            // No match
            return null;
        }

        int id = rs.getInt("id");
        int producerID =  rs.getInt("producerID");
        String name = rs.getString("name");
        int internalID = rs.getInt("internalID");
        boolean approved = rs.getBoolean("approved");
        boolean awaitingApproval = rs.getBoolean("awaitingApproval");

        return new Program(id, producerID, name, internalID, approved, awaitingApproval);
    }

    /**
     * Gets a program by name
     * @param programName The name of the program
     * @return
     */
    public Program getProgram (String programName) throws SQLException {
        PreparedStatement stmt = PersistenceDatabaseHelper.getConnection().prepareStatement("SELECT * FROM Program WHERE name = ?;");
        stmt.setString(1, programName);

        ResultSet rs = stmt.executeQuery();

        if (!rs.next()) {
            // No match
            return null;
        }

        int id = rs.getInt("id");
        int producerID =  rs.getInt("producerID");
        String name = rs.getString("name");
        int internalID = rs.getInt("internalID");
        boolean approved = rs.getBoolean("approved");
        boolean awaitingApproval = rs.getBoolean("awaitingApproval");

        return new Program(id, producerID, name, internalID, approved, awaitingApproval);
    }

    /**
     * Set program credits approved status and saves to file
     * @param programID The ID of the program
     * @param approved `true` for approved, `false` for not
     */
    public void setApproved (int programID, boolean approved) throws SQLException {
        // Create statement
        PreparedStatement stmt = PersistenceDatabaseHelper.getConnection().prepareStatement("UPDATE Program SET approved = ? WHERE id = ?;");

        // Set values
        stmt.setBoolean(1, approved);
        stmt.setInt(2, programID);

        // Execute statement
        stmt.execute();
    }

    /**
     * Set a program awaiting approval status and saves to file
     * @param programID The ID of the program
     * @param awaitingApproval The approval status, `true` for pending approval and `false` for not
     */
    public void setAwaitingApproval (int programID, boolean awaitingApproval) throws SQLException {
        PreparedStatement stmt = PersistenceDatabaseHelper.getConnection().prepareStatement("UPDATE Program SET awaitingApproval = ? WHERE id = ?;");

        stmt.setBoolean(1, awaitingApproval);
        stmt.setInt(2, programID);

        stmt.execute();
    }
}
