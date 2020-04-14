package sdu.se9.tv2.management.system;

import org.json.simple.parser.ParseException;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class PersistenceProgram implements IPersistenceProgram {
    private Persistence persistence = new Persistence("program.json");

    private int lastID = -1;

    private ArrayList<Program> programs = new ArrayList<Program>();

    PersistenceProgram () {
        this.read();
    }

    public Program createProgram (int producerID, String programName, int internalID) {
        lastID++;
        Program newProgram = new Program(lastID, producerID, programName, internalID, false, false);
        programs.add(newProgram);

        this.write();

        return newProgram;
    }

    public Program getProgram (int programID) {
        for (int i = 0; i < this.programs.size(); i++) {
            Program element = this.programs.get(i);

            if (element.getID() == programID) {
                return element;
            }
        }

        return null;
    }

    public void setApproved (int programID, boolean approved) {
        for (int i = 0; i < this.programs.size(); i++) {
            Program element = this.programs.get(i);

            if (element.getID() == programID) {
                element.setApproved(approved);
                return;
            }
        }
    }

    public void setAwaitingApproval (int programID, boolean awaitingApproval) {
        for (int i = 0; i < this.programs.size(); i++) {
            Program element = this.programs.get(i);

            if (element.getID() == programID) {
                element.setAwaitingApproval(awaitingApproval);
                return;
            }
        }
    }

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
