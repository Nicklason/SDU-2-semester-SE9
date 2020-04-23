package sdu.se9.tv2.management.system.domain.accounts;

public class AdminAccount extends Account {
    public AdminAccount(int accountId, String accountUsername, String accountPassword) {
        super(accountId, accountUsername, accountPassword,  "admin");
    }

    @Override
    public String toString() {
        return "AdminAccount{" +
                "id='" + getId() + '\'' +
                ", username='" + getUsername() + '\'' +
                ", type='" + getType() + '\'' +
                '}';
    }
}
