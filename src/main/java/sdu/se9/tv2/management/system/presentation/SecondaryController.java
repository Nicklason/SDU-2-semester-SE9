package sdu.se9.tv2.management.system.presentation;

import java.io.IOException;
import javafx.fxml.FXML;
import sdu.se9.tv2.management.system.presentation.App;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}