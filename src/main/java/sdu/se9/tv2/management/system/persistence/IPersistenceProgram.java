package sdu.se9.tv2.management.system.persistence;

import sdu.se9.tv2.management.system.domain.Program;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Interface for persistence for programs
 */
public interface IPersistenceProgram {
    /**
     * Create a new program
     * @param producerID The ID of the producer who made the program
     * @param programName The name of the program
     * @param internalID TV2's internal ID for the program
     * @return
     */
    Program createProgram (int producerID, String programName, int internalID) throws SQLException;

    /**
     * Get a program by ID
     * @param programID The ID of the program
     * @return
     */
    Program getProgram (int programID) throws SQLException;

    /**
     * Get a program by name
     * @param programName The name of the program
     * @return
     */
    Program getProgram (String programName) throws SQLException;

    /**
     * Set a program awaiting approval
     * @param programID The ID of the program
     * @param awaitingApproval The approval status, `true` for pending approval and `false` for not
     */
    void setAwaitingApproval (int programID, boolean awaitingApproval) throws SQLException;

    /**
     * Get a program approval
     * @param programID The ID of the program
     * @param approved `true` for approved, `false` for not
     */
    void setApproved (int programID, boolean approved) throws SQLException;

    /**
     * Get all programs that are awaiting approval
     * @return
     */
    ArrayList<Program> getProgramsAwaitingApproval () throws SQLException;
}
