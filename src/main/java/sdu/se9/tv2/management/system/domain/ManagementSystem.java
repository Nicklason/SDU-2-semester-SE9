package sdu.se9.tv2.management.system.domain;

import sdu.se9.tv2.management.system.domain.accounts.Account;
import sdu.se9.tv2.management.system.domain.accounts.ProducerAccount;
import sdu.se9.tv2.management.system.persistence.*;

import java.sql.SQLException;
import java.util.ArrayList;

public class ManagementSystem implements IManagementSystem {

    private static ManagementSystem instance = null;

    /**
     * Singleton for creating an instance of the ManagementSystem class
     * It is needed because controllers need easy access to the domain layer
     * @return
     */
    public static ManagementSystem getInstance() {
        if (instance == null) {
            instance = new ManagementSystem();
        }

        return instance;
    }

    private Account account = null;

    private IPersistenceProgram persistenceProgram = null;

    private ManagementSystem () {
        persistenceProgram = new PersistenceProgram();
    };

    public void setAccount(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

    public boolean isLoggedIn () {
        return this.account != null;
    }

    public boolean hasAccess (String requiredAccountType) {
        if (!this.isLoggedIn()) {
            return false;
        }

        return account.getType().equals(requiredAccountType);

    }

    public ArrayList<ProducerAccount> createAccountsForProducer(int producerId, int amount) throws SQLException {

        ArrayList<ProducerAccount> accounts = new ArrayList<ProducerAccount>();

        Producer producer = PersistenceProducer.getInstance().getProducer(producerId);

        int accountCount = PersistenceAccount.getInstance().getProducerAccountCount(producerId);

        for (int i = 0; i < amount; i++) {
            int accountId = accountCount + i + 1;
            String username = producer.getName() + "-" + accountId;
            String password = producer.getName() + "123";

            ProducerAccount account = PersistenceAccount.getInstance().createProducerAccount(username, password, producer.getID());
            accounts.add(account);

        }

        return accounts;
    }

    public boolean hasExistingEmptyPerson (String firstname, String lastname) throws SQLException {
        ArrayList<Person> people = PersistencePerson.getInstance().getPersons(firstname, lastname);

        for (int i = 0; i < people.size(); i++) {
            Person person = people.get(i);

            int creditCount = PersistenceCredit.getInstance().getCreditsByPerson(person.getId(), 1).size();

            if (creditCount == 0) {
                return true;
            }
        }

        return false;
    }

    public void setApproved (int programID, boolean approved) throws SQLException {
        this.persistenceProgram.setApproved(programID, true);
    }

    public Program getProgram (String programName) throws SQLException {
        return this.persistenceProgram.getProgram(programName);
    }

    public Program createProgram(int producerID, String name, int internalID) throws SQLException {
        return this.persistenceProgram.createProgram(producerID, name, internalID);
    }
}
