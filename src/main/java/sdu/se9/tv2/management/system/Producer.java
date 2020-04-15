package sdu.se9.tv2.management.system;

import org.json.simple.JSONObject;

public class Producer {
    private int id;
    private String name;

    public Producer(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() { return id; }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Producer{" +
                "id='" + id + "'" +
                ", name='" + name + "'" +
                "}";
    }

    public static Producer parseJSON(JSONObject producer) {
        int id = Math.toIntExact((Long)producer.get("id"));
        String name = (String)producer.get("name");
        return new Producer(id, name);
    }

    public static JSONObject parseJSON(Producer producer) {
        // Create new json object
        JSONObject obj = new JSONObject();

        // Add properties to json object
        obj.put("id", producer.getId());
        obj.put("name", producer.getName());

        // Return object
        return obj;
    }
}
