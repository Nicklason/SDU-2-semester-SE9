package sdu.se9.tv2.management.system;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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
        System.out.println("Skriv \"help\" for en liste med alle kommandoerne.");

        while (running) {
            switch (scanner.nextLine().toLowerCase()) {
                case "stop":
                    running = false;
                    break;
                case "createproducer":
                    this.createProducer();
                    break;
                case "getproducer":
                    this.getProducer();
                    break;
                default:
                    System.out.println("stop, help, createproducer, getproducer");
            }
        }

        scanner.close();
    }

    public void createProducer () {
        System.out.println("Hvad er navnet på den nye producent?");

        String producerName = scanner.nextLine();

        System.out.println(persistenceProducer.createProducer(producerName));
    }

    public void getProducer () {
        System.out.println("Hvilken producer leder du efter?");

        String producerName = scanner.nextLine();

        System.out.println(persistenceProducer.getProducer(producerName));
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

    public void exportCredits(int programID, String fileFormat) {
        Program program = getProgram(programID);
        Persistence persistence = new Persistence(program.getName() + "." + fileFormat);
        write(persistence, getCredits(programID));
    }

    private void write(Persistence persistence, ArrayList<Credit> credits) {
        // Create JSONObject to save
        JSONObject obj = new JSONObject();

        // JSONArray that contains the producers
        JSONArray list = new JSONArray();

        // Go through producer list and parse as JSON objects
        for (int i = 0; i < credits.size(); i++) {
            list.add(Credit.parseJSON(credits.get(i)));
        }

        // Add lastID to JSON object
        //obj.put("lastID", lastID);
        // Add list to JSON object
        obj.put("list", list);

        // Overwrite the file with new JSON object
        persistence.write(obj);
    }
}
