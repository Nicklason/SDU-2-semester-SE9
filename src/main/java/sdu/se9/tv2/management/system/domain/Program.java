package sdu.se9.tv2.management.system.domain;

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
}
