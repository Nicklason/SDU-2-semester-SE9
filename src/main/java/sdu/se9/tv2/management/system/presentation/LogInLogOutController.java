package sdu.se9.tv2.management.system.presentation;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sdu.se9.tv2.management.system.domain.accounts.Account;
import sdu.se9.tv2.management.system.persistence.PersistenceAccount;

import java.io.IOException;

public class LogInLogOutController {
    private Account account;

    @FXML
    private TextField usernameInput;
    @FXML
    private PasswordField passwordInput;
    @FXML
    private Label loginError;

    @FXML
    public void login(ActionEvent event) throws IOException {
        System.out.println("Hvad er dit brugernavn?");

        String username = usernameInput.getText();

        System.out.println(username);

        System.out.println("Hvad er din adgangskode?");

        String password = passwordInput.getText();

        System.out.println(password);

        Account account = PersistenceAccount.getInstance().getMatchingAccount(username, password);
        if (account == null) {
            loginError.setText("Brugernavn eller adgangskoder er forkert");
            return;
        }

        this.account = account;
        System.out.println(account);
        App.setPage("homepage");
    }

}
