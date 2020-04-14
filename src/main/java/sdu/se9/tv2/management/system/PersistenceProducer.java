package sdu.se9.tv2.management.system;

import org.json.simple.parser.ParseException;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class PersistenceProducer implements IPersistenceProducer {
    private Persistence persistence = new Persistence("producer.json");

    private int lastID = -1;

    private ArrayList<Producer> producers = new ArrayList<Producer>();

    PersistenceProducer () {
        this.read();
    }

    public Producer createProducer (String producerName) {
        lastID++;
        Producer newProducer = new Producer(lastID, producerName);
        producers.add(newProducer);

        // Save changes to file

        this.write();

        return newProducer;
    }

    public Producer getProducer (int producerID) {
        for (int i = 0; i < this.producers.size(); i++) {
            Producer element = this.producers.get(i);

            if (element.getID() == producerID) {
                return element;
            }
        }

        return null;
    }

    private void read() {
        JSONObject obj = null;

        try {
            obj = this.persistence.read();
        } catch (ParseException err) {
            err.printStackTrace();
        }

        if (obj != null) {
            // obj.get("lastID") returns Long
            this.lastID = Math.toIntExact((Long)obj.get("lastID"));

            JSONArray objList = (JSONArray)obj.get("list");

            ArrayList<Producer> parsedList = new ArrayList<Producer>();

            // Parse producer list
            Iterator<JSONObject> iterator = objList.iterator();
            while (iterator.hasNext()) {
                JSONObject element = iterator.next();
                parsedList.add(Producer.parseJSON(element));
            }

            producers = parsedList;
        }
    }

    private void write() {
        JSONObject obj = new JSONObject();

        JSONArray list = new JSONArray();

        for (int i = 0; i < this.producers.size(); i++) {
            list.add(Producer.parseJSON(this.producers.get(i)));
        }

        obj.put("lastID", lastID);
        obj.put("list", list);

        this.persistence.write(obj);
    }
}
