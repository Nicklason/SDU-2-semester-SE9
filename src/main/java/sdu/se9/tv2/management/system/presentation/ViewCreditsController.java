package sdu.se9.tv2.management.system.presentation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import sdu.se9.tv2.management.system.domain.Credit;
import sdu.se9.tv2.management.system.domain.Person;
import sdu.se9.tv2.management.system.domain.Program;
import sdu.se9.tv2.management.system.persistence.PersistenceCredit;
import sdu.se9.tv2.management.system.persistence.PersistenceProgram;

import java.sql.SQLException;
import java.util.ArrayList;

public class ViewCreditsController {
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
            program = PersistenceProgram.getInstance().getProgram(programName);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return;
        }

        if (program == null) {
            return;
        }

        ArrayList<Credit> credits = PersistenceCredit.getInstance().getCredits(program.getID());

        ArrayList<CreditTableViewItem> creditTableItems = new ArrayList<CreditTableViewItem>();

        for (int i = 0; i < credits.size(); i++) {
            Credit credit = credits.get(i);
            creditTableItems.add(new CreditTableViewItem(credit));
        }

        data.setAll(creditTableItems);
    }
}
