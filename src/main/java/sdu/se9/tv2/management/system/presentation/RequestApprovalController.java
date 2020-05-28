package sdu.se9.tv2.management.system.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import sdu.se9.tv2.management.system.domain.IManagementSystem;
import sdu.se9.tv2.management.system.domain.ManagementSystem;
import sdu.se9.tv2.management.system.domain.Program;
import sdu.se9.tv2.management.system.domain.accounts.ProducerAccount;

import java.io.IOException;
import java.sql.SQLException;

public class RequestApprovalController {

    private IManagementSystem managementSystem = ManagementSystem.getInstance();

    @FXML
    private TextField programNameText;

    @FXML
    private Text userResponse;

    @FXML
    public void requestApproval(ActionEvent event) throws IOException {
        userResponse.setText("");

        String programName = this.programNameText.getText();

        if (programName.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Anmod om krediteringer");
            alert.setHeaderText("Feltet kan ikke være blankt");
            alert.show();
            return;
        }

        Program program = null;
        try {
            program = this.managementSystem.getProgram(programName);
        } catch (SQLException throwables) {
            throwables.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Anmod om krediteringer");
            alert.setHeaderText("Der skete en fejl");
            alert.show();
            return;
        }

        // TODO: Add getProgramByProducer method to IPersistenceProgram, that way the check for if the producer owns the program is not needed all the time
        ManagementSystem system = ManagementSystem.getInstance();
        ProducerAccount producer = (ProducerAccount)system.getAccount();

        if(program == null || producer.getProducerId() != program.getProducerID()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Anmod om krediteringer");
            alert.setHeaderText("Program med navn: " + programName + " ikke fundet");
            alert.show();
            return;
        }

        if (program.isApproved()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Anmod om krediteringer");
            alert.setHeaderText("Krediteringen for " + programName + " er allerede blevet godkendt");
            alert.show();
            return;
        }

        if (program.isPendingApproval()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Anmod om krediteringer");
            alert.setHeaderText("Krediteringen for " + programName + " afventer allerede godkendelse");
            alert.show();
            return;
        }

        try {
            this.managementSystem.setPendingApproval(program.getID(), true);
        } catch (SQLException throwables) {
            throwables.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Anmod om krediteringer");
            alert.setHeaderText("Der skete en fejl");
            alert.show();
            return;
        }

        Alert alert = new Alert((Alert.AlertType.CONFIRMATION));
        alert.setTitle("Godkend kreditering");
        alert.setHeaderText("Krediteringen for " + programName + " afventer nu godkendelse");
        alert.show();
    }
}
