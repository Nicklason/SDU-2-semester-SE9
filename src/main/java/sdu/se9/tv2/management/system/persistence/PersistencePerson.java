package sdu.se9.tv2.management.system.persistence;

import java.sql.*;
import java.util.ArrayList;
import sdu.se9.tv2.management.system.domain.Person;

/**
 * Implementation of the IPersistencePerson interface
 */
public class PersistencePerson implements IPersistencePerson {
    public PersistencePerson() {}

    /**
     * Creating a person
     * @param firstName The person's firstname
     * @param lastName The person's lastname
     * @return
     */
    public Person createPerson (String firstName, String lastName) throws SQLException {
        Connection connection = PersistenceDatabaseHelper.getConnection();
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO person(firstName, lastName) VALUES(?,?) RETURNING id;");
        stmt.setString(1, firstName);
        stmt.setString(2, lastName);

        ResultSet rs = stmt.executeQuery();
        rs.next();

        int id = rs.getInt(1);

        return new Person(id, firstName, lastName);
    }

    /**
     * Gets a person by ID
     * @param personID The ID of the producer
     * @return
     */
    public Person getPerson (int personID) throws SQLException {
        Connection connection = PersistenceDatabaseHelper.getConnection();

        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Person WHERE id = ? LIMIT 1;");
        stmt.setInt(1, personID);

        ResultSet rs = stmt.executeQuery();
        if (!rs.next()) {
            // No match
            return null;
        }

        int id = rs.getInt("id");
        String firstName= rs.getString("firstName");
        String lastName = rs.getString("lastName");

        return new Person(id, firstName, lastName);
    }

    /**
     * Get a list of people with matching names
     * @param firstName Firstname to search for
     * @param lastName Lastname to search for
     * @return
     */
    public ArrayList<Person> getPersons (String firstName, String lastName) throws SQLException {
        Connection connection = PersistenceDatabaseHelper.getConnection();

        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM person WHERE firstName = ? AND lastName = ?;");
        stmt.setString(1, firstName);
        stmt.setString(2, lastName);

        ResultSet result = stmt.executeQuery();

        ArrayList<Person> returnValue = new ArrayList<>();
        while (result.next()){
            returnValue.add(new Person(result.getInt("id"), result.getString("firstName"), result.getString("lastName")));
        }

        return returnValue;
    }
}

