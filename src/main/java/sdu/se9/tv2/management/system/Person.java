package sdu.se9.tv2.management.system;

public class Person {
    private String firstName;
    private String lastName;
    private int personID;

    public Person(String firstName, String lastName, int personID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.personID = personID;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public int getPersonID() {
        return this.personID;
    }

    public String GetName() {
        return this.firstName + " " + this.lastName;
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", personID=" + personID +
                '}';
    }
}
