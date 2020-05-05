package sdu.se9.tv2.management.system.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import sdu.se9.tv2.management.system.domain.ManagementSystem;
import sdu.se9.tv2.management.system.domain.Program;
import sdu.se9.tv2.management.system.domain.accounts.Account;
import sdu.se9.tv2.management.system.domain.accounts.ProducerAccount;
import sdu.se9.tv2.management.system.persistence.PersistenceProgram;


public class CreateProgramController {
    @FXML
    private TextField programNameField;
    @FXML
    private TextField internalIDField;



    public void createProgram(ActionEvent event) {
        ProducerAccount producerAccount = (ProducerAccount) ManagementSystem.getInstance().getAccount();

        String name = programNameField.getText();
        int internalID =Integer.parseInt(internalIDField.getText());
        int producerID = producerAccount.getProducerId();
        Program program = PersistenceProgram.getInstance().createProgram(producerID, name, internalID);

        // TODO: Make alert confirming that the program was made
    }
}
