package sdu.se9.tv2.management.system.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ProducerController {

        @FXML
        public TextField textfieldNewProducer;

        @FXML
        private TextField textfieldProducer;

        @FXML
        private TextField textfieldAmount;

        @FXML
        private Button buttonAddProducer;

        @FXML
        private Button buttonAddAccounts;

        @FXML
        public String addProducer(ActionEvent e) {
                System.out.println("test");
                return null;
        }

        @FXML
        public String addAccounts (ActionEvent e) {
                System.out.println("test2");
                return null;
        }

}
