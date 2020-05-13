package sdu.se9.tv2.management.system.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import sdu.se9.tv2.management.system.domain.Program;
import sdu.se9.tv2.management.system.persistence.PersistenceProgram;

import java.io.IOException;
import java.sql.SQLException;

public class ApproveController {

    @FXML
    private TextField programNameText;

    @FXML
    private Text userResponse;

    @FXML
    public void approve(ActionEvent event) throws IOException {

        userResponse.setText("");

        String programName = this.programNameText.getText();

        if (programName.isBlank()) {
            userResponse.setText("Feltet kan ikke være blankt");
            return;
        }

        Program program = null;

        try {
            program = PersistenceProgram.getInstance().getProgram(programName);
        } catch (SQLException err) {
            err.printStackTrace();
            // TODO: Make alert saying something went wrong
            return;
        }

        if(program == null){
            userResponse.setText("Program med navn: " + programName + " ikke fundet");
            return;
        }
        if (program.isApproved()) {
            userResponse.setText("Krediteringen for " + programName + " er allerede blevet godkendt");
            return;
        }
        if(!program.isAwaitingApproval()) {
            userResponse.setText("Krediteringen for " + programName + " afventer ikke godkendelse");
            return;
        }

        try {
            // Setting awaiting approval to false is not needed because of a trigger
            PersistenceProgram.getInstance().setApproved(program.getID(), true);
        } catch (SQLException err) {
            err.printStackTrace();
            // TODO: Make alert saying something went wrong
            return;
        }

        userResponse.setText("Krediteringen for: " + programName + " er blevet godkendt");
    }
}
