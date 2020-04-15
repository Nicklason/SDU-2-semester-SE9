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

    public Credit addCredits (int programID, int personID, String roleName){
        // add logic here

        return null;
    }

    public Producer findProducer(Producer producer) {
        // add logic here
        return null;
    }

    public Program findCredits(Program program) {
        // add logic here
        return null;
    }

    public Credit getCredit(int programID, int personID, String roleName) {
        // add logic here
        return null;
    }

    public void setPendingApproval(int programID, boolean pendingApproval) {
        // add logic here
    }

    public void setApproved(int programID, boolean approved) {
        // add logic here

    }

    public Credit getCredits(int programID) {
        // add logic here
        return null;
    }

    public Credit exportCredits(int programID, String fileFormat) {
        // add logic here
        return null;
    }

    public Program getProgram(int programID) {
        // add logic here
        return null;
    }
}
