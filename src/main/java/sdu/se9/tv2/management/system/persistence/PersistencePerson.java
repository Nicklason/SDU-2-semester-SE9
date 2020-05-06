package sdu.se9.tv2.management.system.persistence;
import org.json.simple.parser.ParseException;

import java.sql.*;
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

    private PersistencePerson() {}

    /**
     * Creating a person
     * @param firstName The person's firstname
     * @param lastName The person's lastname
     * @return
     */
    public Person createPerson (String firstName, String lastName) {
        Person newPerson;
        Connection connection = PersistenceDatabaseHelper.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO person(first_name, last_name) VALUES(?,?)", Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1,  firstName);
            stmt.setString(2,  lastName);
            stmt.execute();
            ResultSet rs = stmt.getGeneratedKeys();
            int i = 0;
            if (rs.next()) {
                i = rs.getInt(1);
            }
            System.out.println("You did it!");
            System.out.println(i);
            return new Person(i, firstName, lastName);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("You didn't do it");
            return null;
        }
    }

    /**
     * Gets a person by ID
     * @param personID The ID of the producer
     * @return
     */
    public Person getPerson (int personID) {
        Connection connection = PersistenceDatabaseHelper.getConnection();

        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Person WHERE personID =" + personID);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                // No match
                return null;
            }

            int id = rs.getInt("id");
            String firstName= rs.getString("first_name");
            String lastName = rs.getString("last_name");

            return new Person(id, firstName, lastName);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    /**
     * Get a list of people with matching names
     * @param firstName Firstname to search for
     * @param lastName Lastname to search for
     * @return
     */
    public ArrayList<Person> getPersons (String firstName, String lastName) {
        Connection connection = PersistenceDatabaseHelper.getConnection();

        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM person WHERE first_name ='" + firstName + "' AND last_name ='" + lastName+"'");
            ResultSet result = stmt.executeQuery();
            int rowcount = 0;
            ArrayList<Person> returnValue = new ArrayList<>();
            while (result.next()){
                returnValue.add(new Person(result.getInt(1), result.getString(2), result.getString(3)));
            }

            System.out.println(result);
            return returnValue;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }
}

