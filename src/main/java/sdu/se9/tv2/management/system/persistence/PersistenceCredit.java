package sdu.se9.tv2.management.system.persistence;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import sdu.se9.tv2.management.system.domain.Credit;
import sdu.se9.tv2.management.system.exceptions.DuplicateRoleNameException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PersistenceCredit implements IPersistenceCredit {

    private static PersistenceCredit instance = null;

    public static PersistenceCredit getInstance() {
        if (instance == null) {
            instance = new PersistenceCredit();
        }

        return instance;
    }

    private Persistence persistence = new Persistence("credit.json");

    private int lastID = -1;

    private ArrayList<Credit> credits = new ArrayList<Credit>();

    private PersistenceCredit() {
        this.read();
    }

    @Override
    public Credit createCredit(int programID, int personID, String roleName) throws DuplicateRoleNameException {
        if (this.getCredit(programID, roleName) != null) {
            throw new DuplicateRoleNameException("Role already exists");
        }

        lastID++;
        Credit newCredit = new Credit(lastID, programID, personID, roleName);
        credits.add(newCredit);

        // Save changes to file

        this.write(newCredit);

        return newCredit;
    }

    @Override
    public ArrayList<Credit> getCredits(int programID) {
        ArrayList<Credit> result = new ArrayList<Credit>();

        for (int i = 0; i < this.credits.size(); i++) {
            Credit element = this.credits.get(i);

            if (element.getProgramID() == programID) {
                result.add(element);
            }
        }

        return result;
    }

    /**
     * Get credits a person has for a program
     * @param programID Program id of the program
     * @param personID Person id of the person
     * @return
     */
    public ArrayList<Credit> getCredits(int programID, int personID) {
        ArrayList<Credit> result = new ArrayList<Credit>();

        for (int i = 0; i < this.credits.size(); i++) {
            Credit element = this.credits.get(i);

            if (element.getProgramID() == programID && element.getPersonID() == personID) {
                result.add(element);
            }
        }

        return result;
    }

    public Credit getCredit (int programID, String roleName) {
        for (int i = 0; i < this.credits.size(); i++) {
            Credit element = this.credits.get(i);

            if (element.getProgramID() == programID && element.getRole().equals(roleName)) {
                return element;
            }
        }

        return null;
    }

    public Credit getCredit (int programID, int personID, String roleName) {
        for (int i = 0; i < this.credits.size(); i++) {
            Credit element = this.credits.get(i);

            if (element.getProgramID() == programID && element.getPersonID() == personID && element.getRole().equals(roleName)) {
                return element;
            }
        }

        return null;
    }

    public ArrayList<Credit> getCreditsByPerson (int personID) {
        return this.getCreditsByPerson(personID, Integer.MAX_VALUE);
    }

    public ArrayList<Credit> getCreditsByPerson (int personID, int maxCount) {
        ArrayList<Credit> result = new ArrayList<Credit>();

        for (int i = this.credits.size() - 1; i >= 0; i--) {
            Credit element = this.credits.get(i);

            if (element.getPersonID() == personID) {
                result.add(element);
            }

            if (result.size() >= maxCount) {
                break;
            }
        }

        return result;
    }

    private void read() {
        Connection connection = PersistenceDatabaseHelper.getConnection();

        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM credit");
            ResultSet result = stmt.executeQuery();
            int rowcount = 0;
            ArrayList<Credit> returnValue = new ArrayList<>();
            while (result.next()){
                returnValue.add(new Credit(result.getInt(1), result.getInt(2), result.getInt(3), result.getString(4)));
            }
            this.credits = returnValue;

            System.out.println(result);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        /*
        JSONObject obj = null;

        // Try and read the file
        try {
            obj = this.persistence.read();
        } catch (ParseException err) {
            err.printStackTrace();
        }

        if (obj != null) {
            // The file exists and has correct formatting, get lastID and list of producers

            // obj.get("lastID") returns Long
            this.lastID = Math.toIntExact((Long)obj.get("lastID"));

            JSONArray objList = (JSONArray)obj.get("list");

            ArrayList<Credit> parsedList = new ArrayList<Credit>();

            // Parse producer list
            Iterator<JSONObject> iterator = objList.iterator();
            while (iterator.hasNext()) {
                // Get element of the list array
                JSONObject element = iterator.next();
                // Parse the JSONObject using Producer.parseJSON
                parsedList.add(Credit.parseJSON(element));
            }

            // Set producer list
            credits = parsedList;
        }

         */
    }

    private void write(Credit credit) {
        Connection connection = PersistenceDatabaseHelper.getConnection();
        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO credit VALUES(?,?,?,?)");
            stmt.setInt(1,  credit.getID());
            stmt.setInt(2,  credit.getProgramID());
            stmt.setInt(3,  credit.getPersonID());
            stmt.setString(4,  credit.getRole());
            stmt.execute();
            System.out.println("You did it!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("You didn't do it");
        }


        /*
        // Create JSONObject to save
        JSONObject obj = new JSONObject();

        // JSONArray that contains the producers
        JSONArray list = new JSONArray();

        // Go through credit list and parse as JSON objects
        for (int i = 0; i < this.credits.size(); i++) {
            list.add(Credit.parseJSON(this.credits.get(i)));
        }

        // Add lastID to JSON object
        obj.put("lastID", lastID);
        // Add list to JSON object
        obj.put("list", list);

        // Overwrite the file with new JSON object
        this.persistence.write(obj); */
    }
}

