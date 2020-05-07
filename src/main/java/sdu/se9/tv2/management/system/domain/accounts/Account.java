package sdu.se9.tv2.management.system.domain.accounts;

import org.json.simple.JSONObject;

import java.io.InvalidClassException;

public abstract class Account {
    private int id;
    private String username;
    private String password;
    private String type;

    public Account(int accountId, String accountUsername, String accountPassword, String accountType) {
        this.id = accountId;
        this.username = accountUsername;
        this.password = accountPassword;
        this.type = accountType;
    }

    public int getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getType() {
        return this.type;
    }

}
