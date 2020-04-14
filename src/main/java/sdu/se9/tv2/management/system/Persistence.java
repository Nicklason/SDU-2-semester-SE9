package sdu.se9.tv2.management.system;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Persistence {
    private File file = new File("data.json");

    private int lastProducerID = -1;

    private int lastProgramID = -1;

    private ArrayList<Producer> producers = new ArrayList<Producer>();

    private ArrayList<Program> programs = new ArrayList<>();

    public Persistence() {
        try {
            JSONObject obj = this.read();

            if (obj != null) {
                // <editor-fold desc="Producer">
                JSONObject producerObj = (JSONObject)obj.get("producer");

                // producerObj.get("lastID") returns Long
                this.lastProducerID = Math.toIntExact((Long)producerObj.get("lastID"));

                JSONArray producerList = (JSONArray)producerObj.get("list");

                ArrayList<Producer> parsedProducerList = new ArrayList<Producer>();

                // Parse producer list
                Iterator<JSONObject> producerIterator = producerList.iterator();
                while (producerIterator.hasNext()) {
                    JSONObject element = producerIterator.next();
                    parsedProducerList.add(Producer.parseJSON(element));
                }

                producers = parsedProducerList;
                // </editor-fold>

                // <editor-fold desc="Program">
                JSONObject programObj = (JSONObject)obj.get("program");

                this.lastProgramID = Math.toIntExact((Long)programObj.get("lastID"));

                JSONArray programList = (JSONArray)programObj.get("list");

                ArrayList<Program> parsedProgramList = new ArrayList<>();

                Iterator<JSONObject> programIterator = programList.iterator();
                while (programIterator.hasNext()) {
                    JSONObject element = programIterator.next();
                    parsedProgramList.add(Program.parseJSON(element));
                }

                programs = parsedProgramList;
                // </editor-fold>
            }

        } catch (ParseException err) {
            err.printStackTrace();
        }
    }

    private JSONObject read () throws ParseException {
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(this.file))
        {
            //Read JSON file
            Object obj = parser.parse(reader);

            return (JSONObject) obj;
        } catch (FileNotFoundException e) {
            // e.printStackTrace();
        } catch (IOException e) {
            // e.printStackTrace();
        }

        // Return null if there was an error

        return null;
    }

    private void write () {
        System.out.println("Saving...");

        // Create JSONObject that will be saved
        JSONObject obj = new JSONObject();

        // <editor-fold desc="Producer">
        JSONObject producerObj = new JSONObject();

        JSONArray producerList = new JSONArray();

        for (int i = 0; i < this.producers.size(); i++) {
            producerList.add(Producer.parseJSON(this.producers.get(i)));
        }

        // Add producer info
        producerObj.put("lastID", this.lastProducerID);
        producerObj.put("list", producerList);

        obj.put("producer", producerObj);
        // </editor-fold>

        // <editor-fold desc="Program">
        JSONObject programObj = new JSONObject();

        JSONArray programList = new JSONArray();

        for (int i = 0; i < this.programs.size(); i++) {
            programList.add(Program.parseJSON(this.programs.get(i)));
        }

        // Add producer info
        programObj.put("lastID", this.lastProgramID);
        programObj.put("list", programList);

        obj.put("program", programObj);
        // </editor-fold>

        // Make file writer
        try (FileWriter file = new FileWriter(this.file)) {
            // Write to the file
            file.write(obj.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Producer createProducer (String producerName) {
        lastProducerID++;
        Producer newProducer = new Producer(lastProducerID, producerName);
        producers.add(newProducer);

        // Save changes to file

        this.write();

        return newProducer;
    }

    public boolean hasProducer (int producerID) {
        for (int i = 0; i < this.producers.size(); i++) {
            Producer element = this.producers.get(i);

            if (element.getID() == producerID) {
                return true;
            }
        }

        return false;
    }

    public Producer getProducer (int producerID) throws IllegalStateException {
        for (int i = 0; i < this.producers.size(); i++) {
            Producer element = this.producers.get(i);

            if (element.getID() == producerID) {
                return element;
            }
        }

        throw new IllegalStateException("Producer does not exist");
    }

    public Program createProgram (int producerID, String programName, int internalID) throws IllegalStateException {
        // If the producer does not exist then an error will be thrown
        Producer producer = this.getProducer(producerID);

        lastProgramID++;
        Program newProgram = new Program(lastProgramID, producer.getID(), programName, internalID, false, false);
        programs.add(newProgram);

        // Save changes to file

        this.write();

        return newProgram;
    }

    public boolean hasProgram (int programID) {
        for (int i = 0; i < this.programs.size(); i++) {
            Program element = this.programs.get(i);

            if (element.getID() == programID) {
                return true;
            }
        }

        return false;
    }

    public Program getProgram (int programID) throws IllegalStateException {
        for (int i = 0; i < this.programs.size(); i++) {
            Program element = this.programs.get(i);

            if (element.getID() == programID) {
                return element;
            }
        }

        throw new IllegalStateException("Program does not exist");
    }
}
