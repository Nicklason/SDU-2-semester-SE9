package sdu.se9.tv2.management.system.presentation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import sdu.se9.tv2.management.system.domain.*;
import sdu.se9.tv2.management.system.domain.accounts.ProducerAccount;

import java.sql.SQLException;
import java.util.ArrayList;

public class ViewCreditsController {
    private IManagementSystem managementSystem = ManagementSystem.getInstance();

    @FXML
    private TableView personTableView;

    @FXML
    private TextField programNameField;

    private ObservableList<CreditTableViewItem> data = FXCollections.observableArrayList();

    @FXML
    private void initialize () {
        TableColumn personIDColumn = (TableColumn)personTableView.getColumns().get(0);
        TableColumn personFirstnameColumn = (TableColumn)personTableView.getColumns().get(1);
        TableColumn personLastnameColumn = (TableColumn)personTableView.getColumns().get(2);
        TableColumn creditRoleColumn = (TableColumn)personTableView.getColumns().get(3);

        personIDColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("personID"));
        personFirstnameColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("firstName"));
        personLastnameColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("lastName"));
        creditRoleColumn.setCellValueFactory(new PropertyValueFactory<Credit, String>("role"));

        data.clear();

        personTableView.setItems(data);

        programNameField.textProperty().addListener((obs, oldText, newText) -> {
            this.onProgramNameChanged();
        });
    }

    private void onProgramNameChanged () {
        String programName = programNameField.getText();

        Program program = null;
        try {
            program = this.managementSystem.getProgram(programName);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return;
        }

        // If the user is a guest, then they can only see approved programs
        // If the user is a producer, then they can only see their own programs / approved programs

        if (program == null) {
            return;
        }

        ManagementSystem system = ManagementSystem.getInstance();
        boolean isProducer = system.hasAccess("producer");

        if (!program.isApproved()) {
            if (!isProducer) {
                // User is not a producer and the program has not been approved, don't show it
                return;
            }

            // Program is not approved and user is a producer
            ProducerAccount producer = (ProducerAccount)system.getAccount();

            if (producer.getProducerId() != program.getProducerID()) {
                // User does not own program, don't show it to them
                return;
            }
        }

        ArrayList<CreditTableViewItem> creditTableItems = new ArrayList<CreditTableViewItem>();

        try {
            ArrayList<Credit> credits = this.managementSystem.getCredits(program.getID());

            for (int i = 0; i < credits.size(); i++) {
                Credit credit = credits.get(i);
                creditTableItems.add(new CreditTableViewItem(credit));
            }
        } catch (SQLException sql) {
            sql.printStackTrace();
            return;
        }

        data.setAll(creditTableItems);
    }
}
