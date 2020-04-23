package sdu.se9.tv2.management.system.persistence;
import org.json.simple.parser.ParseException;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
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

        this.write();

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
        JSONObject obj = null;

        // Try and read the file
        try {
            obj = this.persistence.read();
        } catch (ParseException err) {
            err.printStackTrace();
        }

        if (obj != null) {
            // The file exists and has correct formatting, get lastID and list of person

            // obj.get("lastID") returns Long
            this.lastID = Math.toIntExact((Long)obj.get("lastID"));

            JSONArray objList = (JSONArray)obj.get("list");

            ArrayList<Person> parsedList = new ArrayList<Person>();

            // Parse person list
            Iterator<JSONObject> iterator = objList.iterator();
            while (iterator.hasNext()) {
                // Get element of the list array
                JSONObject element = iterator.next();
                // Parse the JSONObject using Producer.parseJSON
                parsedList.add(Person.parseJSON(element));
            }

            // Set person list
            persons = parsedList;
        }
    }

    /**
     * Writes data saved in memory to file
     */
    private void write() {
        // Create JSONObject to save
        JSONObject obj = new JSONObject();

        // JSONArray that contains the person
        JSONArray list = new JSONArray();

        // Go through person list and parse as JSON objects
        for (int i = 0; i < this.persons.size(); i++) {
            list.add(Person.parseJSON(this.persons.get(i)));
        }

        // Add lastID to JSON object
        obj.put("lastID", lastID);
        // Add list to JSON object
        obj.put("list", list);

        // Overwrite the file with new JSON object
        this.persistence.write(obj);
    }
}

