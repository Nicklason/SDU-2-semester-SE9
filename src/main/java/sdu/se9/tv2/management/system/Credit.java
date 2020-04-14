package sdu.se9.tv2.management.system;

public class Credit {
    private String role;
    private Person person;

    public Credit(String role, Person person) {
        this.role = role;
        this.person = person;
    }

    public String getRole() {
        return role;
    }

    public Person getPerson() {
        return person;
    }
}
