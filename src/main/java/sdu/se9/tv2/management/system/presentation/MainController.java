package sdu.se9.tv2.management.system.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class MainController {
    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private void handleHomepageShowView(ActionEvent e) throws IOException {
        App.setPage("homepage");
    }

    @FXML
    private void handleTestShowView(ActionEvent e) throws IOException {
        App.setPage("test");
    }

    @FXML
    private void handleLoginShowView(ActionEvent e) throws IOException {
        App.setPage("login");
    }

    @FXML
    private void handleInsertCreditsShowView(ActionEvent e) throws IOException {
        App.setPage("insert-credits");
    }
}
