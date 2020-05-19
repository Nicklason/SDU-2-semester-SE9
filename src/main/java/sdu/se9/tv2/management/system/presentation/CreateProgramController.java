package sdu.se9.tv2.management.system.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
            return;
        }

        // TODO: Make alert confirming that the program was made
    }
}
