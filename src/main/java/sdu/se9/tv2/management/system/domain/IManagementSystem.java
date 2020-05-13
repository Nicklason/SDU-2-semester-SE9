package sdu.se9.tv2.management.system.domain;

import sdu.se9.tv2.management.system.domain.accounts.Account;
import sdu.se9.tv2.management.system.domain.accounts.ProducerAccount;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IManagementSystem {
    /**
     * Update login
     * @param account Account of the user who has signed in (can be null to sign out)
     */
    void setAccount (Account account);

    /**
     * Get account that is currently signed in
     * @return The account that is currently signed in
     */
    Account getAccount();

    /**
     * Check if someone is signed in
     * @return true if logged in, false if not
     */
    boolean isLoggedIn();

    /**
     * Check if user has access
     * @param requiredAccountType Required type of the account
     * @return true if user has access, false if not
     */
    boolean hasAccess (String requiredAccountType);

    /**
     * Create prodducer accounts for a producer
     * @param producerID The id of the producer
     * @param amount The amount of accounts to create
     * @return An arraylist containing the newly made accounts
     * @throws SQLException Database error
     */
    ArrayList<ProducerAccount> createAccountsForProducer (int producerID, int amount) throws SQLException;

    /**
     * Check if there already exists a person with no credits with the given name
     * @param firstName Firstname of the person
     * @param lastName Lastname of the person
     * @return true if a person exists, false if not
     * @throws SQLException Database error
     */
    boolean hasExistingEmptyPerson (String firstName, String lastName) throws SQLException;


    /**
     * Get a program by name
     * @param programName Name of the program
     * @return Returns matching program
     * @throws SQLException Database error
     */
    Program getProgram (String programName) throws SQLException;

    /**
     * Approve a program
     * @param programID Id of the program
     * @param approved true to approve, false to not approve
     * @throws SQLException Database error
     */
    void setApproved (int programID, boolean approved) throws SQLException;

    /**
     * Creates a new program for a producer
     * @param producerID The id of the producer
     * @param name The name of the new program
     * @param internalID The internal id of the new program
     * @return Returns new program
     * @throws SQLException Database error
     */
    Program createProgram (int producerID, String name, int internalID) throws SQLException;

    /**
     * Get a person by id
     * @param personID Id of the person
     * @return Returns matching person
     * @throws SQLException Database error
     */
    Person getPerson (int personID) throws SQLException;
}
