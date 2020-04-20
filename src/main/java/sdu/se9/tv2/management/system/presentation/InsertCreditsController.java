package sdu.se9.tv2.management.system.presentation;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import sdu.se9.tv2.management.system.domain.Credit;
import sdu.se9.tv2.management.system.domain.Person;
import sdu.se9.tv2.management.system.domain.Program;
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

        // Check if a person with the same name but no credits already exists

        Person person = PersistencePerson.getInstance().createPerson(firstname, lastname);

        personList.add(person);
    }

    @FXML
    private void handleCreateCredit (ActionEvent e) {
        System.out.println("Handle create credit");
    }

    /**
     * Called when the firstname or lastname text fields change
     */
    private void onPersonNameChanged () {
        String firstname = personFirstnameField.getText();
        String lastname = personLastnameField.getText();

        ArrayList<Person> people = PersistencePerson.getInstance().getPersons(firstname, lastname);

        personList.addAll(people);
    }

    private void onPersonSelected () {
        Person selected = (Person)personListView.getSelectionModel().getSelectedItem();

        String text = "ID: " + selected.getId();

        // Get credits that the person has

        ArrayList<Credit> credits = PersistenceCredit.getInstance().getCreditsByPerson(selected.getId(), 5);

        for (int i = 0; i < credits.size(); i++) {
            Credit credit = credits.get(i);
            Program program = PersistenceProgram.getInstance().getProgram(credit.getProgramID());

            text += "\nProgram: " + program.getName() + " Rolle: " + credit.getRole();
        }

        personInfoArea.setText(text);
    }
}
