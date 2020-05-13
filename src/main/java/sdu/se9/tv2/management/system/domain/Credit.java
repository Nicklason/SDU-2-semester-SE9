package sdu.se9.tv2.management.system.domain;

public class Credit {
    private int id;
    private String role;
    private int personID;
    private int programID;

    public Credit(int id, int programID, int personID, String role) {
        this.id = id;
        this.role = role;
        this.personID = personID;
        this.programID = programID;
    }

    public int getID() {
        return id;
    }

    public int getProgramID() {
        return programID;
    }

    public String getRole() {
        return role;
    }

    public int getPersonID() {
        return personID;
    }

    @Override
    public String toString() {
        return "Credit{" +
                "id='" + id + '\'' +
                ", programID='" + programID + '\'' +
                ", role='" + role + '\'' +
                ", personID=" + personID +
                '}';
    }
}
