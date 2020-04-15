package sdu.se9.tv2.management.system;

import java.util.ArrayList;

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
        // add logic here
        return null;
    }

    public Producer getProducer (int producerID) {
        // add logic here
        return null;
    }

    public Credit createCredit (int programID, int personID, String roleName){
        Credit newCredit = persistenceCredit.createCredit( programID, personID, roleName);
        return newCredit;
    }

    public Credit getCredit(int programID, int personID, String roleName) {
        ArrayList<Credit> creditList = persistenceCredit.getCredits(programID);

        for (int i = 0; i < creditList.size(); i++) {
            Credit credit = creditList.get(i);

            if (credit.getProgramID() == programID && credit.getPersonID() == personID && credit.getRole() == roleName) {
                return credit;
            }
        }

        return null;
    }

    public ArrayList<Credit> getCredits(int programID) {
        return persistenceCredit.getCredits(programID);
    }

    public Program createProgram (int producerID, String programName, int internalID) {
        // add logic here
        return null;
    }

    public Program getProgram(int programID) {
        // add logic here
        return null;
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
