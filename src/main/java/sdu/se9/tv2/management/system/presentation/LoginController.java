package sdu.se9.tv2.management.system.presentation;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import sdu.se9.tv2.management.system.domain.IManagementSystem;
import sdu.se9.tv2.management.system.domain.ManagementSystem;
import sdu.se9.tv2.management.system.domain.accounts.Account;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {
    private IManagementSystem managementSystem = ManagementSystem.getInstance();

    @FXML
    private TextField usernameInput;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private Label loginError;

    private ManagementSystem system = ManagementSystem.getInstance();

    @FXML
    public void initialize() {
        loginError.setVisible(false);
    }

    @FXML
    public void login(ActionEvent event) throws IOException {
        String username = usernameInput.getText();
        String password = passwordInput.getText();

        Account account = null;
        try {
            account = managementSystem.getMatchingAccount(username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
        if (account == null) {
            loginError.setVisible(true);
            loginError.setText("Brugernavn eller adgangskoder er forkert");
            return;
        }

        system.setAccount(account);

        App.updateLogin();

        loginError.setVisible(false);

        App.setPage("homepage");
    }
}
