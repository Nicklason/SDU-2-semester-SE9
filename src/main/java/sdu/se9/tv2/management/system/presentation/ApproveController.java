package sdu.se9.tv2.management.system.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import sdu.se9.tv2.management.system.domain.Program;
import sdu.se9.tv2.management.system.persistence.PersistenceProgram;

import java.io.IOException;

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

        Program program = PersistenceProgram.getInstance().getProgram(programName);

        if(program != null){
            if (program.isApproved()) {
                userResponse.setText("Krediteringen for " + programName + " er allerede blevet godkendt");
                return;
            }
            if(!program.isAwaitingApproval()) {
                userResponse.setText("Krediteringen for " + programName + " afventer ikke godkendelse");
                return;
            }
            PersistenceProgram.getInstance().setApproved(program.getID(), true);
            PersistenceProgram.getInstance().setAwaitingApproval(program.getID(), false);
            userResponse.setText("Krediteringen for: " + programName + " er blevet godkendt");
            return;
        }
        userResponse.setText("Program med navn: " + programName + " ikke fundet");
    }

    @FXML
    public void requestApproval(ActionEvent event) throws IOException {

        userResponse.setText("");

        String programName = this.programNameText.getText();

        if (programName.isBlank()) {
            userResponse.setText("Feltet kan ikke være blankt");
            return;
        }

        Program program = PersistenceProgram.getInstance().getProgram(programName);

        if(program != null){
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
            return;
        }
        userResponse.setText("Program med navn: " + programName + " ikke fundet");
    }
}
