package sdu.se9.tv2.management.system.persistence;

import sdu.se9.tv2.management.system.domain.accounts.Account;
import sdu.se9.tv2.management.system.domain.accounts.AdminAccount;
import sdu.se9.tv2.management.system.domain.accounts.ProducerAccount;
import sdu.se9.tv2.management.system.domain.accounts.SystemAdminAccount;
import sdu.se9.tv2.management.system.exceptions.UsernameAlreadyExistsException;

/**
 * Interface for persistence for accounts
 */
public interface IPersistenceAccount {
    /**
     * Gets an account by username and password
     *
     * @param username The username of the account
     * @param password The password of the account
     * @return
     */
    Account getMatchingAccount(String username, String password);

    /**
     * Checks if a username is already used
     * @param username The username to check
     * @return
     */
    boolean usernameTaken (String username);

    /**
     * Creates a new admin account
     * @param username
     * @param password
     * @return
     * @throws UsernameAlreadyExistsException
     */
    AdminAccount createAdminAccount (String username, String password) throws UsernameAlreadyExistsException;

    /**
     * Creates a new producer account
     * @param username The username of the account
     * @param password The password of the account
     * @param producerId The producer ID for the account
     * @return
     * @throws UsernameAlreadyExistsException
     */
    ProducerAccount createProducerAccount (String username, String password, int producerId) throws UsernameAlreadyExistsException;

    /**
     * Creates a new system admin account
     * @param username The username of the account
     * @param password The password of the account
     * @return
     * @throws UsernameAlreadyExistsException
     */
    SystemAdminAccount createSystemAdminAccount (String username, String password) throws UsernameAlreadyExistsException;

    /**
     * Gets the aomunt of accounts for the specific producer
     * @param producerId
     * @return
     */
    int getProducerAccountCount (int producerId);

}
