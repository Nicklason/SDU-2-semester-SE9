package sdu.se9.tv2.management.system.presentation;

import java.io.IOException;
import javafx.fxml.FXML;
import sdu.se9.tv2.management.system.presentation.App;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
