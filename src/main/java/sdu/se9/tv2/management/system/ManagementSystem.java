package sdu.se9.tv2.management.system;

import java.util.ArrayList;
import java.util.Scanner;

public class ManagementSystem {
    private static ManagementSystem instance;

    public static ManagementSystem getSingleton() {
        if (instance == null) {
            instance = new ManagementSystem();
        }

        return instance;
    }

    private IPersistenceProducer persistenceProducer;

    private IPersistenceProgram persistenceProgram;

    private IPersistencePerson persistencePerson;

    private IPersistenceCredit persistenceCredit;

    private boolean running = true;

    private Scanner scanner = new Scanner(System.in);

    ManagementSystem () {
        this.persistenceProducer = new PersistenceProducer();
        this.persistenceProgram = new PersistenceProgram();
        this.persistencePerson = new PersistencePerson();
        this.persistenceCredit = new PersistenceCredit();
    }

    public void run () {
        while (running) {
            switch (scanner.nextLine().toLowerCase()) {
                case "stop":
                    running = false;
                    break;
                case "createproducer":
                    this.createProducer();
                    break;
            }
        }

        scanner.close();
    }

    public void createProducer () {
        System.out.println("Hvad er navnet på den nye producent?");

        String producerName = scanner.nextLine();

        persistenceProducer.createProducer(producerName);
    }

    public Producer getProducer (int producerID) {
        return persistenceProducer.getProducer(producerID);
    }

    public Credit createCredit (int programID, int personID, String roleName){
        Credit newCredit = persistenceCredit.createCredit( programID, personID, roleName);
        return newCredit;
    }

    public Credit getCredit(int programID, int personID, String roleName) {
        ArrayList<Credit> creditList = persistenceCredit.getCredits(programID);

        for (int i = 0; i < creditList.size(); i++) {
            Credit credit = creditList.get(i);

            if (credit.getProgramID() == programID && credit.getPersonID() == personID && credit.getRole() == roleName) {
                return credit;
            }
        }

        return null;
    }

    public ArrayList<Credit> getCredits(int programID) {
        return persistenceCredit.getCredits(programID);
    }

    public Program createProgram (int producerID, String programName, int internalID) {
        // TODO: Check if program already exists
        return this.persistenceProgram.createProgram(producerID, programName, internalID);
    }

    public Program getProgram(int programID) {
        return this.persistenceProgram.getProgram(programID);
    }

    public Person createPerson (String firstName, String lastName) {
        return this.persistencePerson.createPerson(firstName, lastName);
    }

    public ArrayList<Person> getPersons (String firstName, String lastName) {
        return this.persistencePerson.getPersons(firstName, lastName);
    }

    public Person getPerson (int personID) {
        return this.persistencePerson.getPerson(personID);
    }

    public void setPendingApproval(int programID, boolean pendingApproval) {
        persistenceProgram.setAwaitingApproval(programID, pendingApproval);
    }

    public void setApproved(int programID, boolean approved) {
        persistenceProgram.setApproved(programID, approved);
    }

    public Credit exportCredits(int programID, String fileFormat) {
        // add logic here
        return null;
    }
}
