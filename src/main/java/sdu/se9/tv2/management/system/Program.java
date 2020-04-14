package sdu.se9.tv2.management.system;

public class Program {
    private String programName;
    private int internalID;
    private String confirmation;
    private boolean awaitingConfirmation;

    public Program(String programName, int internalID, String confirmation, boolean awaitingConfirmation) {
        this.programName = programName;
        this.internalID = internalID;
        this.confirmation = confirmation;
        this.awaitingConfirmation = awaitingConfirmation;
    }

    public String getProgramName() {
        return programName;
    }

    public int getInternalID() {
        return internalID;
    }

    public String getConfirmation() {
        return confirmation;
    }

    public boolean isAwaitingConfirmation() {
        return awaitingConfirmation;
    }
}
