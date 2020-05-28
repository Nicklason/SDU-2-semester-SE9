package sdu.se9.tv2.management.system.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import sdu.se9.tv2.management.system.domain.IManagementSystem;
import sdu.se9.tv2.management.system.domain.ManagementSystem;
import sdu.se9.tv2.management.system.domain.Producer;
import sdu.se9.tv2.management.system.domain.accounts.ProducerAccount;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProducerController {

    private IManagementSystem managementSystem = ManagementSystem.getInstance();

    @FXML
    public TextField textfieldNewProducer;

    @FXML
    private TextField textfieldProducer;

    @FXML
    private TextField textfieldAmount;

    @FXML
    public void addProducer(ActionEvent e) throws IOException {
        String newProducer = textfieldNewProducer.getText();
        try {
            this.managementSystem.createProducer(newProducer);
        } catch (SQLException throwables) {
            throwables.printStackTrace();

            Alert alert = new Alert((Alert.AlertType.WARNING));
            alert.setTitle("Fejl");
            alert.setHeaderText("fejl");
            alert.setContentText("Der skete en fejl");
            alert.show();
        }
    }

    @FXML
    public void addAccounts (ActionEvent e) throws  IOException {
        String nameOfProducer = textfieldProducer.getText();

        Producer producer = null;
        try {
            producer = this.managementSystem.getProducer(nameOfProducer);
        } catch (SQLException throwables) {
            throwables.printStackTrace();

            Alert alert = new Alert((Alert.AlertType.WARNING));
            alert.setTitle("Fejl");
            alert.setHeaderText("fejl");
            alert.setContentText("Der skete en fejl");
            alert.show();
            return;
        }

        if (producer == null) {
            Alert alert = new Alert((Alert.AlertType.WARNING));
            alert.setTitle("Fejl");
            alert.setHeaderText("fejl");
            alert.setContentText("Indtast producenten's navn");
            alert.show();
            return;
        }

        int amount = Integer.parseInt(textfieldAmount.getText());

        ArrayList<ProducerAccount> accounts = null;
        try {
            accounts = this.managementSystem.createAccountsForProducer(producer.getID(), amount);
        } catch (SQLException ex) {
            ex.printStackTrace();

            Alert alert = new Alert((Alert.AlertType.WARNING));
            alert.setTitle("Fejl");
            alert.setHeaderText("fejl");
            alert.setContentText("Der skete en fejl");
            alert.show();
            return;
        }

        Alert alert = new Alert((Alert.AlertType.CONFIRMATION));
        alert.setTitle("Konti");
        alert.setHeaderText("Her er alle de oprettede konti:");
        alert.setContentText(accounts.toString());
        alert.show();
    }
}
