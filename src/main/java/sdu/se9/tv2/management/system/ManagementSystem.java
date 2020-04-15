package sdu.se9.tv2.management.system;

public class ManagementSystem {
    private static ManagementSystem instance;

    public static ManagementSystem getSingleton() {
        if (instance == null) {
            instance = new ManagementSystem();
        }

        return instance;
    }

    private PersistenceProducer persistenceProducer;

    private PersistenceProgram persistenceProgram;

    private PersistencePerson persistencePerson;

    private PersistenceCredit persistenceCredit;

    ManagementSystem () {
        this.persistenceProducer = new PersistenceProducer();
        this.persistenceProgram = new PersistenceProgram();
        this.persistencePerson = new PersistencePerson();
        this.persistenceCredit = new PersistenceCredit();
    }

    public Producer createProducer (String producerName) {
        return persistenceProducer.createProducer(producerName);
    }

    public Producer getProducer (int producerID) {
        return persistenceProducer.getProducer(producerID);
    }

    public Credit createCredit (int programID, int personID, String roleName){
        // add logic here
        return null;
    }

    public Credit getCredit(int programID, int personID, String roleName) {
        // add logic here
        return null;
    }

    public Credit getCredits(int programID) {
        // add logic here
        return null;
    }

    public Program createProgram (int producerID, String programName, int internalID) {
        // TODO: Check if program already exists
        return this.persistenceProgram.createProgram(producerID, programName, internalID);
    }

    public Program getProgram(int programID) {
        return this.persistenceProgram.getProgram(programID);
    }

    public void setPendingApproval(int programID, boolean pendingApproval) {
        // add logic here
    }

    public void setApproved(int programID, boolean approved) {
        // add logic here
    }

    public Credit exportCredits(int programID, String fileFormat) {
        // add logic here
        return null;
    }
}
