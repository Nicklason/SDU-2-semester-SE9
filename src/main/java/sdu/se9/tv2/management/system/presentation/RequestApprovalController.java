package sdu.se9.tv2.management.system.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import sdu.se9.tv2.management.system.domain.Program;
import sdu.se9.tv2.management.system.persistence.PersistenceProgram;

import java.io.IOException;

public class RequestApprovalController {

    @FXML
    private TextField programNameText;

    @FXML
    private Text userResponse;

    @FXML
    public void requestApproval(ActionEvent event) throws IOException {

        userResponse.setText("");

        String programName = this.programNameText.getText();

        if (programName.isBlank()) {
            userResponse.setText("Feltet kan ikke være blankt");
            return;
        }

        Program program = PersistenceProgram.getInstance().getProgram(programName);

        if(program == null){
            userResponse.setText("Program med navn: " + programName + " ikke fundet");
            return;
        }
        if (program.isApproved()) {
            userResponse.setText("Krediteringen for " + programName + " er allerede blevet godkendt");
            return;
        }
        if(program.isAwaitingApproval()) {
            userResponse.setText("Krediteringen for " + programName + " afventer allerede godkendelse");
            return;
        }
        PersistenceProgram.getInstance().setAwaitingApproval(program.getID(), true);
        userResponse.setText("Krediteringen for " + programName + " afventer nu godkendelse");
    }
}