package sdu.se9.tv2.management.system.persistence;

import sdu.se9.tv2.management.system.domain.Person;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IPersistencePerson {
    /**
     * Creates a new person
     * @param firstName The person's firstname
     * @param lastName The person's lastname
     * @return
     */
    Person createPerson (String firstName, String lastName) throws SQLException;

    /**
     * Get a person by person ID
     * @param personID The ID of the person
     * @return
     */
    Person getPerson (int personID) throws SQLException;

    /**
     * Get a list of people with matching names
     * @param firstName Firstname to search for
     * @param lastName Lastname to search for
     * @return
     */
    ArrayList<Person> getPersons (String firstName, String lastName) throws SQLException;
}
