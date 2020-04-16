package sdu.se9.tv2.management.system;

import sdu.se9.tv2.management.system.exceptions.UsernameAlreadyExistsException;

/**
 * Interface for persistence for accounts
 */
public interface IPersistenceAccount {
    /**
     * Gets an account by username and password
     * @param username The username of the account
     * @param password The password of the account
     * @return
     */
    Account getMatchingAccount (String username, String password);

    /**
     * Checks if a username is already used
     * @param username The username to check
     * @return
     */
    boolean usernameTaken (String username);

    /**
     * Creates a new admin account by username and password
     * @param username
     * @param password
     * @return
     */
    AdminAccount createAdminAccount (String username, String password) throws UsernameAlreadyExistsException;

    /**
     * Creates a new producer account by username, password and producerId
     * @param username The username of the account
     * @param password The password of the account
     * @param producerId The producer ID for the account
     * @return
     */
    ProducerAccount createProducerAccount (String username, String password, int producerId) throws UsernameAlreadyExistsException;
}
