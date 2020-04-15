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
                case "createprogram":
                    this.createProgram();
                    break;
                case "getprogram":
                    this.getProgram();
                    break;
                case "setapproved":
                    this.setApproved();
                    break;
                case "exportcredits":
                    this.exportCredits();
                    break;
                default:
                    System.out.println("stop, help, createproducer, getproducer, createprogram, getprogram, setapproved");
            }
        }

        scanner.close();
    }

    public void createProducer () {
        System.out.println("Hvad er navnet på den nye producent?");

        String producerName = scanner.nextLine();

        System.out.println(persistenceProducer.createProducer(producerName));
    }

    public Producer getProducer () {
        System.out.println("Hvilken producer leder du efter?");

        String producerName = scanner.nextLine();
        Producer producer = persistenceProducer.getProducer(producerName);
        System.out.println(producer);
        return producer;
    }

    public void createCredit (){
        System.out.println("Indtast programID");

        String programID = scanner.nextLine();

        System.out.println("Indtast personID");

        String personID = scanner.nextLine();

        System.out.println("Indtast rolle");

        String roleName = scanner.nextLine();

        Credit credit = persistenceCredit.createCredit(Integer.parseInt(programID), Integer.parseInt(personID), roleName);

        System.out.println(credit);
    }

    public void getCredit() {
        System.out.println("Indtast programID");

        String programID = scanner.nextLine();

        System.out.println("Indtast personID");

        String personID = scanner.nextLine();

        System.out.println("Indtast rolle");

        String roleName = scanner.nextLine();
        ArrayList<Credit> creditList = persistenceCredit.getCredits(Integer.parseInt(programID));

        for (int i = 0; i < creditList.size(); i++) {
            Credit credit = creditList.get(i);

            if (credit.getProgramID() == Integer.parseInt(programID) && credit.getPersonID() == Integer.parseInt(personID) && credit.getRole() == roleName) {
                System.out.println(credit);
            }
        }

        System.out.println("Der er ingen kreditering for de indtastede oplysninger");;
    }

    public void getCredits() {
        System.out.println("Hvilket program vil du se krediteringer for?");

        String programID = scanner.nextLine();

        persistenceCredit.getCredits(Integer.parseInt(programID));
    }

    public void createProgram () {
        // TODO: Check if program already exists

        Producer producer = this.getProducer();

        if (producer == null) {
            return;
        }

        System.out.println("Hvad er navnet på programmet?");

        String programName = scanner.nextLine();

        System.out.println("Hvad er det interne ID?");

        int internalID = Integer.parseInt(scanner.nextLine());

        Program program = this.persistenceProgram.createProgram(producer.getID(), programName, internalID);

        System.out.println(program);
    }

    public Program getProgram() {
        System.out.println("Hvilket program leder du efter?");

        String programName = scanner.nextLine();
        Program program = persistenceProgram.getProgram(programName);
        System.out.println(program);
        return program;

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

    public void setApproved() {
        Program program = this.getProgram();

        if(program != null){
            persistenceProgram.setApproved(program.getID(), true);
            System.out.println("Krediteringen for programmet er godkendt.");
        } else {
            System.out.println("Programmet belv ikke fundet.");
        }
    }

    public void exportCredits() {
        Program program = getProgram();

        System.out.println("Indtast ønskede filformat");

        String fileFormat = scanner.nextLine();

        if (fileFormat != "json") {
            System.out.println("Kan kun eksporterer til json");
            return;
        }

        Persistence persistence = new Persistence(program.getName() + ".json");
        ArrayList<Credit> credits = persistenceCredit.getCredits(program.getID());

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
