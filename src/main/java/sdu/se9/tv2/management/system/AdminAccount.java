package sdu.se9.tv2.management.system;

public class AdminAccount extends Account {
    private int producerId;

    AdminAccount(int accountId, String accountUsername, String accountPassword) {
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
