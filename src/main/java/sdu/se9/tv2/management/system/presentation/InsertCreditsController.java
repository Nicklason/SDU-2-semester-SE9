package sdu.se9.tv2.management.system.presentation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

import sdu.se9.tv2.management.system.domain.Credit;
import sdu.se9.tv2.management.system.domain.ManagementSystem;
import sdu.se9.tv2.management.system.domain.Person;
import sdu.se9.tv2.management.system.domain.Program;
import sdu.se9.tv2.management.system.exceptions.DuplicateRoleNameException;
import sdu.se9.tv2.management.system.persistence.PersistenceCredit;
import sdu.se9.tv2.management.system.persistence.PersistencePerson;
import sdu.se9.tv2.management.system.persistence.PersistenceProgram;

public class InsertCreditsController {

    @FXML
    private TextField programNameField;

    @FXML
    private TextField personFirstnameField;

    @FXML
    private TextField personLastnameField;

    @FXML
    ObservableList personList = FXCollections.observableArrayList();

    @FXML
    private ListView personListView;

    @FXML
    private TextArea personInfoArea;

    @FXML
    private TextField creditRoleField;

    @FXML
    private void initialize () {
        // Reset list and text area

        personList.clear();
        personListView.setItems(personList);

        personInfoArea.setText("");

        // Add event listeners
        personListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelect, newSelect) -> {
            this.onPersonSelected();
        });

        personFirstnameField.textProperty().addListener((obs, oldText, newText) -> {
            this.onPersonNameChanged();
        });

        personLastnameField.textProperty().addListener((obs, oldText, newText) -> {
            this.onPersonNameChanged();
        });
    }

    @FXML
    private void handleCreateNewPerson (ActionEvent e) {
        String firstname = personFirstnameField.getText();
        String lastname = personLastnameField.getText();

        if (firstname.isBlank() || lastname.isBlank()) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Tilføj kreditering");
            alert.setHeaderText("Tilføj ny person");
            alert.setContentText("Du har ikke indtastet navnet på personen");

            alert.show();
            return;
        }

        // Check if a person with the same name but no credits already exists

        boolean hasEmptyPerson = false;

        try {
            hasEmptyPerson = ManagementSystem.getInstance().hasExistingEmptyPerson(firstname, lastname);
        } catch (SQLException sql) {
            sql.printStackTrace();
            //Alert user of exception
            return;
        }

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Tilføj kreditering");
        alert.setHeaderText("Tilføj ny person");

        if (hasEmptyPerson) {
            alert.setContentText("En ny person med samme navn findes allerede, er du sikker?");
        } else {
            alert.setContentText("Er du sikker?");
        }

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() != ButtonType.OK){
            return;
        }

        Person person = null;

        try {
            person = PersistencePerson.getInstance().createPerson(firstname, lastname);
        } catch (SQLException sql){
            sql.printStackTrace();
            //Alert user of exception
            return;
        }

        personList.add(person);

        personListView.getSelectionModel().select(personList.size() - 1);
    }

    @FXML
    private void handleCreateCredit (ActionEvent e) {
        // Check if everything is good

        String programName = programNameField.getText();

        if (programName.isBlank()) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Tilføj kreditering");
            alert.setHeaderText("Mangler navn på program");
            alert.setContentText("Du har ikke indtastet et program navn");

            alert.show();
            return;
        }

        Person person = (Person)personListView.getSelectionModel().getSelectedItem();

        if (person == null) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Tilføj kreditering");
            alert.setHeaderText("Har ikke valgt en person");
            alert.setContentText("Du har ikke valgt en person");

            alert.show();
            return;
        }

        String creditRole = creditRoleField.getText();

        if (creditRole.isBlank()) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Tilføj kreditering");
            alert.setHeaderText("Mangler navn på rolle");
            alert.setContentText("Du har ikke indtastet et rolle navn");

            alert.show();
            return;
        }

        Program program = PersistenceProgram.getInstance().getProgram(programName);

        if (program == null) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Tilføj kreditering");
            alert.setHeaderText("Program ikke fundet");
            alert.setContentText("Fandt ikke et program med matchene navn");

            alert.show();
            return;
        }

        Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Tilføj kreditering");
        confirmationAlert.setHeaderText("Tilføj ny kreditering");
        confirmationAlert.setContentText("Er du sikker?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.get() != ButtonType.OK){
            return;
        }

        Credit credit = null;

        try {
            credit = PersistenceCredit.getInstance().createCredit(program.getID(), person.getId(), creditRole);
        } catch (DuplicateRoleNameException err) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Tilføj kreditering");
            alert.setHeaderText("Rolle eksisterer allerede");
            alert.setContentText("Rolle med navnet \"" + creditRole + "\" eksisterer allerede for program \"" + program.getName() + "\"");
            alert.show();
            return;
        } catch (SQLException sql) {
            sql.printStackTrace();
            //Alert user of exception
            return;
        }

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Tilføj kreditering");
        alert.setHeaderText("Tilføjet ny kreditering");
        alert.setContentText("Tilføjet ny kreditering for \"" + person.getName() + "\" i programmet \"" + program.getName() + "\" med rollen \"" + credit.getRole() + "\"");
        alert.show();

        this.onPersonSelected();
    }

    /**
     * Called when the firstname or lastname text fields change
     */
    private void onPersonNameChanged () {
        String firstname = personFirstnameField.getText();
        String lastname = personLastnameField.getText();

        ArrayList<Person> people = null;

        try {
            people = PersistencePerson.getInstance().getPersons(firstname, lastname);
        } catch (SQLException sql){
            sql.printStackTrace();
            //Alert user of exception
            return;
        }

        personList.clear();
        personList.addAll(people);
    }

    private void onPersonSelected () {
        Person selected = (Person)personListView.getSelectionModel().getSelectedItem();

        if (selected == null) {
            return;
        }

        String text = "ID: " + selected.getId() + "\nProgrammer medvirket i:";

        // Get credits that the person has

        ArrayList<Credit> credits = null;

        try {
            credits = PersistenceCredit.getInstance().getCreditsByPerson(selected.getId(), 5);
        } catch (SQLException sql) {
            sql.printStackTrace();
            //Alert user of exception
            return;
        }
        for (int i = 0; i < credits.size(); i++) {
            Credit credit = credits.get(i);
            Program program = PersistenceProgram.getInstance().getProgram(credit.getProgramID());

            text += "\n- Program: \"" + program.getName() + "\" Rolle: \"" + credit.getRole() + "\"";
        }

        personInfoArea.setText(text);
    }
}
