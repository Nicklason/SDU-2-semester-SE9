package sdu.se9.tv2.management.system.domain;

public class Person {
    private int id;
    private String firstName;
    private String lastName;

    public Person(int personID, String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = personID;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.firstName + " " + this.lastName;
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", id=" + id +
                '}';
    }
}
