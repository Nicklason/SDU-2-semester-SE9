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
                case "logout":
                    this.logout();
                    break;
                case "createproduceraccount":
                    this.createProducerAccount();
                    break;
                case "createadminaccount":
                    this.createAdminAccount();
                    break;
                default:
                    System.out.println("stop, help, createproducer, getproducer, createprogram, getprogram," +
                            " setapproved, createcredit, getcredit, getcredits, getperson, exportcredits," +
                            " createperson, setpendingapproval, login, logout, createproduceraccount, createadminaccount");

            }
        }

        scanner.close();
    }

    public boolean isLoggedIn() {
        return this.account != null;
    }

    public boolean hasAccess (String requiredAccountType) {
        if (!this.isLoggedIn()) {
            return false;
        }

        return account.getType().equals(requiredAccountType);
    }

    public boolean hasAccessToProducer (int producerId) {
        if (!this.hasAccess("producer")) {
            return false;
        }

        ProducerAccount producerAccount = (ProducerAccount)this.account;

        return producerAccount.getProducerId() == producerId;
    }

    public void createProducerAccount() {
        if (!this.hasAccess("admin")) {
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

    public void createAdminAccount () {
        if (!this.hasAccess("systemadmin")) {
            System.out.println("Du har ikke adgang til denne funktion");
            return;
        }

        System.out.println("Hvad skal brugernavnet være?");

        String username = scanner.nextLine();

        System.out.println("Hvad skal adgangskoden være?");

        String password = scanner.nextLine();

        try {
            System.out.println(persistenceAccount.createAdminAccount(username, password));
        } catch (UsernameAlreadyExistsException e) {
            System.out.println("En bruger med dette brugernavn findes allerede");
        }
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

    public void logout () {
        this.account = null;
        System.out.println("Du er nu logget ud");
    }

    public void createProducer () {
        if (!this.hasAccess("admin")) {
            System.out.println("Du har ikke adgang til denne funktion");
            return;
        }

        System.out.println("Hvad er navnet på den nye producent?");

        String producerName = scanner.nextLine();

        System.out.println(persistenceProducer.createProducer(producerName));
    }

    public Producer getProducer () {
        // Anyone can see producers
        System.out.println("Hvilken producer leder du efter?");

        String producerName = scanner.nextLine();
        Producer producer = persistenceProducer.getProducer(producerName);
        System.out.println(producer);
        return producer;
    }

    public void createCredit () {
        boolean isProducer = this.hasAccess("producer");
        if (!this.hasAccess("admin") && !isProducer) {
            System.out.println("Du har ikke adgang til denne funktion");
            return;
        }

        Program program = this.getProgram();

        if (program == null) {
            System.out.println("Fandt ikke programmet / du har ikke adgang til programmet");
            return;
        } else if (isProducer) {
            ProducerAccount producer = (ProducerAccount)account;

            if (producer.getProducerId() != program.getProducerID()) {
                // Producer does not own this program, return error message as if the program was not found
                System.out.println("Fandt ikke programmet / du har ikke adgang til programmet");
                return;
            }
        }

        if (program.isApproved() && isProducer) {
            System.out.println("Programmet er allerede godkendt");
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

        if (program == null) {
            return;
        }

        System.out.println(persistenceCredit.getCredits(program.getID()));
    }

    public void createProgram () {
        // TODO: Check if program already exists

        if (!hasAccess("producer")) {
            System.out.println("Du har ikke adgang til denne funktion");
            return;
        }

        ProducerAccount producerAccount = (ProducerAccount)account;

        System.out.println("Hvad er navnet på programmet?");

        String programName = scanner.nextLine();

        System.out.println("Hvad er det interne ID?");

        int internalID = Integer.parseInt(scanner.nextLine());

        Program program = this.persistenceProgram.createProgram(producerAccount.getProducerId(), programName, internalID);

        System.out.println(program);
    }

    public Program getProgram() {
        System.out.println("Hvad er navnet på programmet?");

        String programName = scanner.nextLine();
        Program program = persistenceProgram.getProgram(programName);

        if (program == null) {
            System.out.println(program);
            System.out.println("Program blev ikke fundet");
            return null;
        }

        if (!program.isApproved() && !hasAccess("admin") && !this.hasAccessToProducer(program.getProducerID())) {
            System.out.println("null");
            System.out.println("Program blev ikke fundet");
            return null;
        }

        System.out.println(program);

        return program;
    }

    public Person createPerson () {
        if (!hasAccess("admin") && !hasAccess("producer")) {
            System.out.println("Du har ikke adgang til denne funktion");
            return null;
        }

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
            return;
        }

        if (!hasAccess("producer")) {
            System.out.println("Du har ikke adgang til denne funktion");
            return;
        }

        if (program.isApproved()) {
            System.out.println("Programmet er allerede godkendt");
            return;
        }

        persistenceProgram.setAwaitingApproval(program.getID(), true);
    }

    public void setApproved() {
        if (!hasAccess("admin")) {
            System.out.println("Du har ikke adgang til denne funktion");
            return;
        }

        Program program = this.getProgram();

        if(program != null){
            persistenceProgram.setApproved(program.getID(), true);
            persistenceProgram.setAwaitingApproval(program.getID(), false);
            System.out.println("Krediteringen for programmet er godkendt.");
        }
    }

    public void exportCredits() {
        Program program = getProgram();

        if (!hasAccess("admin") && !hasAccess("producer")) {
            System.out.println("Du har ikke adgang til denne funktion");
            return;
        }

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

        obj.put("programNavn", program.getName());
        obj.put("programID", program.getID());

        // JSONArray that contains the producers
        JSONArray list = new JSONArray();

        // Go through producer list and parse as JSON objects
        for (int i = 0; i < credits.size(); i++) {
            Credit credit = credits.get(i);
            JSONObject jsonCredit = new JSONObject();

            Person person = persistencePerson.getPerson(credit.getPersonID());

            jsonCredit.put("rolle", credit.getRole());
            jsonCredit.put("personID", credit.getPersonID());
            jsonCredit.put("fornavn", person.getFirstName());
            jsonCredit.put("efternavn", person.getLastName());

            list.add(jsonCredit);
        }

        // Add lastID to JSON object
        //obj.put("lastID", lastID);
        // Add list to JSON object
        obj.put("medvirkende", list);

        // Overwrite the file with new JSON object
        persistence.write(obj);
    }
}
