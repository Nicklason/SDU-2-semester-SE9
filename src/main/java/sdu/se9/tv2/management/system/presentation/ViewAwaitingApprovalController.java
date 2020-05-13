package sdu.se9.tv2.management.system.presentation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import sdu.se9.tv2.management.system.domain.IManagementSystem;
import sdu.se9.tv2.management.system.domain.ManagementSystem;
import sdu.se9.tv2.management.system.domain.Program;
import sdu.se9.tv2.management.system.persistence.PersistenceProgram;

import java.sql.SQLException;
import java.util.ArrayList;

public class ViewAwaitingApprovalController {
    private IManagementSystem managementSystem = ManagementSystem.getInstance();

    @FXML
    private TableView programTableView;

    private ObservableList<ProgramTableViewItem> data = FXCollections.observableArrayList();

    @FXML
    private void initialize () {
        TableColumn programID = (TableColumn)programTableView.getColumns().get(0);
        TableColumn programName = (TableColumn)programTableView.getColumns().get(1);

        programName.setCellValueFactory(new PropertyValueFactory<Program, String>("name"));
        programID.setCellValueFactory(new PropertyValueFactory<Program, String>("id"));

        data.clear();

        programTableView.setItems(data);
    }

    @FXML
    private void handleRefresh () {
        ArrayList<ProgramTableViewItem> programTableItems = new ArrayList<ProgramTableViewItem>();

        try {
            ArrayList<Program> programs = this.managementSystem.getProgramsPendingApproval();

            for (int i = 0; i < programs.size(); i++) {
                Program program = programs.get(i);
                programTableItems.add(new ProgramTableViewItem(program));
            }
        } catch (SQLException sql) {
            sql.printStackTrace();
            return;
        }

        data.setAll(programTableItems);
    }
}


