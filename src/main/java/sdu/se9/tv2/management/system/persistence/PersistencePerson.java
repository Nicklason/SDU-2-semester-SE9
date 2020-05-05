package sdu.se9.tv2.management.system.persistence;
import org.json.simple.parser.ParseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import sdu.se9.tv2.management.system.domain.Credit;
import sdu.se9.tv2.management.system.domain.Person;

/**
 * Implementation of the IPersistencePerson interface
 */
public class PersistencePerson implements IPersistencePerson {

    private static PersistencePerson instance = null;

    public static PersistencePerson getInstance() {
        if (instance == null) {
            instance = new PersistencePerson();
        }

        return instance;
    }

    /**
     * Instance of persistence class for file
     */
    private Persistence persistence = new Persistence("person.json");

    /**
     * The last ID used
     */
    private int lastID = -1;

    /**
     * A list of persons
     */
    private ArrayList<Person> persons = new ArrayList<Person>();

    /**
     * Creates a new instance of the PersistencePerson class
     */
    private PersistencePerson() {
        // Once a new instance is made the data will be read and saved in memory
        this.read();
    }

    /**
     * Creating a person
     * @param firstName The person's firstname
     * @param lastName The person's lastname
     * @return
     */
    public Person createPerson (String firstName, String lastName) {
        lastID++;
        Person newPerson = new Person(lastID, firstName, lastName);
        persons.add(newPerson);

        // Save changes to file

        this.write(newPerson);

        return newPerson;
    }

    /**
     * Gets a person by ID
     * @param personID The ID of the producer
     * @return
     */
    public Person getPerson (int personID) {
        for (int i = 0; i < this.persons.size(); i++) {
            Person element = this.persons.get(i);

            if (element.getId() == personID) {
                return element;
            }
        }

        return null;
    }

    /**
     * Get a list of people with matching names
     * @param firstName Firstname to search for
     * @param lastName Lastname to search for
     * @return
     */
    public ArrayList<Person> getPersons (String firstName, String lastName) {
        ArrayList<Person> persons = new ArrayList<Person>();

        for (int i = 0; i < this.persons.size(); i++) {
            Person element = this.persons.get(i);

            if (element.getFirstName().equals(firstName) && element.getLastName().equals(lastName)) {
                persons.add(element);
            }
        }

        return persons;
    }

    /**
     * Reads file and parses JSONObject
     */
    private void read() {
        Connection connection = PersistenceDatabaseHelper.getConnection();

        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM person");
            ResultSet result = stmt.executeQuery();
            int rowcount = 0;
            ArrayList<Person> returnValue = new ArrayList<>();
            while (result.next()){
                returnValue.add(new Person(result.getInt(1), result.getString(2), result.getString(3)));
            }
            this.persons = returnValue;

            System.out.println(result);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Writes data saved in memory to file
     */
    private void write(Person person) {
        Connection connection = PersistenceDatabaseHelper.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO person VALUES(?,?,?)");
            stmt.setInt(1,  person.getId());
            stmt.setString(2,  person.getFirstName());
            stmt.setString(3,  person.getLastName());
            stmt.execute();
            System.out.println("You did it!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("You didn't do it");
        }
    }
}

