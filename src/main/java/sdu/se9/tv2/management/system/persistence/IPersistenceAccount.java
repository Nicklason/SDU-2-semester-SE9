package sdu.se9.tv2.management.system.persistence;

import kotlin.jvm.Throws;
import sdu.se9.tv2.management.system.domain.accounts.Account;
import sdu.se9.tv2.management.system.domain.accounts.AdminAccount;
import sdu.se9.tv2.management.system.domain.accounts.ProducerAccount;
import sdu.se9.tv2.management.system.domain.accounts.SystemAdminAccount;

import java.sql.SQLException;
import java.util.ArrayList;

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
    Account getMatchingAccount(String username, String password) throws SQLException;

    /**
     * Creates a new admin account
     * @param username
     * @param password
     * @return
     */
    AdminAccount createAdminAccount (String username, String password) throws SQLException;

    /**
     * Creates a new producer account
     * @param username The username of the account
     * @param password The password of the account
     * @param producerId The producer ID for the account
     * @return
     */
    ProducerAccount createProducerAccount (String username, String password, int producerId) throws SQLException;

    /**
     * Creates a new system admin account
     * @param username The username of the account
     * @param password The password of the account
     * @return
     */
    SystemAdminAccount createSystemAdminAccount (String username, String password) throws SQLException;

    /**
     * Gets the aomunt of accounts for the specific producer
     * @param producerId
     * @return
     */
    int getProducerAccountCount (int producerId) throws SQLException;

}
