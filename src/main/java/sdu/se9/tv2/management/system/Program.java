package sdu.se9.tv2.management.system;

import org.json.simple.JSONObject;

public class Program {
    private int id;
    private int producerID;
    private String name;
    private int internalID;
    private boolean approved;
    private boolean awaitingApproval;

    public Program(int id, int producerID, String name, int internalID, boolean approved, boolean awaitingApproval) {
        this.id = id;
        this.producerID = producerID;
        this.name = name;
        this.internalID = internalID;
        this.approved = approved;
        this.awaitingApproval = awaitingApproval;
    }

    public int getID() { return id; }

    public int getProducerID() { return producerID; }

    public String getName() {
        return name;
    }

    public int getInternalID() {
        return internalID;
    }

    public boolean isApproved() {
        return approved;
    }

    public boolean isAwaitingApproval() {
        return awaitingApproval;
    }

    @Override
    public String toString() {
        return "Program{" +
                "id='" + id + "'" +
                ", producerID='" + producerID + "'" +
                ", name='" + name + "'" +
                ", internalID='" + internalID + "'" +
                ", approved='" + approved + "'" +
                ", awaitingApproval='" + awaitingApproval + "'" +
                "}";
    }

    public static Program parseJSON (JSONObject program) {
        int id = Math.toIntExact((Long)program.get("id"));
        int producerID = Math.toIntExact((Long)program.get("producerID"));
        String name = (String)program.get("name");
        int internalID = Math.toIntExact((Long)program.get("internalID"));
        boolean approved = (boolean)program.get("approved");
        boolean awaitingApproval = (boolean)program.get("awaitingApproval");

        return new Program(id, producerID, name, internalID, approved, awaitingApproval);
    }

    public static JSONObject parseJSON(Program program) {
        JSONObject obj = new JSONObject();

        obj.put("id", program.getID());
        obj.put("producerID", program.getProducerID());
        obj.put("name", program.getName());
        obj.put("internalID", program.getInternalID());
        obj.put("approved", program.isApproved());
        obj.put("awaitingApproval", program.isAwaitingApproval());

        return obj;
    }
}
