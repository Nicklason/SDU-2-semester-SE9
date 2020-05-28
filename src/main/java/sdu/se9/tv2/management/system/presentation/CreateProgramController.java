package sdu.se9.tv2.management.system.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import sdu.se9.tv2.management.system.domain.IManagementSystem;
import sdu.se9.tv2.management.system.domain.ManagementSystem;
import sdu.se9.tv2.management.system.domain.Program;
import sdu.se9.tv2.management.system.domain.accounts.ProducerAccount;

import java.sql.SQLException;

public class CreateProgramController {
    private IManagementSystem managementSystem = ManagementSystem.getInstance();

    @FXML
    private TextField programNameField;
    @FXML
    private TextField internalIDField;
    @FXML
    public void createProgram(ActionEvent event) {
        ProducerAccount producerAccount = (ProducerAccount) managementSystem.getAccount();

        String name = programNameField.getText();
        int internalID =Integer.parseInt(internalIDField.getText());
        int producerID = producerAccount.getProducerId();

        try {
            Program program = managementSystem.createProgram(producerID, name, internalID);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Tilføj program");
            alert.setHeaderText("Der skete en fejl ved tilføjelese af program: " + programNameField.getText());
            alert.show();
            return;
        }

        Alert alert = new Alert((Alert.AlertType.CONFIRMATION));
        alert.setTitle("Tilføj program");
        alert.setHeaderText("Program: " + programNameField.getText() + " blev oprettet");
        alert.show();
    }
}
