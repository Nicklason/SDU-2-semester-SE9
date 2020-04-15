package sdu.se9.tv2.management.system;

import org.json.simple.JSONObject;

public class Person {
    private String firstName;
    private String lastName;
    private int id;

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

    public static Person parseJSON (JSONObject person) {
        int personID = Math.toIntExact((Long)person.get("id"));
        String firstName = (String)person.get("firstName");
        String lastName = (String)person.get("lastName");

        return new Person(personID, firstName, lastName);
    }

    public static JSONObject parseJSON(Person person) {
        JSONObject obj = new JSONObject();

        obj.put("firstName", person.getFirstName());
        obj.put("lastName", person.getLastName());
        obj.put("id", person.getId());

        return obj;
    }
}
