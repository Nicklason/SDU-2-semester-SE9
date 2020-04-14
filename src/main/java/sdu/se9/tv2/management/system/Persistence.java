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

    private ArrayList<Producer> producers = new ArrayList<Producer>();

    public Persistence() {
        try {
            JSONObject obj = this.read();

            if (obj != null) {
                JSONObject producerObj = (JSONObject)obj.get("producer");

                // jsonObject.get("lastProducerID") returns Long
                this.lastProducerID = Math.toIntExact((Long)producerObj.get("lastID"));

                JSONArray producerList = (JSONArray)producerObj.get("list");

                ArrayList<Producer> parsedProducerList = new ArrayList<Producer>();

                // Parse producer list
                Iterator<JSONObject> iterator = producerList.iterator();
                while (iterator.hasNext()) {
                    JSONObject element = iterator.next();
                    parsedProducerList.add(Persistence.parseProducer(element));
                }

                producers = parsedProducerList;
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

        JSONArray producerList = new JSONArray();

        for (int i = 0; i < this.producers.size(); i++) {
            producerList.add(Persistence.parseProducer(this.producers.get(i)));
        }

        // Create JSONObject that will be saved
        JSONObject obj = new JSONObject();

        JSONObject producerObj = new JSONObject();

        // Add producer info
        producerObj.put("lastID", this.lastProducerID);
        producerObj.put("list", producerList);

        obj.put("producer", producerObj);

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
            Producer producer = this.producers.get(i);

            if (producer.getID() == producerID) {
                return true;
            }
        }

        return false;
    }

    public Producer getProducer (int producerID) throws IllegalStateException {
        for (int i = 0; i < this.producers.size(); i++) {
            Producer producer = this.producers.get(i);

            if (producer.getID() == producerID) {
                return producer;
            }
        }

        throw new IllegalStateException("Producer does not exist");
    }

    private static Producer parseProducer(JSONObject producer) {
        int id = Math.toIntExact((Long)producer.get("id"));
        String name = (String)producer.get("name");
        return new Producer(id, name);
    }

    private static JSONObject parseProducer(Producer producer) {
        // Create new json object
        JSONObject producerObj = new JSONObject();

        // Add properties to json object
        producerObj.put("id", producer.getID());
        producerObj.put("name", producer.getName());

        // Return object
        return producerObj;
    }
}
