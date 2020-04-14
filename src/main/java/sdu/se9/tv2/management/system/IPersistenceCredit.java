package sdu.se9.tv2.management.system;

import java.util.ArrayList;

public interface IPersistenceCredit {
    Credit createCredit (int programID, int personID, String roleName);
    ArrayList<Credit> getCredits (int programID);
}
