package sdu.se9.tv2.management.system.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import sdu.se9.tv2.management.system.domain.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ExportController {

    private IManagementSystem managementSystem = ManagementSystem.getInstance();

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
            program = managementSystem.getProgram(programName);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return;
        }

        if (program == null) {
            userResponse.setText("Program med navn: " + programName + " findes ikke");
            return;
        }

        try {
            managementSystem.exportProgramToFile(program);
        } catch (SQLException err) {
            err.printStackTrace();
            // Alert user about error
            return;
        }

        userResponse.setText("Krediteringer for " + programName + " er nu eksporteret");
    }
}
