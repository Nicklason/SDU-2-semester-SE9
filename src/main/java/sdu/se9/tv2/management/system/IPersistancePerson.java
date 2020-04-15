package sdu.se9.tv2.management.system;

public interface IPersistancePerson {
    /**
     * Creates a new person
     * @param firstName The person's firstname
     * @param lastName The person's lastname
     * @return
     */
    Person createPerson (String firstName, String lastName);
}
