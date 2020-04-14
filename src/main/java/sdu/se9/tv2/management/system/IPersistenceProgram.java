package sdu.se9.tv2.management.system;

public interface IPersistenceProgram {
    Program createProgram (int producerID, String programName, int internalID);
    Program getProgram (int programID);
    void setAwaitingApproval (int programID, boolean awaitingApproval);
    void setApproved (int programID, boolean approved);
}
