package sdu.se9.tv2.management.system.persistence;

import org.json.simple.parser.ParseException;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import sdu.se9.tv2.management.system.domain.Producer;

/**
 * Implementation of the IPersistenceProducer interface
 */
public class PersistenceProducer implements IPersistenceProducer {

    private static PersistenceProducer instance = null;

    public static PersistenceProducer getInstance() {
        if (instance == null) {
            instance = new PersistenceProducer();
        }

        return instance;
    }

    /**
     * Instance of persistence class for file
     */
    private Persistence persistence = new Persistence("producer.json");

    /**
     * The last ID used
     */
    private int lastID = -1;

    /**
     * A list of producers
     */
    private ArrayList<Producer> producers = new ArrayList<Producer>();

    /**
     * Creates a new instance of the PersistenceProducer class
     */
    private PersistenceProducer() {
        // Once a new instance is made the data will be read and saved in memory
        this.read();
    }

    /**
     * Creates a new producer and saves it to file
     * @param producerName The name of the new producer
     * @return
     */
    public Producer createProducer (String producerName) {
        lastID++;
        Producer newProducer = new Producer(lastID, producerName);
        producers.add(newProducer);

        // Save changes to file

        this.write();

        return newProducer;
    }

    /**
     * Gets a producer by ID
     * @param producerID The ID of the producer
     * @return
     */
    public Producer getProducer (int producerID) {
        for (int i = 0; i < this.producers.size(); i++) {
            Producer element = this.producers.get(i);

            if (element.getID() == producerID) {
                return element;
            }
        }

        return null;
    }

    public Producer getProducer (String producerName) {
        for (int i = 0; i < this.producers.size(); i++) {
            Producer element = this.producers.get(i);

            if (element.getName().equals(producerName)) {
                return element;
            }
        }

        return null;
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
            // The file exists and has correct formatting, get lastID and list of producers

            // obj.get("lastID") returns Long
            this.lastID = Math.toIntExact((Long)obj.get("lastID"));

            JSONArray objList = (JSONArray)obj.get("list");

            ArrayList<Producer> parsedList = new ArrayList<Producer>();

            // Parse producer list
            Iterator<JSONObject> iterator = objList.iterator();
            while (iterator.hasNext()) {
                // Get element of the list array
                JSONObject element = iterator.next();
                // Parse the JSONObject using Producer.parseJSON
                parsedList.add(Producer.parseJSON(element));
            }

            // Set producer list
            producers = parsedList;
        }
    }

    /**
     * Writes data saved in memory to file
     */
    private void write() {
        // Create JSONObject to save
        JSONObject obj = new JSONObject();

        // JSONArray that contains the producers
        JSONArray list = new JSONArray();

        // Go through producer list and parse as JSON objects
        for (int i = 0; i < this.producers.size(); i++) {
            list.add(Producer.parseJSON(this.producers.get(i)));
        }

        // Add lastID to JSON object
        obj.put("lastID", lastID);
        // Add list to JSON object
        obj.put("list", list);

        // Overwrite the file with new JSON object
        this.persistence.write(obj);
    }
}
