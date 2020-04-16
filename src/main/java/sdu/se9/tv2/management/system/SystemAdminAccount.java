package sdu.se9.tv2.management.system;

public class SystemAdminAccount extends Account {
    SystemAdminAccount(int accountId, String accountUsername, String accountPassword) {
        super(accountId, accountUsername, accountPassword,  "systemadmin");
    }

    @Override
    public String toString() {
        return "SystemAdminAccount{" +
                "id='" + getId() + '\'' +
                ", username='" + getUsername() + '\'' +
                ", type='" + getType() + '\'' +
                '}';
    }
}
