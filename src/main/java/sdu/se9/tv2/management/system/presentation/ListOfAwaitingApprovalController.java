package sdu.se9.tv2.management.system.presentation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sdu.se9.tv2.management.system.domain.Program;
import sdu.se9.tv2.management.system.persistence.PersistenceProgram;

import java.sql.SQLException;
import java.util.ArrayList;

public class ListOfAwaitingApprovalController {
    @FXML
    private TableView personTableView;

    private ObservableList<ProgramTableViewItem> data = FXCollections.observableArrayList();

    @FXML
    private void initialize () {
        TableColumn programName = (TableColumn)personTableView.getColumns().get(0);
        TableColumn programID = (TableColumn)personTableView.getColumns().get(1);

        programName.setCellValueFactory(new PropertyValueFactory<Program, String>("program"));
        programID.setCellValueFactory(new PropertyValueFactory<Program, String>("id"));

        data.clear();

        personTableView.setItems(data);
    }


        ArrayList<Program> programTableItems = new ArrayList<Program>();

        try {
        ArrayList<Program> programs = PersistenceProgram.getInstance().getProgramsAwaitingApproval();

        for (int i = 0; i < programs.size(); i++) {
            Program program = programs.get(i);
            programTableItems.add(program);
        }
    } catch (SQLException sql) {
        sql.printStackTrace();
        return;
    }

        data.setAll(ProgramTableItems);
}


