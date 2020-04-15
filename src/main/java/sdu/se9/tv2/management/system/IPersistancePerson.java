package sdu.se9.tv2.management.system;

import java.util.ArrayList;

public interface IPersistancePerson {
    /**
     * Creates a new person
     * @param firstName The person's firstname
     * @param lastName The person's lastname
     * @return
     */
    Person createPerson (String firstName, String lastName);

    /**
     * Get a list of people with matching names
     * @param firstName Firstname to search for
     * @param lastName Lastname to search for
     * @return
     */
    ArrayList<Person> getPersons (String firstName, String lastName);
}
