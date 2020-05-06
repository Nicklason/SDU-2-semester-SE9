package sdu.se9.tv2.management.system.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import sdu.se9.tv2.management.system.domain.ManagementSystem;
import sdu.se9.tv2.management.system.domain.Producer;
import sdu.se9.tv2.management.system.domain.accounts.Account;
import sdu.se9.tv2.management.system.domain.accounts.ProducerAccount;
import sdu.se9.tv2.management.system.exceptions.UsernameAlreadyExistsException;
import sdu.se9.tv2.management.system.persistence.IPersistenceProducer;
import sdu.se9.tv2.management.system.persistence.PersistenceAccount;
import sdu.se9.tv2.management.system.persistence.PersistenceProducer;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProducerController {

    @FXML
    public TextField textfieldNewProducer;

    @FXML
    private TextField textfieldProducer;

    @FXML
    private TextField textfieldAmount;

    @FXML
    private Button buttonAddProducer;

    @FXML
    private Button buttonAddAccounts;

    @FXML
    public void addProducer(ActionEvent e) throws IOException {
        String newProducer = textfieldNewProducer.getText();
        Producer producer = PersistenceProducer.getInstance().createProducer(newProducer);
    }

    @FXML
    public void addAccounts (ActionEvent e) throws  IOException {
        String nameOfProducer = textfieldProducer.getText();

        Producer producer = PersistenceProducer.getInstance().getProducer(nameOfProducer);

        if (producer == null) {
            // TODO: Show error to user
            return;
        }

        int amount = Integer.parseInt(textfieldAmount.getText());
        try {
            ArrayList<ProducerAccount> accounts = ManagementSystem.getInstance().createAccountsForProducer(producer.getID(), amount);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return;
        }

        // TODO: Show usernames and passwords to admin
    }
}
