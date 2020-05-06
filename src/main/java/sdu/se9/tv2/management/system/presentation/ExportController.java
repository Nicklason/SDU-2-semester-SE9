package sdu.se9.tv2.management.system.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import sdu.se9.tv2.management.system.domain.Credit;
import sdu.se9.tv2.management.system.domain.ManagementSystem;
import sdu.se9.tv2.management.system.domain.Person;
import sdu.se9.tv2.management.system.domain.Program;
import sdu.se9.tv2.management.system.domain.accounts.Account;
import sdu.se9.tv2.management.system.persistence.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ExportController {

    @FXML
    private TextField programNameText;

    @FXML
    private Text userResponse;

    @FXML
    public void export(ActionEvent event) throws IOException {

        userResponse.setText("");

        String programName = this.programNameText.getText();

        if (programName.isBlank()) {
            userResponse.setText("Feltet kan ikke være blankt");
            return;
        }


        Program program = null;
        try {
            program = PersistenceProgram.getInstance().getProgram(programName);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return;
        }

        if (program == null) {
            userResponse.setText("Program med navn: " + programName + " findes ikke");
            return;
        }

        Persistence persistence = new Persistence(program.getName().replaceAll("\\?", "") + ".json");
        ArrayList<Credit> credits = PersistenceCredit.getInstance().getCredits(program.getID());

        // Create JSONObject to save
        JSONObject obj = new JSONObject();

        obj.put("programNavn", program.getName());
        obj.put("programID", program.getID());

        // JSONArray that contains the producers
        JSONArray list = new JSONArray();

        // Go through producer list and parse as JSON objects
        for (int i = 0; i < credits.size(); i++) {
            Credit credit = credits.get(i);
            JSONObject jsonCredit = new JSONObject();

            Person person = PersistencePerson.getInstance().getPerson(credit.getPersonID());

            jsonCredit.put("rolle", credit.getRole());
            jsonCredit.put("personID", credit.getPersonID());
            jsonCredit.put("fornavn", person.getFirstName());
            jsonCredit.put("efternavn", person.getLastName());

            list.add(jsonCredit);
        }

        // Add list to JSON object
        obj.put("medvirkende", list);

        // Overwrite the file with new JSON object
        persistence.write(obj);

        userResponse.setText("Krediteringer for " + programName + " er nu eksporteret");
    }
}
