package sdu.se9.tv2.management.system.domain;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import sdu.se9.tv2.management.system.domain.accounts.Account;
import sdu.se9.tv2.management.system.domain.accounts.ProducerAccount;
import sdu.se9.tv2.management.system.exceptions.DuplicateRoleNameException;
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
    private IPersistencePerson persistencePerson = null;
    private IPersistenceCredit persistenceCredit = null;
    private IPersistenceAccount persistenceAccount = null;
    private IPersistenceProducer persistenceProducer = null;

    private ManagementSystem () {
        persistenceProgram = new PersistenceProgram();
        persistencePerson = new PersistencePerson();
        persistenceCredit = new PersistenceCredit();
        persistenceAccount = new PersistenceAccount();
        persistenceProducer = new PersistenceProducer();
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

        Producer producer = this.persistenceProducer.getProducer(producerId);

        int accountCount = this.persistenceAccount.getProducerAccountCount(producerId);

        for (int i = 0; i < amount; i++) {
            int accountId = accountCount + i + 1;
            String username = producer.getName() + "-" + accountId;
            String password = producer.getName() + "123";

            ProducerAccount account = this.persistenceAccount.createProducerAccount(username, password, producer.getID());
            accounts.add(account);

        }

        return accounts;
    }

    public boolean hasExistingEmptyPerson (String firstname, String lastname) throws SQLException {
        ArrayList<Person> people = this.persistencePerson.getPersons(firstname, lastname);

        for (int i = 0; i < people.size(); i++) {
            Person person = people.get(i);

            int creditCount = this.persistenceCredit.getCreditsByPerson(person.getId(), 1).size();

            if (creditCount == 0) {
                return true;
            }
        }

        return false;
    }

    public void setApproved (int programID, boolean approved) throws SQLException {
        this.persistenceProgram.setApproved(programID, true);
    }

    public Person getPerson (int personID) throws SQLException {
        return this.persistencePerson.getPerson(personID);
    }

    public Program getProgram (String programName) throws SQLException {
        return this.persistenceProgram.getProgram(programName);
    }

    public Program getProgram (int programID) throws SQLException {
        return this.persistenceProgram.getProgram(programID);
    }

    public Program createProgram(int producerID, String name, int internalID) throws SQLException {
        return this.persistenceProgram.createProgram(producerID, name, internalID);
    }

    public void exportProgramToFile (Program program) throws SQLException {
        PersistenceJSONFileHelper persistence = new PersistenceJSONFileHelper(program.getName().replaceAll("\\?", "") + ".json");
        ArrayList<Credit> credits = this.getCredits(program.getID());

        // Create JSONObject to save
        JSONObject obj = new JSONObject();

        obj.put("programNavn", program.getName());
        obj.put("programID", program.getID());

        // JSONArray that contains the producers
        JSONArray list = new JSONArray();

        // Go through producer list and parse as JSON objects
        for (int i = 0; i < credits.size(); i++) {
            Credit credit = credits.get(i);
            JSONObject jsonCredit = new JSONObject();

            Person person = this.getPerson(credit.getPersonID());

            jsonCredit.put("rolle", credit.getRole());
            jsonCredit.put("personID", credit.getPersonID());
            jsonCredit.put("fornavn", person.getFirstName());
            jsonCredit.put("efternavn", person.getLastName());

            list.add(jsonCredit);
        }

        // Add list to JSON object
        obj.put("medvirkende", list);

        // Overwrite the file with new JSON object
        persistence.write(obj);
    }

    public Person createPerson(String firstName, String lastName) throws SQLException {
        return this.persistencePerson.createPerson(firstName, lastName);
    }

    public ArrayList<Person> getPersons (String firstName, String lastName) throws SQLException {
        return this.persistencePerson.getPersons(firstName, lastName);
    }

    public Credit createCredit (int programID, int personID, String roleName) throws DuplicateRoleNameException, SQLException {
        return this.persistenceCredit.createCredit(programID, personID, roleName);
    }

    public ArrayList<Credit> getCreditsByPerson(int personID, int maxCount) throws SQLException {
        return this.persistenceCredit.getCreditsByPerson(personID, maxCount);
    }

    public Account getMatchingAccount(String username, String password) throws SQLException {
        return this.persistenceAccount.getMatchingAccount(username, password);
    }

    public Producer createProducer(String producerName) throws SQLException {
        return this.persistenceProducer.createProducer(producerName);
    }

    public Producer getProducer(String producerName) throws SQLException {
        return this.persistenceProducer.getProducer(producerName);
    }

    public ArrayList<Credit> getCredits(int programID) throws SQLException {
        return this.persistenceCredit.getCredits(programID);
    }
}
