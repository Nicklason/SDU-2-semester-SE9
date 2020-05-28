package sdu.se9.tv2.management.system.presentation;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import sdu.se9.tv2.management.system.domain.ManagementSystem;
import sdu.se9.tv2.management.system.domain.Program;
import sdu.se9.tv2.management.system.domain.IManagementSystem;

import java.io.IOException;
import java.sql.SQLException;

public class ApproveController {

    private IManagementSystem managementSystem = ManagementSystem.getInstance();

    @FXML
    private TextField programNameText;

    @FXML
    private Text userResponse;

    @FXML
    public void approve(ActionEvent event) throws IOException {

        userResponse.setText("");

        String programName = this.programNameText.getText();

        if (programName.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Godkend kreditering");
            alert.setHeaderText("Feltet kan ikke være blankt");
            alert.show();
            return;
        }

        Program program = null;

        try {
            program = managementSystem.getProgram(programName);
        } catch (SQLException err) {
            err.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Godkend kreditering");
            alert.setHeaderText("Der skete en fejl");
            alert.show();
            return;
        }

        if(program == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Godkend kreditering");
            alert.setHeaderText("Program med navn: " + programName + " ikke fundet");
            alert.show();
            return;
        }
        if (program.isApproved()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Godkend kreditering");
            alert.setHeaderText("Krediteringen for " + programName + " er allerede blevet godkendt");
            alert.show();
            return;
        }
        if(!program.isPendingApproval()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Godkend kreditering");
            alert.setHeaderText("Krediteringen for " + programName + " afventer ikke godkendelse");
            alert.show();
            return;
        }

        try {
            // Setting awaiting approval to false is not needed because of a trigger
            managementSystem.setApproved(program.getID(), true);
        } catch (SQLException err) {
            err.printStackTrace();
            err.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Godkend kreditering");
            alert.setHeaderText("Det skete en fejl");
            alert.show();
            return;
        }

        Alert alert = new Alert((Alert.AlertType.CONFIRMATION));
        alert.setTitle("Godkend kreditering");
        alert.setHeaderText("Krediteringen for: " + programName + " er blevet godkendt");
        alert.show();
    }
}
