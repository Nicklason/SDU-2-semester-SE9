package sdu.se9.tv2.management.system.presentation;

import sdu.se9.tv2.management.system.domain.Credit;
import sdu.se9.tv2.management.system.domain.Person;
import sdu.se9.tv2.management.system.persistence.PersistencePerson;

import java.sql.SQLException;

public class CreditTableViewItem {
    private final Credit credit;
    private final Person person;
    private final String personID;
    private final String firstName;
    private final String lastName;
    private final String role;

    public CreditTableViewItem (Credit credit) throws SQLException {
        this.credit = credit;
        this.person = PersistencePerson.getInstance().getPerson(credit.getPersonID());
        this.personID = Integer.toString(this.person.getId());
        this.firstName = this.person.getFirstName();
        this.lastName = this.person.getLastName();
        this.role = this.credit.getRole();
    }

    public String getPersonID() {
        return personID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getRole() {
        return role;
    }
}
