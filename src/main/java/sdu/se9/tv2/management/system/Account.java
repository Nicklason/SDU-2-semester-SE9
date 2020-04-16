package sdu.se9.tv2.management.system;

import org.json.simple.JSONObject;

import java.io.InvalidClassException;

public abstract class Account {
    private int id;
    private String username;
    private String password;
    private String type;

    Account(int accountId, String accountUsername, String accountPassword, String accountType) {
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

    public static Account parseJSON (JSONObject account) throws InvalidClassException {
        int id = Math.toIntExact((Long)account.get("id"));
        String username = (String)account.get("username");
        String password = (String)account.get("password");
        String type = (String)account.get("type");

        if (type.equals("producer")) {
            int producerId = Math.toIntExact((Long)account.get("producerId"));
            return new ProducerAccount(id, username, password, producerId);
        } else if (type.equals("admin")) {
            return new AdminAccount(id, username, password);
        } else if (type.equals("systemadmin")) {
            return new SystemAdminAccount(id, username, password);
        }

        throw new InvalidClassException("Unknown account type");
    }

    public static JSONObject parseJSON(Account account) {
        JSONObject obj = new JSONObject();

        obj.put("id", account.getId());
        obj.put("username", account.getUsername());
        obj.put("password", account.getPassword());

        String type = account.getType();
        obj.put("type", type);

        if (account.getType() == "producer") {
            ProducerAccount producerAccount = (ProducerAccount) account;
            obj.put("producerId", producerAccount.getProducerId());
        }

        return obj;
    }
}
