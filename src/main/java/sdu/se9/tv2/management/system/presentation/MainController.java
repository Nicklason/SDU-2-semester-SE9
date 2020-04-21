package sdu.se9.tv2.management.system.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import sdu.se9.tv2.management.system.domain.ManagementSystem;

import java.io.IOException;

public class MainController {

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

        ManagementSystem system = ManagementSystem.getInstance();

        if(system.isLoggedIn()){
            system.setAccount(null);
            App.updateLogin();
        } else{
            App.setPage("login");
        }
    }

    @FXML
    private void handleProducer(ActionEvent e) throws IOException {
        App.setPage("producer");
    }

    @FXML
    private void handleInsertCreditsShowView(ActionEvent e) throws IOException {
        App.setPage("insert-credits");
    }
}
