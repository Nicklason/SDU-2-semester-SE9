package sdu.se9.tv2.management.system;

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
    Credit createCredit (int programID, int personID, String roleName);

    /**
     * Get all credits for a program
     * @param programID The ID of the program
     * @return
     */
    ArrayList<Credit> getCredits (int programID);

    Credit getCredit (int programID, int personID, String roleName);

    ArrayList<Credit> getCredits (int programID, int personID);
}
