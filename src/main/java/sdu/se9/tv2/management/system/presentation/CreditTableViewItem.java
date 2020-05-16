package sdu.se9.tv2.management.system.presentation;

import sdu.se9.tv2.management.system.domain.Credit;
import sdu.se9.tv2.management.system.domain.IManagementSystem;
import sdu.se9.tv2.management.system.domain.ManagementSystem;
import sdu.se9.tv2.management.system.domain.Person;

import java.sql.SQLException;

public class CreditTableViewItem {
    private IManagementSystem managementSystem = ManagementSystem.getInstance();
    private String personID;
    private String firstName;
    private String lastName;
    private String role;

    public CreditTableViewItem (Credit credit) throws SQLException {
        Person person = managementSystem.getPerson(credit.getPersonID());
        this.personID = Integer.toString(person.getId());
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.role = credit.getRole();
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
