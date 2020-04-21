package sdu.se9.tv2.management.system.domain;

import sdu.se9.tv2.management.system.domain.accounts.Account;

public class ManagementSystem {

    private static ManagementSystem instance = null;

    public static ManagementSystem getInstance() {
        if (instance == null) {
            instance = new ManagementSystem();
        }

        return instance;
    }

    private Account account = null;

    private ManagementSystem () {};

    public void setAccount(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

    public boolean isLoggedIn () {
        return this.account != null;
    }

}
