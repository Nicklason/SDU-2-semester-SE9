package sdu.se9.tv2.management.system.presentation;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import sdu.se9.tv2.management.system.domain.ManagementSystem;
import sdu.se9.tv2.management.system.domain.accounts.Account;
import sdu.se9.tv2.management.system.persistence.PersistenceAccount;


import java.io.IOException;

public class LoginController {
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

        Account account = PersistenceAccount.getInstance().getMatchingAccount(username, password);
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
