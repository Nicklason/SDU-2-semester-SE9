package sdu.se9.tv2.management.system;

public class Program {
    private String programName;
    private int internalID;
    private boolean awaitingConfirmation;

    public Program(String programName, int internalID, boolean awaitingConfirmation) {
        this.programName = programName;
        this.internalID = internalID;
        this.awaitingConfirmation = awaitingConfirmation;
    }

    public String getProgramName() {
        return programName;
    }

    public int getInternalID() {
        return internalID;
    }

    public boolean isAwaitingConfirmation() {
        return awaitingConfirmation;
    }
}
