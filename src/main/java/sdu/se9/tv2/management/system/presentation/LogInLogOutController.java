package sdu.se9.tv2.management.system.presentation;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sdu.se9.tv2.management.system.domain.accounts.Account;
import sdu.se9.tv2.management.system.persistence.IPersistenceAccount;

public class LogInLogOutController {
    private Account account;
    private IPersistenceAccount persistenceAccount;

    @FXML
    private TextField usernameInput;
    @FXML
    private PasswordField passwordInput;

    @FXML
    public void login(ActionEvent event) {
        System.out.println("Hvad er dit brugernavn?");

        String username = usernameInput.getText();

        System.out.println(username);

        System.out.println("Hvad er din adgangskode?");

        String password = passwordInput.getText();

        System.out.println(password);

        Account account = IPersistenceAccount.getMatchingAccount(username, password);
        if (account == null) {
            System.out.println("Forkert brugernavn/adgangskode");
            return;
        }

        this.account = account;
        System.out.println(account);
    }

    public void logout () {
        this.account = null;
        System.out.println("Du er nu logget ud");
    }
}
