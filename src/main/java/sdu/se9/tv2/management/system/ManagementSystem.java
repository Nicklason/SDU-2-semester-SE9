package sdu.se9.tv2.management.system;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import sdu.se9.tv2.management.system.exceptions.UsernameAlreadyExistsException;

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

    private IPersistenceAccount persistenceAccount;

    private Account account = null;

    private boolean running = true;

    private Scanner scanner = new Scanner(System.in);

    ManagementSystem () {
        this.persistenceProducer = new PersistenceProducer();
        this.persistenceProgram = new PersistenceProgram();
        this.persistencePerson = new PersistencePerson();
        this.persistenceCredit = new PersistenceCredit();
        this.persistenceAccount = new PersistenceAccount();
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
                case "setpendingapproval":
                    this.setPendingApproval();
                    break;
                case "setapproved":
                    this.setApproved();
                    break;
                case "createcredit":
                    this.createCredit();
                    break;
                case "getcredit":
                    this.getCredit();
                    break;
                case "getcredits":
                    this.getCredits();
                    break;
                case "createperson":
                    this.createPerson();
                    break;
                case "getperson":
                    this.getPerson();
                    break;
                case "exportcredits":
                    this.exportCredits();
                    break;
                case "login":
                    this.login();
                    break;
                case "createproduceraccount":
                    this.createProducerAccount();
                default:
                    System.out.println("stop, help, createproducer, getproducer, createprogram, getprogram," +
                            " setapproved, createcredit, getcredit, getcredits, getperson, exportcredits," +
                            " createperson, setpendingapproval, login, createproduceraccount");

            }
        }

        scanner.close();
    }

    public void createProducerAccount() {
        if (account == null) {
            System.out.println("Du er ikke logget ind");
            return;
        }
        if (!account.getType().equals("admin")) {
            System.out.println("Du har ikke adgang til denne funktion");
            return;
        }

        Producer producer = this.getProducer();
        if (producer == null) {
            System.out.println("Producenten blev ikke fundet");
            return;
        }

        int accountCount = persistenceAccount.getProducerAccountCount(producer.getID());

        System.out.println("Hvor mange nye konti skal produceren have? (kun hele tal)");

        int numberOfAccounts = Integer.parseInt(scanner.nextLine());

        ArrayList<ProducerAccount> accounts = new ArrayList<ProducerAccount>();

        for (int i = 0; i < numberOfAccounts; i++) {
            int accountId = accountCount+i+1;
            String username = producer.getName()+ "-" +accountId;
            String password = producer.getName() + "123";
            try {
                ProducerAccount account = persistenceAccount.createProducerAccount(username, password, producer.getID());
                accounts.add(account);
            } catch (UsernameAlreadyExistsException e) {
                e.printStackTrace();
            }
        }
        System.out.println(accounts);

    }

    public void login() {
        System.out.println("Hvad er dit brugernavn?");

        String username = scanner.nextLine();

        System.out.println("Hvad er din adgangskode?");

        String password = scanner.nextLine();

        Account account = persistenceAccount.getMatchingAccount(username, password);
        if (account == null) {
            System.out.println("Forkert brugernavn/adgangskode");
            return;
        }
        this.account = account;
        System.out.println(account);

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
        Program program = this.getProgram();

        if (program == null) {
            System.out.println("Fandt ikke programmet");
            return;
        }

        Person person = this.getPerson();

        if (person == null) {
            System.out.println("Fandt ikke personen");
            return;
        }

        System.out.println("Indtast rolle");

        String roleName = scanner.nextLine();

        Credit credit = persistenceCredit.createCredit(program.getID(), person.getId(), roleName);

        System.out.println(credit);
    }

    public void getCredit() {
        Program program = this.getProgram();

        if (program == null) {
            System.out.println("Program blev ikke fundet.");
            return;
        }

        Person person = this.getPerson();

        if (person == null) {
            System.out.println("Person blev ikke fundet");
            return;
        }

        System.out.println("Indtast rolle");

        String roleName = scanner.nextLine();

        System.out.println(persistenceCredit.getCredit(program.getID(), person.getId(), roleName));
    }

    public void getCredits() {
        Program program = this.getProgram();

        System.out.println(persistenceCredit.getCredits(program.getID()));
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
        System.out.println("Hvad er navnet på programmet?");

        String programName = scanner.nextLine();
        Program program = persistenceProgram.getProgram(programName);
        System.out.println(program);

        return program;
    }

    public Person createPerson () {
        System.out.println("Hvad er personens fornavn?");

        String firstName = scanner.nextLine();

        System.out.println("Hvad er personens efternavn?");

        String lastName = scanner.nextLine();

        Person person = this.persistencePerson.createPerson(firstName, lastName);

        System.out.println(person);

        return person;
    }

    public ArrayList<Person> getPersons (String firstName, String lastName) {
        return this.persistencePerson.getPersons(firstName, lastName);
    }

    public Person getPerson () {
        System.out.println("Hvad er personens fornavn?");

        String firstName = scanner.nextLine();

        System.out.println("Hvad er personens efternavn?");

        String lastName = scanner.nextLine();

        ArrayList<Person> people = persistencePerson.getPersons(firstName, lastName);

        int peopleCount = people.size();

        Person person = null;

        if (peopleCount > 1) {
            // Found multiple people with matching name, choose using id instead
            System.out.println("Fandt " + peopleCount + " personer med matchene navn, vælg en af dem med deres ID: " + people.toString());

            int personID = Integer.parseInt(scanner.nextLine());

            // Return person (might be null if the id does not exist)
            person = persistencePerson.getPerson(personID);
        } else if (peopleCount == 1) {
            person = people.get(0);
        }

        System.out.println(person);

        return person;
    }

    public void setPendingApproval() {
        Program program = this.getProgram();

        if (program == null) {
            System.out.println("Fandt ikke noget program");
            return;
        }

        persistenceProgram.setAwaitingApproval(program.getID(), true);
    }

    public void setApproved() {
        Program program = this.getProgram();

        if(program != null){
            persistenceProgram.setApproved(program.getID(), true);
            persistenceProgram.setAwaitingApproval(program.getID(), false);
            System.out.println("Krediteringen for programmet er godkendt.");
        } else {
            System.out.println("Programmet blev ikke fundet.");
        }
    }

    public void exportCredits() {
        Program program = getProgram();

        System.out.println("Indtast ønskede filformat");

        String fileFormat = scanner.nextLine();

        if (!fileFormat.equals("json")) {
            System.out.println("Kan kun eksporterer til json");
            return;
        }



        Persistence persistence = new Persistence(program.getName().replaceAll("\\?","") + ".json");
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
