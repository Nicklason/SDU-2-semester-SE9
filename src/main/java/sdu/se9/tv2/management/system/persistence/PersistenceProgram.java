package sdu.se9.tv2.management.system.persistence;

import org.json.simple.parser.ParseException;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import sdu.se9.tv2.management.system.domain.Program;

/**
 * Implementation of the IPersistenceProgram interface
 */
public class PersistenceProgram implements IPersistenceProgram {

    private static PersistenceProgram instance = null;

    public static PersistenceProgram getInstance() {
        if (instance == null) {
            instance = new PersistenceProgram();
        }

        return instance;
    }

    /**
     * Instance of persistence class for file
     */
    private Persistence persistence = new Persistence("program.json");

    /**
     * The last ID used
     */
    private int lastID = -1;

    /**
     * A list of programs
     */
    private ArrayList<Program> programs = new ArrayList<Program>();

    /**
     * Creates a new instance of the PersistenceProducer class
     */
    private PersistenceProgram() {
        this.read();
    }

    /**
     * Creates a new program and saves it to file
     * @param producerID The ID of the producer who made the program
     * @param programName The name of the program
     * @param internalID TV2's internal ID for the program
     * @return
     */
    public Program createProgram (int producerID, String programName, int internalID) {
        lastID++;
        Program newProgram = new Program(lastID, producerID, programName, internalID, false, false);
        programs.add(newProgram);

        this.write();

        return newProgram;
    }

    /**
     * Gets a program by ID
     * @param programID The ID of the program
     * @return
     */
    public Program getProgram (int programID) {
        for (int i = 0; i < this.programs.size(); i++) {
            Program element = this.programs.get(i);

            if (element.getID() == programID) {
                return element;
            }
        }

        return null;
    }

    /**
     * Gets a program by name
     * @param programName The name of the program
     * @return
     */
    public Program getProgram (String programName) {
        for (int i = 0; i < this.programs.size(); i++) {
            Program element = this.programs.get(i);

            if (element.getName().equals(programName)) {
                return element;
            }
        }

        return null;
    }

    /**
     * Set program credits approved status and saves to file
     * @param programID The ID of the program
     * @param approved `true` for approved, `false` for not
     */
    public void setApproved (int programID, boolean approved) {
        // Loop through all programs
        for (int i = 0; i < this.programs.size(); i++) {
            Program element = this.programs.get(i);

            // Check if the program is the one we are looking for by matching ids
            if (element.getID() == programID) {
                // Set it as approved
                element.setApproved(approved);
                // Stop the loop
                break;
            }
        }

        // Save changes to file
        this.write();
    }

    /**
     * Set a program awaiting approval status and saves to file
     * @param programID The ID of the program
     * @param awaitingApproval The approval status, `true` for pending approval and `false` for not
     */
    public void setAwaitingApproval (int programID, boolean awaitingApproval) {
        // Loop through programs and look for program matching programID
        for (int i = 0; i < this.programs.size(); i++) {
            Program element = this.programs.get(i);

            if (element.getID() == programID) {
                // Set awaiting approval
                element.setAwaitingApproval(awaitingApproval);
                return;
            }
        }

        this.write();
    }

    /**
     * Reads file and parses JSONObject
     */
    private void read() {
        JSONObject obj = null;

        try {
            obj = this.persistence.read();
        } catch (ParseException err) {
            err.printStackTrace();
        }

        if (obj != null) {
            this.lastID = Math.toIntExact((Long)obj.get("lastID"));

            JSONArray objList = (JSONArray)obj.get("list");

            ArrayList<Program> parsedList = new ArrayList<Program>();

            Iterator<JSONObject> iterator = objList.iterator();
            while (iterator.hasNext()) {
                JSONObject element = iterator.next();
                parsedList.add(Program.parseJSON(element));
            }

            programs = parsedList;
        }
    }

    /**
     * Writes data saved in memory to file
     */
    private void write() {
        JSONObject obj = new JSONObject();

        JSONArray list = new JSONArray();

        for (int i = 0; i < this.programs.size(); i++) {
            list.add(Program.parseJSON(this.programs.get(i)));
        }

        obj.put("lastID", lastID);
        obj.put("list", list);

        this.persistence.write(obj);
    }
}
