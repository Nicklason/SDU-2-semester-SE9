package sdu.se9.tv2.management.system.persistence;

import sdu.se9.tv2.management.system.domain.Credit;
import sdu.se9.tv2.management.system.exceptions.DuplicateRoleNameException;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Interface for persistence for credits
 */
public interface IPersistenceCredit {
    /**
     * Create a new credit
     * @param programID The ID of the program the credit is for
     * @param personID The ID of the person the credit is for
     * @param roleName The name of the role that the person has
     * @return
     */
    Credit createCredit (int programID, int personID, String roleName) throws DuplicateRoleNameException, SQLException;

    /**
     * Get all credits for a program
     * @param programID The ID of the program
     * @return
     */
    ArrayList<Credit> getCredits (int programID) throws SQLException;

    Credit getCredit (int programID, String roleName) throws SQLException;

    Credit getCredit (int programID, int personID, String roleName) throws SQLException;

    ArrayList<Credit> getCredits (int programID, int personID) throws  SQLException;

    ArrayList<Credit> getCreditsByPerson (int personID) throws SQLException;

    ArrayList<Credit> getCreditsByPerson (int personID, int maxCount) throws SQLException;
}
