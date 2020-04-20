package sdu.se9.tv2.management.system.domain;

import sdu.se9.tv2.management.system.domain.accounts.Account;
import sdu.se9.tv2.management.system.persistence.PersistenceCredit;
import sdu.se9.tv2.management.system.persistence.PersistencePerson;

import java.util.ArrayList;

public class ManagementSystem {

    private static ManagementSystem instance = null;

    public static ManagementSystem getInstance() {
        if (instance == null) {
            instance = new ManagementSystem();
        }

        return new ManagementSystem();
    }

    private Account account = null;

    private ManagementSystem () {};

    public void setAccount(Account account) {
        this.account = account;
    }

    public boolean isLoggedIn () {
        return this.account != null;
    }

    public boolean hasExistingEmptyPerson (String firstname, String lastname) {
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
}
